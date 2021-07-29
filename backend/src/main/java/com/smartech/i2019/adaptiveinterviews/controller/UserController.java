package com.smartech.i2019.adaptiveinterviews.controller;

import com.smartech.i2019.adaptiveinterviews.model.User;
import com.smartech.i2019.adaptiveinterviews.model.UserAutorities;
import com.smartech.i2019.adaptiveinterviews.service.UserAutoritiesServiceImpl;
import com.smartech.i2019.adaptiveinterviews.service.UserServiceImpl;
import com.smartech.i2019.adaptiveinterviews.util.UserForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.Min;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:8081"})
@RestController
@RequestMapping("/api/users")
@Tag(name = "Пользователи", description = "Взаимодействие с пользователями")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;
    private final UserAutoritiesServiceImpl userAutoritiesService;
    private final PasswordEncoder passwordEncoder;

    @Operation(summary = "Список всех пользователей")
    @GetMapping()
    ResponseEntity<List<User>> findAll() {
        List<User> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @Operation(summary = "Найти пользователя по ID")
    @GetMapping("/find/{id}")
    ResponseEntity<User> findUser(@PathVariable @Min(1) Long id) throws EntityNotFoundException {
        User user = userService.findById(id);
        if (user == null) {
            throw new EntityNotFoundException();
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Operation(summary = "Обновить данные пользователя")
    @PutMapping("/update/{id}")
    ResponseEntity updateUser(@RequestBody UserForm newUser, @PathVariable Long id) {
        User userToUpdate = userService.findById(id);
        UserAutorities autoritiesToUpdate = userAutoritiesService.findByUserId(id);

        userToUpdate.setName(newUser.getName());
        userToUpdate.setEmail(newUser.getEmail());
        userToUpdate.setPosition(newUser.getPosition());
        userService.edit(userToUpdate);

        autoritiesToUpdate.setRole(newUser.getRole());
        autoritiesToUpdate.setUsername(newUser.getUsername());
        autoritiesToUpdate.setPassword(passwordEncoder.encode(newUser.getPassword()));
        userAutoritiesService.edit(autoritiesToUpdate);
        return new ResponseEntity(autoritiesToUpdate, HttpStatus.OK);
    }

    @Operation(summary = "Удалить пользователя")
    @DeleteMapping("/delete/{id}")
    @Secured("ROLE_ADMIN")
    ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userAutoritiesService.delete(userAutoritiesService.findByUserId(id).getId());
        return new ResponseEntity<>("User deleted", HttpStatus.OK);
    }

    @Operation(summary = "Добавить нового пользователя")
    @PostMapping("/add")
    ResponseEntity<User> newUser(@RequestBody UserForm form) {
        User user = new User();
        UserAutorities userAutorities = new UserAutorities();
        user.setName(form.getName());
        user.setEmail(form.getEmail());
        user.setPosition(form.getPosition());
        userAutorities.setPassword(passwordEncoder.encode(form.getPassword()));
        userAutorities.setUsername(form.getUsername());
        userAutorities.setRole("USER");
        userAutorities.setUser(user);
        userService.add(user);
        userAutoritiesService.add(userAutorities);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
