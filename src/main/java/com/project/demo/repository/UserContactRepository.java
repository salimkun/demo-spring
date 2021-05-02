/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.demo.repository;

import com.project.demo.model.UserContact;
import com.project.demo.model.Users;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author salimkun
 */

@Repository
public interface UserContactRepository extends JpaRepository<UserContact, Long> {
    List<UserContact> findByUser(Users user);
}