package com.smartech.i2019.adaptiveinterviews.service;

import com.smartech.i2019.adaptiveinterviews.api.UsersAuthoritiesService;
import com.smartech.i2019.adaptiveinterviews.model.UserAuthorities;
import com.smartech.i2019.adaptiveinterviews.repository.UsersAuthoritiesRepository;
import com.smartech.i2019.adaptiveinterviews.repository.specification.UserAutoritiesSpecification;
import com.smartech.i2019.adaptiveinterviews.util.exception.SameDataException;
import com.smartech.i2019.adaptiveinterviews.util.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserAuthoritiesServiceImpl implements UsersAuthoritiesService {
    private final UsersAuthoritiesRepository usersAuthoritiesRepository;
    private final UserAutoritiesSpecification userAutoritiesSpecification;

    @Override
    public void add(UserAuthorities user) throws SameDataException {
        validate(user);
        usersAuthoritiesRepository.saveAndFlush(user);
    }

    @Override
    public void edit(UserAuthorities user) throws SameDataException {
        validate(user);
        usersAuthoritiesRepository.saveAndFlush(user);
    }

    @Override
    public void delete(long id) {
        log.info("Пользователь с никнеймом ({}) удалён", findById(id).getUsername());
        usersAuthoritiesRepository.deleteById(id);
    }

    @Override
    public List<UserAuthorities> findAll() {
        return usersAuthoritiesRepository.findAll();
    }

    @Override
    public UserAuthorities findById(Long id) {
        UserAuthorities userAuthorities = usersAuthoritiesRepository.findById(id).orElse(null);
        if (userAuthorities == null) {
            log.error("Пары логин/пароль с таким идентификатором нет в базе данных: ({})", id);
            throw new UserNotFoundException("Пары логин/пароль с таким идентификатором нет в базе данных");
        }
        return userAuthorities;
    }

    @Override
    public UserAuthorities findByUsername(String username) {
        return usersAuthoritiesRepository.findOne(userAutoritiesSpecification.hasUsername(username)).orElse(null);
    }

    public UserAuthorities findByUserId(Long id) {
        return usersAuthoritiesRepository.findUserAuthoritiesByUserId(id);
    }

    private void validate(UserAuthorities user) throws SameDataException {
        for (UserAuthorities userAuthorities : findAll()) {
            if (user.getUsername() == userAuthorities.getUsername()) {
                throw new SameDataException("Такой логин уже есть!");
            } else if (user.getUser().getEmail() == userAuthorities.getUser().getEmail()) {
                throw new SameDataException("Такой e-mail уже есть!");
            }
        }
    }

}
