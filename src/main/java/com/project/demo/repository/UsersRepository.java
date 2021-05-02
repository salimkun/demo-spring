/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.project.demo.model.Users;
import java.util.Optional;
/**
 *
 * @author salimkun
 */
@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByName(String name);
}
