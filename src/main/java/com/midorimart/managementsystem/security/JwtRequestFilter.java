package com.midorimart.managementsystem.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.midorimart.managementsystem.entity.Permission;
import com.midorimart.managementsystem.entity.Role;
import com.midorimart.managementsystem.entity.User;
import com.midorimart.managementsystem.model.CustomError;
import com.midorimart.managementsystem.model.TokenPayload;
import com.midorimart.managementsystem.repository.PermissionRepository;
import com.midorimart.managementsystem.repository.UserRepository;
import com.midorimart.managementsystem.utils.JwtTokenUtil;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    private final List<String> listDontNeedAuthentication = Arrays.asList("/api/usermanagement/login",
            "/api/productManagement/getProductsByCategoryId", "/api/usermanagement/addNewUser",
            "/api/productManagement/getAllCategories");
    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String url = request.getRequestURI();
        String method = request.getMethod();
        // if (listDontNeedAuthentication.stream().anyMatch(o -> o.equals(url))) {
        // filterChain.doFilter(request, response);
        // return;
        // }

        final String requestToken = request.getHeader("Authorization");
        String token = null;
        TokenPayload tokenPayload = null;
        if (requestToken != null && requestToken.startsWith("Token ")) {
            token = requestToken.substring(6).trim();
            try {
                tokenPayload = jwtTokenUtil.getPayLoadFromToken(token);
            } catch (SignatureException e) {
                System.out.println("Invalid jwt signature");
            } catch (MalformedJwtException e) {
                System.out.println("Invalid Jwt Token");
            } catch (ExpiredJwtException e) {
                System.out.println("Token expired");
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get token");
                return;
            }
        } else {
            logger.warn("JWT does not start with Token");
        }

        if (tokenPayload != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Optional<User> userOptional = userRepository.findByEmail(tokenPayload.getEmail());
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                Role userRole = user.getRole();
                if (needCheckPermission(userRole, url, method)) {
                    String path = request.getRequestURI().toLowerCase();
                    if (path.matches("/api/v1/.+")) {
                        String route = path.split("[/]")[3];
                        path = "/api/v1/" + route;
                    }
                    System.out.println(path);
                    Optional<Permission> permissionOptional = permissionRepository.findByPathAndMethod(path, method);
                    if (permissionOptional.isEmpty()) {
                        CustomError customError = CustomError.builder().code("access denied")
                                .message("You have no permission").build();
                        responseToClient(response, customError, HttpServletResponse.SC_FORBIDDEN);
                        return;
                    }
                    List<Role> roleList = permissionOptional.get().getRoles();
                    boolean permission = false;
                    for (Role role : roleList) {
                        if (role.getId() == userRole.getId()) {
                            permission = true;
                            break;
                        }
                    }
                    if (!permission) {
                        CustomError customError = CustomError.builder().code("access.denied")
                                .message("Access denied, You have no permission").build();
                        responseToClient(response, customError, HttpServletResponse.SC_FORBIDDEN);
                        return;
                    }
                }
                if (jwtTokenUtil.validateToken(token, tokenPayload)) {
                    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                    UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getEmail(),
                            user.getPassword(), true, true, true, true, authorities);
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    private boolean needCheckPermission(Role userRole, String url, String method) {
        if (url.startsWith("/api/v1/user-management") && method.equalsIgnoreCase("PUT"))
            return true;
        return userRole.getId() != Role.ADMIN;
    }

    private void responseToClient(HttpServletResponse response, CustomError customError, int httpStatus)
            throws IOException {
        response.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        response.setStatus(httpStatus);
        Map<String, CustomError> map = new HashMap<>();
        map.put("error", customError);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        response.getOutputStream().print(mapper.writeValueAsString(map));
        response.flushBuffer();
    }
}
