package com.smartech.i2019.adaptiveinterviews.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;

@Data
public class JwtResponse implements Serializable {
    private static final long serialVersionUID = -8091879091924046844L;
    private final String jwtToken;
    private final String username;
    private final Long id;
    private final Collection<GrantedAuthority> authorities;

    public JwtResponse(String jwtToken, UserDetails userDetails, Long id) {
        this.id = id;
        this.jwtToken = jwtToken;
        username = userDetails.getUsername();
        authorities = (Collection<GrantedAuthority>) userDetails.getAuthorities();
    }
}