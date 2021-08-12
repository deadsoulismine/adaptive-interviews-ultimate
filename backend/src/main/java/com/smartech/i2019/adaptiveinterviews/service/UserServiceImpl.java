package com.smartech.i2019.adaptiveinterviews.service;

import com.smartech.i2019.adaptiveinterviews.api.UserService;
import com.smartech.i2019.adaptiveinterviews.model.User;
import com.smartech.i2019.adaptiveinterviews.repository.UserRepository;
import com.smartech.i2019.adaptiveinterviews.util.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

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
            logger.error("Пользователя с таким идентификатором нет в базе данных: ({})", id);
            throw new UserNotFoundException("Пользователя с таким идентификатором нет в базе данных");
        }
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
