package com.kodeinc.authservice.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.kodeinc.authservice.exceptions.CustomExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

/**
 * @author Muyinda Rogers
 * @Date 2023-09-10
 * @Email moverr@gmail.com
 */

@Slf4j
public class BaseController<T> {

    ResponseEntity<T> errorToDispatch(ImmutablePair<HttpStatus, String> errorMessage) {
        if (!errorMessage.getLeft().equals(HttpStatus.OK)) {
            if (errorMessage.getLeft().equals(HttpStatus.BAD_REQUEST)) {
                CustomExceptionResponse exceptionResponse = CustomExceptionResponse.builder().
                        status(HttpStatus.BAD_REQUEST.toString()).
                        message(errorMessage.getValue()).
                        timestamp(LocalDateTime.now()).build();
                return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
            } else if (errorMessage.getLeft().equals(HttpStatus.CONFLICT)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        }
        return null;
    }

    ResponseEntity<JsonNode> errorToDispatchGetReconForCreate(ImmutablePair<HttpStatus, String> errorMessage) {
        if (!errorMessage.getLeft().equals(HttpStatus.OK)) {
            if (errorMessage.getLeft().equals(HttpStatus.BAD_REQUEST)) {
                CustomExceptionResponse exceptionResponse = CustomExceptionResponse.builder().
                        status(HttpStatus.BAD_REQUEST.toString()).
                        message(errorMessage.getValue()).
                        timestamp(LocalDateTime.now()).build();
                return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
            }
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return null;
    }



}
