/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.demo.params;

import javax.validation.constraints.NotEmpty;
import lombok.Data;

/**
 *
 * @author salimkun
 */
@Data
public class UserContactParams {
    @NotEmpty(message = "userId cannot be empty")
    String userId;
   
    @NotEmpty(message = "address cannot be empty")    
    String address;

}

