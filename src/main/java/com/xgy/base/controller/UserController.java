package com.xgy.base.controller;

import com.xgy.base.entity.User;
import com.xgy.base.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping
    public boolean create(@RequestBody User user) {
        return this.userService.save(user);
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable Long id) {
        return this.userService.getById(id);
    }

    @GetMapping
    public List<User> list() {
        return this.userService.list();
    }

    @PutMapping
    public boolean update(@RequestBody User user) {
        return this.userService.updateById(user);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return this.userService.removeById(id);
    }
}
