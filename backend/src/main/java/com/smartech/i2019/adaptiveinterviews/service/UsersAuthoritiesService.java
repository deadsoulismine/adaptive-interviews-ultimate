package com.smartech.i2019.adaptiveinterviews.service;

import com.smartech.i2019.adaptiveinterviews.model.UserAuthorities;

import java.util.List;

public interface UsersAuthoritiesService {

    void add(UserAuthorities user);

    void edit(UserAuthorities user);

    void delete(long id);

    List<UserAuthorities> findAll();

    UserAuthorities findById(Long id);

    UserAuthorities findByUserId(Long id);

    UserAuthorities findByUsername(String username);

}
