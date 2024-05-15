package com.proyectoRv.ecommerce.services.auth;

import com.proyectoRv.ecommerce.dto.SignupRequest;
import com.proyectoRv.ecommerce.dto.UserDto;

public interface AuthService {

    UserDto createUser(SignupRequest signupRequest);

    Boolean hasUserWithEmail(String email);

}
