package com.midorimart.managementsystem.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.midorimart.managementsystem.entity.User;
import com.midorimart.managementsystem.exception.custom.CustomBadRequestException;
import com.midorimart.managementsystem.exception.custom.CustomNotFoundException;
import com.midorimart.managementsystem.model.CustomError;
import com.midorimart.managementsystem.model.mapper.UserMapper;
import com.midorimart.managementsystem.model.users.UserDTOCreate;
import com.midorimart.managementsystem.model.users.UserDTOLoginRequest;
import com.midorimart.managementsystem.model.users.UserDTOResponse;
import com.midorimart.managementsystem.model.users.UserDTOUpdate;
import com.midorimart.managementsystem.repository.UserRepository;
import com.midorimart.managementsystem.service.UserService;
import com.midorimart.managementsystem.utils.JwtTokenUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceimpl implements UserService {
    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;
    private static final String REGEX_PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d @$!%*?&]{6,32}$";
    private static final String REGEX_ALL_LETTER = "^[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễếệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ ]+$";
    private static final String REGEX_PHONE_NUMBER = "^(0|\\+84)(\\s|\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})$";

    @Override
    public Map<String, UserDTOResponse> authenticate(Map<String, UserDTOLoginRequest> userLoginRequestMap)
            throws CustomBadRequestException {
        UserDTOLoginRequest userDTOLoginRequest = userLoginRequestMap.get("user");

        Optional<User> userOptional = userRepository.findByEmail(userDTOLoginRequest.getEmail());

        boolean isAuthen = false;

        // Check if user exists
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(userDTOLoginRequest.getPassword(), user.getPassword())) {
                isAuthen = true;
            }
        }
        if (!isAuthen) {
            throw new CustomBadRequestException(
                    CustomError.builder().code("400").message("User name and password incorrect").build());
        }
        return buildDTOResponseForLogin(userOptional.get());

    }

    @Override
    public Map<String, UserDTOResponse> addNewUser(Map<String, UserDTOCreate> userDTOCreateMap)
            throws CustomBadRequestException {
        UserDTOCreate userDTOCreate = userDTOCreateMap.get("user");
        Pattern pattern = Pattern.compile(REGEX_PASSWORD);
        Pattern patternPhoneNumber = Pattern.compile(REGEX_PHONE_NUMBER);
        //check password
        if (!pattern.matcher(userDTOCreate.getPassword()).matches()) {
            throw new CustomBadRequestException(CustomError.builder().code("400")
                    .message(
                            "Password must be at least 6 characters and contain at least 1 uppercase, 1 lowercase, 1 digit and 1 special character")
                    .build());
        }
        //check phone number
        if (!patternPhoneNumber.matcher(userDTOCreate.getPhonenumber()).matches()) {
            throw new CustomBadRequestException(
                    CustomError.builder().code("400").message("Invalid Phone Number").build());
        }
        //check email
        if (userRepository.findByEmail(userDTOCreate.getEmail()).isEmpty()) {
            User user = UserMapper.toUser(userDTOCreate);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user = userRepository.save(user);
            return buildDTOResponseForLogin(user);
        }
        throw new CustomBadRequestException(CustomError.builder().code("400").message("Email already existed").build());
    }

    //return DTO response for login with Token
    private Map<String, UserDTOResponse> buildDTOResponseForLogin(User user) {
        Map<String, UserDTOResponse> wrapper = new HashMap<>();
        UserDTOResponse userDTOResponse = UserMapper.toUserDTOResponse(user);
        userDTOResponse.setToken(jwtTokenUtil.generateToken(user, 24 * 60 * 60));
        wrapper.put("user", userDTOResponse);
        return wrapper;
    }

    // return DTO response without Token
    private Map<String, UserDTOResponse> buildDTOResponse(User user) {
        Map<String, UserDTOResponse> wrapper = new HashMap<>();
        UserDTOResponse userDTOResponse = UserMapper.toUserDTOResponse(user);
        wrapper.put("user", userDTOResponse);
        return wrapper;
    }

    // Get user profile
    @Override
    public Map<String, UserDTOResponse> getCurrentUser() throws CustomNotFoundException {
        User userLogin = getUserLogin();
        if (userLogin != null) {
            return buildDTOResponse(userLogin);
        }
        throw new CustomNotFoundException(CustomError.builder().code("404").message("User not exist").build());
    }

    //Get user information after logging in
    @Override
    public User getUserLogin() {
        // User information is saved in SecurityHolder
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String email = ((UserDetails) principal).getUsername();
            User user = userRepository.findByEmail(email).get();
            return user;
        }
        return null;
    }

    // Update user profile
    @Override
    public Map<String, UserDTOResponse> updateUser(int id, Map<String, UserDTOUpdate> userUpdateMap) {
        User user = userRepository.findById(id).get();
        UserDTOUpdate userUpdateDTO = userUpdateMap.get("user");
        if (userUpdateDTO.getAddress() != null) {
            List<String> addresses = userUpdateDTO.getAddress();
            user.setAddress(addresses);
        }
        if (userUpdateDTO.getFullname() != null) {
            user.setFullname(userUpdateDTO.getFullname());
        }

        if (userUpdateDTO.getThumbnail() != null) {
            user.setThumbnail(userUpdateDTO.getThumbnail());
        }

        if (userUpdateDTO.getPhoneNumber() != null) {
            user.setPhonenumber(userUpdateDTO.getPhoneNumber());
        }

        user = userRepository.save(user);
        UserDTOResponse userDTOResponse = UserMapper.toUserDTOResponse(user);
        HashMap<String, UserDTOResponse> wrapper = new HashMap<>();
        wrapper.put("user", userDTOResponse);
        return wrapper;
    }

}
