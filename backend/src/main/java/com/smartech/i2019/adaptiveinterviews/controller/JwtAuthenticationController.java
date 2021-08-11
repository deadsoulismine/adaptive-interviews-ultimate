package com.smartech.i2019.adaptiveinterviews.controller;

import com.smartech.i2019.adaptiveinterviews.model.JwtRequest;
import com.smartech.i2019.adaptiveinterviews.model.JwtResponse;
import com.smartech.i2019.adaptiveinterviews.security.JwtTokenUtil;
import com.smartech.i2019.adaptiveinterviews.security.UserDetailsServiceImpl;
import com.smartech.i2019.adaptiveinterviews.service.UserAuthoritiesServiceImpl;
import com.smartech.i2019.adaptiveinterviews.util.exception.InvalidCredentialsException;
import com.smartech.i2019.adaptiveinterviews.util.exception.UserDisabledException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Авторизация", description = "Логика авторизации")
public class JwtAuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final UserAuthoritiesServiceImpl userAuthoritiesService;

    @Operation(summary = "Авторизация")
    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws
            InvalidCredentialsException, UserDisabledException {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        final Long id = userAuthoritiesService.findByUsername(authenticationRequest.getUsername()).getUser().getId();
        return ResponseEntity.ok(new JwtResponse(token, userDetails, id));
    }

    private void authenticate(String username, String password) throws
            UserDisabledException, InvalidCredentialsException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new UserDisabledException("Учетная запись пользователя отключена", e);
        } catch (BadCredentialsException e) {
            throw new InvalidCredentialsException("Некорректная пара логин/пароль", e);
        }
    }

}