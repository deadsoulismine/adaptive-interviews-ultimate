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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserDaoImpl userDao;
    @Autowired
    UsersAutoritiesDao usersAutoritiesDao;

    @GetMapping()
    public String allUsers(Model model) {
        List<User> users = userDao.list();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/create")
    public ModelAndView createUser(@Valid Model model) {
        ModelAndView mav = new ModelAndView("userformcreate");
        mav.addObject("userFormCreate", new UserForm());
        return mav;
    }

    @GetMapping("/edit")
    public ModelAndView editUser(@Valid Model model) {
        ModelAndView mav = new ModelAndView("userformedit");
        UserAutorities userAutorities = getUserAutorities();
        mav.addObject("userFormEdit", new UserForm(userAutorities));
        return mav;
    }

    @PostMapping("/update")
    public String updateUser(@Valid UserForm form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("userFormEdit", form);
            return "userformedit";
        }
        UserAutorities userAutorities = getUserAutorities();
        User user = userAutorities.getUser();
        user.setName(form.getName());
        user.setEmail(form.getEmail());
        userAutorities.setPassword(form.getPassword());
        userAutorities.setUsername(form.getUsername());
        if (form.getRole() != null ){
            userAutorities.setRole(form.getRole());
        }
        userDao.update(user, user.getId());
        usersAutoritiesDao.update(userAutorities, userAutorities.getUser_id());
        return "redirect:/users";
    }


    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable(value = "id") int id) {
        usersAutoritiesDao.delete(id);
        return "redirect:/users";
    }

    @PostMapping("/add")
    public String addUser(@Valid UserForm form, BindingResult result, Model model) throws Exception {
        if (result.hasErrors()) {
            model.addAttribute("userFormCreate", form);
            return "userformcreate";
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
