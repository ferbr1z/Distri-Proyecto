package com.example.sdfernandobrizuela.auth;

import com.example.sdfernandobrizuela.auth.jwt.JWTService;
import com.example.sdfernandobrizuela.auth.requests.AuthRequest;
import com.example.sdfernandobrizuela.beans.RoleEnum;
import com.example.sdfernandobrizuela.beans.UserBean;
import com.example.sdfernandobrizuela.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;
    Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
    public AuthResponse registrar(RegisterRequest request){
        logger.warn(request.getPassword());
        var user = UserBean.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(RoleEnum.USER)
                .build();
        userRepository.save(user);
        String jwt = jwtService.generateToken(user);
        return AuthResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .token(jwt)
                .build();
    }

    public AuthResponse authenticate(AuthRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );


        Optional<UserBean> user = userRepository.findByEmail(request.getEmail());
        String jwt = jwtService.generateToken(user.get());

        return AuthResponse.builder()
                .username(user.get().getUsername())
                .email(user.get().getEmail())
                .token(jwt)
                .build();

    }


}