/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.demo.controller;

import com.project.demo.exception.UserException;
import com.project.demo.model.UserContact;
import com.project.demo.model.Users;
import com.project.demo.params.UserContactParams;
import com.project.demo.service.UserContactService;
import com.project.demo.service.UserService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author salimkun
 */
@RestController
@RequestMapping("/api/v1/contact/")
public class UserContactController {

    @Autowired
    private final UserContactService userContactService;

    UserContactController(UserContactService userContactService) {
        this.userContactService = userContactService;
    }

    @GetMapping("{id}")
    List<UserContact> findAllByUserId(@PathVariable Long id) {
        return userContactService.findAllContactByUser(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserContact addContact(@Valid @RequestBody UserContactParams data) throws UserException {
        return userContactService.addContact(data);
    }
    
    @PutMapping("{id}")
    public ResponseEntity<UserContact> updateContact(@PathVariable Long id, @Valid @RequestBody  UserContactParams data) {
        return userContactService.findContactById(id)
                .map(userObj -> {
                    userObj.setId(id);
                    userObj.setAddress(data.getAddress());
                    return ResponseEntity.ok(userContactService.updateContact(data.getUserId(), userObj));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<UserContact> deleteContact(@PathVariable Long id) {
        return userContactService.findContactById(id)
                .map(contact -> {
                    userContactService.deleteContactById(id);
                    return ResponseEntity.ok(contact);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
