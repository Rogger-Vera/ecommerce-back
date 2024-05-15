package com.proyectoRv.ecommerce.services.auth;

import com.proyectoRv.ecommerce.dto.SignupRequest;
import com.proyectoRv.ecommerce.dto.UserDto;
import com.proyectoRv.ecommerce.entity.UserEntity;
import com.proyectoRv.ecommerce.enums.UserRole;
import com.proyectoRv.ecommerce.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserDto createUser(SignupRequest signupRequest){
        UserEntity userEntity = UserEntity.builder()
                .name(signupRequest.getName())
                .email(signupRequest.getEmail())
                .password(new BCryptPasswordEncoder().encode(signupRequest.getPassword()))
                .userRole(UserRole.CUSTOMER)
                .build();

        UserEntity createdUser = userRepository.save(userEntity);

        UserDto userDto = new UserDto();
        userDto.setId(createdUser.getId());

        return userDto;
    }

    public Boolean hasUserWithEmail(String email){
        return userRepository.findFirstByEmail(email).isPresent();
    }

    @PostConstruct
    public void createAdminAccount(){
        UserEntity adminAccount = userRepository.findByUserRole(UserRole.ADMIN);
        if (adminAccount == null) {
            UserEntity userEntity = UserEntity.builder()
                    .name("admin")
                    .email("admin@test.com")
                    .password(new BCryptPasswordEncoder().encode("admin123"))
                    .userRole(UserRole.ADMIN)
                    .build();

            userRepository.save(userEntity);
        }
    }
}
