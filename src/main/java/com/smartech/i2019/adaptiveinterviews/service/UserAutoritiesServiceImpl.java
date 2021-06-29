package com.smartech.i2019.adaptiveinterviews.service;

import com.smartech.i2019.adaptiveinterviews.api.UsersAutoritiesService;
import com.smartech.i2019.adaptiveinterviews.model.UserAutorities;
import com.smartech.i2019.adaptiveinterviews.repository.UsersAutoritiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAutoritiesServiceImpl implements UsersAutoritiesService {
    @Autowired
    UsersAutoritiesRepository usersAutoritiesRepository;

    @Override
    public void add(UserAutorities user) {
        usersAutoritiesRepository.saveAndFlush(user);
    }

    @Override
    public void edit(UserAutorities user) {
        usersAutoritiesRepository.saveAndFlush(user);
    }

    @Override
    public void delete(long id) {
        usersAutoritiesRepository.deleteById(id);
    }

    @Override
    public List<UserAutorities> findAll() {
        return usersAutoritiesRepository.findAll();
    }

    @Override
    public UserAutorities findByUsername(String username) {
        return usersAutoritiesRepository.findByUsername(username);
    }
}
