package com.proyectoRv.ecommerce.services;

import com.proyectoRv.ecommerce.entity.UserEntity;
import com.proyectoRv.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> optionalUser = userRepository.findFirstByEmail(username);
        if (optionalUser.isEmpty()) throw new UsernameNotFoundException("Usuario no encontrado", null);
        UserEntity user = optionalUser.get();
        return User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(user.getUserRole().name())
                .build();
    }
}
