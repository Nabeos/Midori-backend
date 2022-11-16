package com.midorimart.managementsystem.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.midorimart.managementsystem.exception.custom.CustomBadRequestException;
import com.midorimart.managementsystem.model.address.dto.AddressDTOResponse;
import com.midorimart.managementsystem.model.users.UserDTOLoginRequest;
import com.midorimart.managementsystem.model.users.UserDTOResponse;
import com.midorimart.managementsystem.repository.UserRepository;
import com.midorimart.managementsystem.utils.JwtTokenUtil;

@ExtendWith(MockitoExtension.class)
public class UserServiceimplTest {
    @InjectMocks
    UserServiceimpl userService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private JwtTokenUtil jwtTokenUtil;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void testAddNewRole() {

    }

    @Test
    void testAddNewUser() {

    }

    @Test
    void testAuthenticate() throws CustomBadRequestException {
        // get
        UserDTOLoginRequest userDTOLoginRequest = UserDTOLoginRequest.builder().email("email").password("password")
                .build();
        Map<String, UserDTOLoginRequest> userLoginRequestMap = new HashMap<>();
        userLoginRequestMap.put("user", userDTOLoginRequest);

        Optional<com.midorimart.managementsystem.entity.User> userOptional = Optional
                .of(com.midorimart.managementsystem.entity.User.builder().thumbnail("thumbnail").id(1)
                        .address("province;district;ward;detail").email("email").password("password").build());
        Map<String, UserDTOResponse> expected = new HashMap<>();
        expected.put("user",
                UserDTOResponse.builder().id(1).email("email").thumbnail("thumbnail").phonenumber("phone")
                        .address(AddressDTOResponse.builder().provinceId("province").districtId("district")
                                .wardId("ward").addressDetail("detail").build())
                        .token("TOKEN")
                        .build());
        // when
        when(userRepository.findByEmail(userDTOLoginRequest.getEmail())).thenReturn(userOptional);
        when(passwordEncoder.matches("password", "password")).thenReturn(true);
        when(jwtTokenUtil.generateToken(userOptional.get(), 24 * 60 * 60)).thenReturn("TOKEN");
        Map<String, UserDTOResponse> actual = userService.authenticate(userLoginRequestMap);
        // then

        assertEquals(true, actual.containsKey("user"));
        UserDTOResponse userDTOResponse = actual.get("user");
        assertEquals(expected.get("user").getEmail(), userDTOResponse.getEmail());
        assertEquals(expected.get("user").getId(), userDTOResponse.getId());
        assertEquals(expected.get("user").getAddress(), userDTOResponse.getAddress());
        assertEquals(expected.get("user").getThumbnail(), userDTOResponse.getThumbnail());
        assertEquals(expected.get("user").getToken(), userDTOResponse.getToken());

    }

    @Test
    void testAuthenticate_Throw_CustomBadRequest() throws CustomBadRequestException {
        // get
        UserDTOLoginRequest userDTOLoginRequest = UserDTOLoginRequest.builder().email("email").password("password")
                .build();
        Map<String, UserDTOLoginRequest> userLoginRequestMap = new HashMap<>();
        userLoginRequestMap.put("user", userDTOLoginRequest);

        Optional<com.midorimart.managementsystem.entity.User> userOptional = Optional
                .of(com.midorimart.managementsystem.entity.User.builder().thumbnail("thumbnail").id(1)
                        .address("province;district;ward;detail").email("email").password("password").build());
        Map<String, UserDTOResponse> expected = new HashMap<>();
        expected.put("user",
                UserDTOResponse.builder().id(1).email("email").thumbnail("thumbnail").phonenumber("phone")
                        .address(AddressDTOResponse.builder().provinceId("province").districtId("district")
                                .wardId("ward").addressDetail("detail").build())
                        .token("TOKEN")
                        .build());
        // when
        when(userRepository.findByEmail(userDTOLoginRequest.getEmail())).thenReturn(Optional.ofNullable(null));
        // then
        assertThrows(CustomBadRequestException.class, () -> {
            userService.authenticate(userLoginRequestMap);
        });
    }

    @Test
    void testGetCurrentUser() {

    }

    @Test
    void testGetUserLogin() {

    }

    @Test
    void testRegister() {

    }

    @Test
    void testUpdateUser() {

    }

    @Test
    void testUploadImage() {

    }
}
