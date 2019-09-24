package com.smartech.i2019.adaptiveinterviews.api;

import com.smartech.i2019.adaptiveinterviews.model.UserAutorities;

import java.util.List;

public interface UsersAutoritiesDao {
    UserAutorities findByUsername(String username);

    List<UserAutorities> list();

    void update(UserAutorities user, int id);

    void add(UserAutorities user);

    void delete(int id);
}
