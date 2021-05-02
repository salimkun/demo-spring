/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.demo.exception;

import com.project.demo.model.ErrorEnvelope;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author salimkun
 */
@ControllerAdvice
public class GlobalHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    public final ResponseEntity<ErrorEnvelope> handleMethodArgumentNotValid(
            HttpServletRequest req, MethodArgumentNotValidException ex) {

        String messageError = ex.getBindingResult().getFieldError().getDefaultMessage();
        if (messageError.isEmpty()) {
            messageError = ex.getBindingResult().getFieldError().toString();
        }
        ErrorEnvelope env = new ErrorEnvelope(
                HttpStatus.UNPROCESSABLE_ENTITY.value(), messageError);

        return new ResponseEntity<>(env, HttpStatus.valueOf(env.getCode()));
    }
    
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorEnvelope> handleMissingParams(MissingServletRequestParameterException ex) {
        ErrorEnvelope env = new ErrorEnvelope(HttpStatus.UNPROCESSABLE_ENTITY.value(), ex.getMessage());

        return new ResponseEntity<>(env, HttpStatus.valueOf(env.getCode()));
    }

    @ExceptionHandler({UserException.class})
    public final ResponseEntity<ErrorEnvelope> handleRestifierException(UserException ex, HttpServletRequest req) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        switch (ex.getErrorType()) {
            case DATA_INCOMPLETE:
                status = HttpStatus.UNPROCESSABLE_ENTITY;
                break;
            case EMAIL_NOT_EXIST:
                status = HttpStatus.UNPROCESSABLE_ENTITY;
                break;
            case PASSWORD_WRONG:
                status = HttpStatus.UNPROCESSABLE_ENTITY;
                break;
            case INVALID_CREDENTIAL:
                status = HttpStatus.UNAUTHORIZED;
        }

        ErrorEnvelope env = new ErrorEnvelope(status.value(), ex.getMessage());

//        ErrorPayload payload = new ErrorPayload(req.getRequestURI(),req.getMethod(),ex.getClass().getCanonicalName(),ex.getMessage());
//        if (ex.getCause() != null) {
//            payload.setCause(ex.getCause().getMessage());
//        }
//        
//        env.setError(payload);
        return new ResponseEntity<>(env, status);

    }

    @ExceptionHandler(MultipartException.class)
    public final ResponseEntity<ErrorEnvelope> handleError1(MultipartException e, RedirectAttributes redirectAttributes) {
        ErrorEnvelope env = new ErrorEnvelope(HttpStatus.UNPROCESSABLE_ENTITY.value(), "file format not valid.");

        return new ResponseEntity<>(env, HttpStatus.valueOf(env.getCode()));
    }

    @ExceptionHandler({DataAccessException.class})
    public final ResponseEntity<ErrorEnvelope> handleDataAccessException(DataAccessException ex, HttpServletRequest req) {

        ErrorEnvelope env = new ErrorEnvelope(HttpStatus.UNPROCESSABLE_ENTITY.value(), ex.getMessage());

//        ErrorPayload payload = new ErrorPayload(req.getRequestURI(),req.getMethod(),ex.getClass().getCanonicalName(),ex.getMessage());
//        if (ex.getCause() != null) {
//            payload.setCause(ex.getCause().getMessage());
//        }
//        
//        env.setError(payload);
        return new ResponseEntity<>(env, HttpStatus.valueOf(env.getCode()));

    }

    @ExceptionHandler({ConstraintViolationException.class})
    public final ResponseEntity<ErrorEnvelope> handleDataValidationException(ConstraintViolationException ex, HttpServletRequest req) {
        ErrorEnvelope env = new ErrorEnvelope(HttpStatus.UNPROCESSABLE_ENTITY.value(), ex.getMessage());

//        ErrorPayload payload = new ErrorPayload(req.getRequestURI(),req.getMethod(),ex.getClass().getCanonicalName());
//        
//        List<FieldViolation> violations = ex.getConstraintViolations().stream().map(FieldViolation::of).collect(Collectors.toList());
//        payload.setViolations(violations);
//        
//        if (ex.getCause() != null) {
//            payload.setCause(ex.getCause().getMessage());
//        }
//        
//        env.setError(payload);
        return new ResponseEntity<>(env, HttpStatus.valueOf(env.getCode()));

    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public final ResponseEntity<ErrorEnvelope> handleBadInputData(HttpMessageNotReadableException ex, HttpServletRequest req) {
        ErrorEnvelope env = new ErrorEnvelope(HttpStatus.BAD_REQUEST.value(), ex.getCause().getMessage());
        return new ResponseEntity<>(env, HttpStatus.valueOf(env.getCode()));

    }

}

