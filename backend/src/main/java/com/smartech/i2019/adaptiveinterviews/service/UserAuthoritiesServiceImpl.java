package com.smartech.i2019.adaptiveinterviews.service;

import com.smartech.i2019.adaptiveinterviews.api.UsersAuthoritiesService;
import com.smartech.i2019.adaptiveinterviews.model.UserAuthorities;
import com.smartech.i2019.adaptiveinterviews.repository.UsersAuthoritiesRepository;
import com.smartech.i2019.adaptiveinterviews.repository.specification.UserAutoritiesSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAuthoritiesServiceImpl implements UsersAuthoritiesService {
    private final UsersAuthoritiesRepository usersAuthoritiesRepository;
    private final UserAutoritiesSpecification userAutoritiesSpecification;

    @Override
    public void add(UserAuthorities user) {
        usersAuthoritiesRepository.saveAndFlush(user);
    }

    @Override
    public void edit(UserAuthorities user) {
        usersAuthoritiesRepository.saveAndFlush(user);
    }

    @Override
    public void delete(long id) {
        usersAuthoritiesRepository.deleteById(id);
    }

    @Override
    public List<UserAuthorities> findAll() {
        return usersAuthoritiesRepository.findAll();
    }

    @Override
    public UserAuthorities findById(Long id) {
        return usersAuthoritiesRepository.findById(id).orElse(null);
    }

    @Override
    public UserAuthorities findByUsername(String username) {
        return usersAuthoritiesRepository.findOne(userAutoritiesSpecification.hasUsername(username)).orElse(null);
    }

    public UserAuthorities findByUserId(Long id) {
        return usersAuthoritiesRepository.findUserAuthoritiesByUserId(id);
    }
}