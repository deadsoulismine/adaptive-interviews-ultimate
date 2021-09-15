package com.smartech.i2019.adaptiveinterviews.security;

import com.smartech.i2019.adaptiveinterviews.model.UserAuthorities;
import com.smartech.i2019.adaptiveinterviews.service.UsersAuthoritiesService;
import com.smartech.i2019.adaptiveinterviews.util.exception.InvalidCredentialsException;
import com.smartech.i2019.adaptiveinterviews.util.exception.UserDisabledException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Авторизация", description = "Логика авторизации")
public class JwtAuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;
    private final UsersAuthoritiesService userAuthoritiesService;

    @Operation(summary = "Авторизация")
    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws
            InvalidCredentialsException, UserDisabledException {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        final Long id = userAuthoritiesService.findByUsername(authenticationRequest.getUsername()).getUser().getId();
        UserAuthorities user = userAuthoritiesService.findByUserId(id);
        if (user.getRole().equals("ADMIN")) {
            log.warn("Вход администратора под никнеймом: ({})", user.getUsername());
        }
        return ResponseEntity.ok(new JwtResponse(token, userDetails, id));
    }

    private void authenticate(String username, String password) throws
            UserDisabledException, InvalidCredentialsException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            log.error("Попытка использования деактированной учётной записи!", e);
            throw new UserDisabledException("Учетная запись пользователя отключена", e);
        } catch (BadCredentialsException e) {
            throw new InvalidCredentialsException("Некорректная пара логин/пароль", e);
        }
    }

}