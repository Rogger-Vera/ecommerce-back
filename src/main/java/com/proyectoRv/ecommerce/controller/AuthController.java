package com.proyectoRv.ecommerce.controller;

import com.proyectoRv.ecommerce.dto.AuthenticationRequest;
import com.proyectoRv.ecommerce.dto.SignupRequest;
import com.proyectoRv.ecommerce.dto.UserDto;
import com.proyectoRv.ecommerce.entity.UserEntity;
import com.proyectoRv.ecommerce.repository.UserRepository;
import com.proyectoRv.ecommerce.services.auth.AuthService;
import com.proyectoRv.ecommerce.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String HEADER_STRING = "Authorization";

    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final UserDetailsService userDetailsService;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final JwtUtil jwtUtil;
    @Autowired
    private final AuthService authService;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/authenticate")
    public void createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest,
                                          HttpServletResponse response) throws IOException, JSONException {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUserName(),
                    authenticationRequest.getPassword()));
            logger.info("Autenticación exitosa para el usuario: {}", authenticationRequest.getUserName());
        } catch (BadCredentialsException e){
            logger.error("Error de autenticación para el usuario: {}", authenticationRequest.getUserName());
            response.getWriter().write(new JSONObject()
                    .put("status", HttpStatus.UNAUTHORIZED.value())
                    .put("errorMessage", e.getMessage())
                    .toString()
            );
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.flushBuffer();
            return ;
        } catch (InternalAuthenticationServiceException e){
            logger.error("Error de autenticación interno: {}", e.getMessage());
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUserName());
        Optional<UserEntity> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

        if (optionalUser.isPresent()) {
            response.getWriter().write(new JSONObject()
                    .put("userId ", optionalUser.get().getId())
                    .put("userRole", optionalUser.get().getUserRole())
                    .put("status", HttpStatus.OK.value())
                    .toString()
            );
            response.addHeader("Access-Control-Expose-Headers", "Authorization");
            response.addHeader("Access-Control-Allow-Headers", "Authorization, X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept, X-Custom-header");
            response.addHeader(HEADER_STRING, TOKEN_PREFIX + jwt);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest){
        System.out.println("body: " + signupRequest);
        if (authService.hasUserWithEmail(signupRequest.getEmail())) {
            return new ResponseEntity<>("EL usuario ya existe", HttpStatus.NOT_ACCEPTABLE);
        }
        UserDto userDto = authService.createUser(signupRequest);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

}
