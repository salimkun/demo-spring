/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.demo.service;

import com.project.demo.exception.ClientError;
import com.project.demo.exception.UserException;
import com.project.demo.model.Users;
import com.project.demo.repository.UsersRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author salimkun
 */
@Service
public class UserService {

    private final UsersRepository userRepository;

    @Autowired
    public UserService(UsersRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Users createUser(Users user) {
        Optional<Users> userOptional = userRepository.findByName(user.getName());
        if (userOptional.isPresent()) {
            throw new UserException(ClientError.DATA_INCOMPLETE, "User with name " + user.getName() + " already exists");
        }
        return userRepository.save (user);
    }


    public Users updateUser(Users user) {
        return userRepository.save(user);
    }

    public List<Users> findAllUsers() {
        return userRepository.findAll();
    }

    public Optional<Users> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }


}
