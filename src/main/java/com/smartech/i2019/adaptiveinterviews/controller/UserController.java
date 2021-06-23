package com.smartech.i2019.adaptiveinterviews.controller;

import com.smartech.i2019.adaptiveinterviews.dao.UserDaoImpl;
import com.smartech.i2019.adaptiveinterviews.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name="Пользователи", description="Взаимодействие с пользователями")
public class UserController {
    @Autowired
    UserDaoImpl userDao;

    @Operation(summary = "Список всех пользователей")
    @GetMapping()
    ResponseEntity<List<User>> findAll() {
        List<User> users = userDao.list();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @Operation(summary = "Найти пользователя по ID")
    @GetMapping("/{id}")
    ResponseEntity<User> findUser(@PathVariable @Min(1) int id) throws EntityNotFoundException {
        User user = userDao.getById(id);
        if (user == null) {
            throw new EntityNotFoundException();
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Operation(summary = "Обновить данные пользователя")
    @PutMapping("/{id}")
    ResponseEntity<User> updateUser(@RequestBody User newUser, @PathVariable int id) {

        userDao.update(newUser, id);
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    @Operation(summary = "Удалить пользователя")
    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    ResponseEntity<String> deleteUser(@PathVariable int id) {
        userDao.delete(id);
        return new ResponseEntity<>("User deleted", HttpStatus.OK);
    }

    @Operation(summary = "Добавить нового пользователя")
    @PostMapping()
    ResponseEntity<User> newUser(@RequestBody User newUser) {
        userDao.add(newUser);
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

}
