package com.smartech.i2019.adaptiveinterviews.service.impl;

import com.smartech.i2019.adaptiveinterviews.model.User;
import com.smartech.i2019.adaptiveinterviews.repository.UserRepository;
import com.smartech.i2019.adaptiveinterviews.service.UserService;
import com.smartech.i2019.adaptiveinterviews.service.UsersAuthoritiesService;
import com.smartech.i2019.adaptiveinterviews.util.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UsersAuthoritiesService usersAuthoritiesService;

    @Override
    public void add(User user) {
        userRepository.saveAndFlush(user);
    }

    @Override
    public void edit(User user) {
        userRepository.saveAndFlush(user);
    }

    @Override
    public void delete(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findById(long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            log.error("Пользователя с таким идентификатором нет в базе данных: ({})", id);
            throw new UserNotFoundException("Пользователя с таким идентификатором нет в базе данных");
        }
        user.setLogin(usersAuthoritiesService.findByUserId(id).getUsername());
        return user;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public List<User> findUsersByUsersId(Collection<Long> usersId) {
        return userRepository.findUsersByIdIn(usersId);
    }
}
