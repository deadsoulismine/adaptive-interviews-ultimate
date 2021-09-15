package com.smartech.i2019.adaptiveinterviews.service;

import com.smartech.i2019.adaptiveinterviews.AdaptiveInterviewsApplication;
import com.smartech.i2019.adaptiveinterviews.model.User;
import com.smartech.i2019.adaptiveinterviews.repository.UserRepository;
import com.smartech.i2019.adaptiveinterviews.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdaptiveInterviewsApplication.class)
@TestPropertySource("/application-test.properties")
class UserServiceImplTest {

    @Mock
//    @Autowired
    UserRepository userRepository;
    @InjectMocks
//    @Autowired
    UserServiceImpl userService;
    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findById() {
//            when(userService.findById(user.getId())).thenReturn(user);

        User foundUser = userService.findById(2);

        assertThat(foundUser).isEqualTo(2);
    }

    @Test
    void add() {
    }

}