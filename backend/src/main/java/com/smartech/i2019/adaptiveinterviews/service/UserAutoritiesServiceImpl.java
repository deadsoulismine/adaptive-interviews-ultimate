package com.smartech.i2019.adaptiveinterviews.service;

import com.smartech.i2019.adaptiveinterviews.api.UsersAutoritiesService;
import com.smartech.i2019.adaptiveinterviews.model.UserAutorities;
import com.smartech.i2019.adaptiveinterviews.repository.UsersAutoritiesRepository;
import com.smartech.i2019.adaptiveinterviews.util.specification.UserAutoritiesSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAutoritiesServiceImpl implements UsersAutoritiesService {
    private final UsersAutoritiesRepository usersAutoritiesRepository;
    private final UserAutoritiesSpecification userAutoritiesSpecification;

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
        return usersAutoritiesRepository.findOne(userAutoritiesSpecification.hasUsername(username)).orElse(null);
    }
}
