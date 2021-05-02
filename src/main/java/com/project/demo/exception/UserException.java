/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.demo.exception;

/**
 *
 * @author salimkun
 */

public class UserException extends RuntimeException {

     private final ClientError errorType;

    public UserException(ClientError errorType) {
        super(errorType.toString());
        this.errorType = errorType;
    }

    public UserException(ClientError errorType, String message) {
        super(message);
        this.errorType = errorType;
    }

    public UserException(ClientError errorType, Throwable cause) {
        super(errorType.toString(),cause);
        this.errorType = errorType;
    }

    public ClientError getErrorType() {
        return errorType;
    }
}
