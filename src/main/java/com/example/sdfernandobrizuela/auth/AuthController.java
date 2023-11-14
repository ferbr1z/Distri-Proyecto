package com.example.sdfernandobrizuela.auth;

import com.example.sdfernandobrizuela.auth.requests.AuthRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity registrar(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authenticationService.registrar(request));
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthRequest request){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

}
