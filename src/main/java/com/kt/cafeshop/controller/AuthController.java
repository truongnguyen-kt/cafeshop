package com.kt.cafeshop.controller;

import com.kt.cafeshop.config.DataHandler;
import com.kt.cafeshop.service.AuthService;
import com.kt.cafeshop.utils.JwtUtil;
import com.kt.cafeshop.utils.MessageDTO;
import com.kt.cafeshop.utils.request.AuthRequest;
import com.kt.cafeshop.utils.response.AuthResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cafeshop/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    @Autowired
    private DataHandler dataHandler;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthToken(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

            final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
            final String jwt = jwtUtil.generateToken(userDetails);
            String role = userDetails.getAuthorities().toString().replaceAll("\\[ROLE_|\\]", "");

            return ResponseEntity.ok(new AuthResponse(jwt, role));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred during authentication: " + e.getMessage());
        }
    }

    @GetMapping("/send")
    public ResponseEntity<?> send(@RequestParam String username, @RequestParam String message) {
        try {
            MessageDTO messageDTO = new MessageDTO(message, username);
            dataHandler.sendToUser(username, messageDTO);
            return ResponseEntity.ok("Message sent to user " + username);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send message: " + e.getMessage());
        }
    }
}
