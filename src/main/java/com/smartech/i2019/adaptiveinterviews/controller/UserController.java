package com.smartech.i2019.adaptiveinterviews.controller;

import com.smartech.i2019.adaptiveinterviews.api.UsersAutoritiesDao;
import com.smartech.i2019.adaptiveinterviews.dao.UserDaoImpl;
import com.smartech.i2019.adaptiveinterviews.model.User;
import com.smartech.i2019.adaptiveinterviews.model.UserAutorities;
import com.smartech.i2019.adaptiveinterviews.util.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    UserDaoImpl userDao;
    @Autowired
    UsersAutoritiesDao usersAutoritiesDao;

    @GetMapping(path = "/users")
    public String allUsers(Model model) {
        List<User> users = userDao.list();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/users/new/add")
    public ModelAndView getUserForm(Model model) {
        ModelAndView mav = new ModelAndView("userform");
        mav.addObject("userForm", new UserForm());
        return mav;
    }

    @GetMapping("/users/edit")
    public ModelAndView editUser(Model model) {
        ModelAndView mav = new ModelAndView("userform");
        UserAutorities userAutorities = getUserAutorities();
        mav.addObject("userForm", new UserForm(userAutorities));
        return mav;
    }

    @PostMapping("/users/update")
    public String updateUser(@Valid UserForm form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("userForm", form);
            return "userform";
        }
        UserAutorities userAutorities = getUserAutorities();
        User user = userAutorities.getUser();
        user.setName(form.getName());
        user.setEmail(form.getEmail());
        userAutorities.setPassword(form.getPassword());
        userAutorities.setUsername(form.getUsername());
        userAutorities.setRole(form.getRole());
        userDao.update(user, user.getId());
        usersAutoritiesDao.update(userAutorities, userAutorities.getUser_id());
        return "redirect:/users";
    }

    @GetMapping("/users/delete")
    public String deleteUser(BindingResult result, Model model) {
        UserAutorities userAutorities = getUserAutorities();
        User user = userAutorities.getUser();
        userDao.delete(user.getId());
        return "redirect:/users";
    }

    @PostMapping("/users/new/update")
    public String createUser(@Valid UserForm form, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            return "userform";
        }
        UserAutorities userAutorities = new UserAutorities();
        User user = new User();
        if (userDao.getByUsername(form.getName()) != null) {
            throw new Exception("Пользователь с таким именем уже существует");
        }
        user.setName(form.getName());
        user.setEmail(form.getEmail());
        userAutorities.setPassword(form.getPassword());
        userAutorities.setUsername(form.getUsername());
        userAutorities.setRole(form.getRole());
        userAutorities.setUser(user);
        userDao.add(user);
        usersAutoritiesDao.add(userAutorities);
        return "redirect:/users";
    }

    private UserAutorities getUserAutorities() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User userPrincipal =
                (org.springframework.security.core.userdetails.User) auth.getPrincipal();
        UserAutorities userAutorities = usersAutoritiesDao.findByUsername(userPrincipal.getUsername());
        if (userAutorities == null) {
            throw new EntityNotFoundException("Пользователь не найден");
        }
        return userAutorities;
    }

}
