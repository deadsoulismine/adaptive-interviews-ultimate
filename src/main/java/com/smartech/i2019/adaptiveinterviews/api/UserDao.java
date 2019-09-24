package com.smartech.i2019.adaptiveinterviews.api;

import com.smartech.i2019.adaptiveinterviews.model.User;

import java.util.List;

public interface UserDao {
    void add(User user);

    void update(User user, int id);

    void delete(int id);

    User getByUsername(String name);

    User getById(int id);

    List<User> list();
}
