package com.smartech.i2019.adaptiveinterviews.service;

import com.smartech.i2019.adaptiveinterviews.model.User;

import java.util.Collection;
import java.util.List;

public interface UserService {
    void add(User user);

    void edit(User user);

    void delete(long id);

    List<User> findAll();

    User findById(long id);

    List<User> findUsersByUsersId(Collection<Long> usersId);
}
