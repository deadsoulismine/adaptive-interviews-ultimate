package com.smartech.i2019.adaptiveinterviews.service;

import com.smartech.i2019.adaptiveinterviews.model.User;
import com.smartech.i2019.adaptiveinterviews.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
class UserServiceImplTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void add() {
    }

    @Test
    void findById() {
        // given
        User user = new User();
        user.setId(1);
        userRepository.saveAndFlush(user);
        // when
        User foundUser = userRepository.findById(user.getId()).orElse(null);

        // then
        assertThat(foundUser.getId()).isEqualTo(user.getId());
    }

}