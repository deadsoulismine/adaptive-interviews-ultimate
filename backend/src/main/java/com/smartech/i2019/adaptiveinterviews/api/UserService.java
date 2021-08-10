package com.smartech.i2019.adaptiveinterviews.api;

import com.smartech.i2019.adaptiveinterviews.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    void add(User user);

    void edit(User user);

    void delete(long id);

    List<User> findAll();

    User findById(long id);

    List<User> findUsersByUsersId(Set<Long> usersId);
}
