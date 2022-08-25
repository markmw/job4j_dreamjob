package ru.job4j.dream.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.dream.model.User;
import ru.job4j.dream.service.UserService;

import java.util.Optional;

@Controller @ThreadSafe
public class UserController {
    private final UserService store;

    public UserController(UserService store) {
        this.store = store;
    }

    @GetMapping("/formAddUser")
    public String formAddUser() {
        return "addUser";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute User user) {
        Optional<User> regUser = store.add(user);
        if (regUser.isEmpty()) {
            return "redirect:/fail";
        }
        return "redirect:/success";
    }

    @GetMapping("/fail")
    public String failRegUser() {
        return "fail";
    }

    @GetMapping("success")
    public String successRegUser() {
        return "success";
    }
}
