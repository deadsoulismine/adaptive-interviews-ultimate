package com.smartech.i2019.adaptiveinterviews.controller;

import com.smartech.i2019.adaptiveinterviews.api.UserService;
import com.smartech.i2019.adaptiveinterviews.api.UsersAuthoritiesService;
import com.smartech.i2019.adaptiveinterviews.dto.UserDTO;
import com.smartech.i2019.adaptiveinterviews.dto.mapper.UserMapper;
import com.smartech.i2019.adaptiveinterviews.model.User;
import com.smartech.i2019.adaptiveinterviews.model.UserAuthorities;
import com.smartech.i2019.adaptiveinterviews.util.exception.UserNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:8081"})
@RestController
@RequestMapping("/api/users")
@Tag(name = "Пользователи", description = "Взаимодействие с пользователями")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UsersAuthoritiesService userAuthoritiesService;
    private final UserMapper userMapper;

    @Operation(summary = "Список всех пользователей")
    @GetMapping()
    List<User> findAll() {
        return userService.findAll();
    }

    @Operation(summary = "Найти пользователя по ID")
    @GetMapping("/{id}")
    User findUser(@PathVariable @Min(1) Long id) throws UserNotFoundException {
        return userService.findById(id);
    }

    @Operation(summary = "Обновить данные пользователя")
    @PutMapping("/{id}")
    User updateUser(@RequestBody UserDTO userDTO) {
        User userToUpdate = userMapper.userDTOToUser(userDTO);
        UserAuthorities authoritiesToUpdate = userMapper.userDTOToUserAuthorities(userDTO);
        authoritiesToUpdate.setUser(userToUpdate);
        userAuthoritiesService.edit(authoritiesToUpdate);
        userService.edit(userToUpdate);
        return userToUpdate;
    }

    @Operation(summary = "Удалить пользователя")
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    String deleteUser(@PathVariable Long id) {
        userAuthoritiesService.delete(userAuthoritiesService.findById(id).getId());
        return "User deleted";
    }

    @Operation(summary = "Добавить нового пользователя")
    @PostMapping("/add")
    User newUser(@RequestBody UserDTO userDTO) {
        User user = userMapper.userDTOToUser(userDTO);
        UserAuthorities userAuthorities = userMapper.userDTOToUserAuthorities(userDTO);
        userAuthorities.setUser(user);
        userAuthoritiesService.add(userAuthorities);
        userService.add(user);
        return user;
    }

}
