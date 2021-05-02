/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.demo.service;

import com.project.demo.exception.ClientError;
import com.project.demo.exception.UserException;
import com.project.demo.model.UserContact;
import com.project.demo.model.Users;
import com.project.demo.params.UserContactParams;
import com.project.demo.repository.UserContactRepository;
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
public class UserContactService {
    private final UserContactRepository userContactRepository;
    
    private final UsersRepository userRepository;


    @Autowired
    public UserContactService(UserContactRepository userContactRepository, UsersRepository userRepository) {
        this.userContactRepository = userContactRepository;
        this.userRepository = userRepository;
    }

    public UserContact addContact(UserContactParams data) {
        Long userId;
        if (data.getUserId()==null || data.getUserId().isEmpty()){
             throw new UserException(ClientError.DATA_INCOMPLETE, "userId can't null or empty.");
        }
        
        if (data.getAddress()==null || data.getAddress().isEmpty()){
             throw new UserException(ClientError.DATA_INCOMPLETE, "address can't null or empty.");
        }
        try {
            userId = Long.parseLong(data.getUserId());
        } catch(NumberFormatException e){
            throw new UserException(ClientError.DATA_INCOMPLETE, "userId not valid.");
        }
        Optional<Users> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new UserException(ClientError.DATA_INCOMPLETE, "User not found.");
        }
        UserContact contact = new UserContact();
        contact.setAddress(data.getAddress());
        contact.setUser(userOptional.get());
        return userContactRepository.save (contact);
    }


    public UserContact updateContact(String id, UserContact contact) {
        Long userId;
        if (id==null || id.isEmpty()){
             throw new UserException(ClientError.DATA_INCOMPLETE, "userId can't null or empty.");
        }
        try {
            userId = Long.parseLong(id);
        } catch(NumberFormatException e){
            throw new UserException(ClientError.DATA_INCOMPLETE, "userId not valid.");
        }
        Optional<Users> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new UserException(ClientError.DATA_INCOMPLETE, "User not found.");
        } 
        if (!contact.getUser().equals(userOptional)){
            throw new UserException(ClientError.DATA_INCOMPLETE, "Can't access update this contact.");
        }
        return userContactRepository.save(contact);
    }

    public List<UserContact> findAllContactByUser(Long userId) {
        Optional<Users> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new UserException(ClientError.DATA_INCOMPLETE, "User not found.");
        }
        return userContactRepository.findByUser(userOptional.get());
    }

    public Optional<UserContact> findContactById(Long id) {
        return userContactRepository.findById(id);
    }

    public void deleteContactById(Long id) {
        userContactRepository.deleteById(id);
    }
  
}
