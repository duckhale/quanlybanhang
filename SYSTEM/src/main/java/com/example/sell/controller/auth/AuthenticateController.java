package com.example.sell.controller.auth;


import com.example.sell.authen.AuthenticationRequest;
import com.example.sell.authen.AuthenticationResponse;
import com.example.sell.model.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@CrossOrigin(origins = "*")
public class AuthenticateController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    @Qualifier("userDetailsService")
    private UserDetailsService  mySQLDetailService;

    @PostMapping("/authenticate")
    public ResponseEntity<?>createAuthenticationToken(@RequestBody AuthenticationRequest authReq) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authReq.getUsername(),authReq.getPassword())
            );
        } catch (AuthenticationException e) {
//            throw new Exception("Incorrect username or password");
            return ResponseEntity.status(401).body("Incorrect username or password");
        }

        final UserDetails userDetails = mySQLDetailService.loadUserByUsername(authReq.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
