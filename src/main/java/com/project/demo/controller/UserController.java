/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.demo.controller;

import com.project.demo.model.Users;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.project.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author salimkun
 */
@RestController
@RequestMapping("/api/v1/users/")
public class UserController {

    @Autowired
    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    List<Users> all() {
        return userService.findAllUsers();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Users createUser(@RequestBody @Validated Users user) {
        return userService.createUser(user);
    }
    
    @PutMapping("{id}")
    public ResponseEntity<Users> updateUser(@PathVariable Long id, @RequestBody Users user) {
        return userService.findUserById(id)
                .map(userObj -> {
                    userObj.setId(id);
                    userObj.setAge(user.getAge());
                    userObj.setName(user.getName());
                    return ResponseEntity.ok(userService.updateUser(userObj));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Users> deleteUser(@PathVariable Long id) {
        return userService.findUserById(id)
                .map(user -> {
                    userService.deleteUserById(id);
                    return ResponseEntity.ok(user);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
