package com.smartech.i2019.adaptiveinterviews.api;

import com.smartech.i2019.adaptiveinterviews.model.UserAuthorities;
import com.smartech.i2019.adaptiveinterviews.util.exception.SameDataException;

import java.util.List;

public interface UsersAuthoritiesService {

    void add(UserAuthorities user) throws SameDataException;

    void edit(UserAuthorities user) throws SameDataException;

    void delete(long id);

    List<UserAuthorities> findAll();

    UserAuthorities findById(Long id);

    UserAuthorities findByUserId(Long id);

    UserAuthorities findByUsername(String username);

}
