package com.kodeinc.authservice.aop;

import com.kodeinc.authservice.exceptions.*;
import com.kodeinc.authservice.models.dtos.responses.ErrorResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.time.Instant;

@Slf4j
@ControllerAdvice
public class BaseControllerAdvice {

    @ExceptionHandler(CustomBadRequestException.class) // exception handled
    public ResponseEntity<ErrorResponseDTO> handleBadRequest(
            Exception e
    ) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        return getErrorResponseDTOResponseEntity(e, status,null);
    }



   @ExceptionHandler(CustomNotFoundException.class) // exception handled
    public ResponseEntity<ErrorResponseDTO> handleNotFoundRequest(
            Exception e
    ) {

        HttpStatus status = HttpStatus.NOT_FOUND;

        return getErrorResponseDTOResponseEntity(e, status,null);
    }


   @ExceptionHandler(CustomUnAuthorizedException.class) // exception handled
    public ResponseEntity<ErrorResponseDTO> handleNotAuthorizedRequest(
            Exception e
    ) {

        HttpStatus status = HttpStatus.UNAUTHORIZED;
        return getErrorResponseDTOResponseEntity(e, status,null);
    }

   @ExceptionHandler(CustomForbiddenRequestException.class) // exception handled
    public ResponseEntity<ErrorResponseDTO> handleForbiddenRequest(
            Exception e
    ) {

        HttpStatus status = HttpStatus.FORBIDDEN;

        return getErrorResponseDTOResponseEntity(e, status,null);
    }



    @ExceptionHandler(KhoodiUnAuthroizedException.class) // exception handled
    public ResponseEntity<ErrorResponseDTO> khoodiUnAuthorizedAccess(
            KhoodiUnAuthroizedException e
    ) {

        HttpStatus status = HttpStatus.UNAUTHORIZED;

        return getErrorResponseDTOResponseEntity(e, status,e.getBody());
    }


    @ExceptionHandler(Exception.class) // exception handled
    public ResponseEntity<ErrorResponseDTO> handleGenericError(
            Exception e
    ) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return getErrorResponseDTOResponseEntity(e, status,null);
    }




    private ResponseEntity<ErrorResponseDTO> getErrorResponseDTOResponseEntity(Exception e, HttpStatus status,String msgBody) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        String stackTrace = stringWriter.toString();

        log.info(e.getMessage());
        log.debug(stackTrace);

        ErrorResponseDTO error = ErrorResponseDTO.builder()
                .code(status.value())
                .message(e.getMessage() == null ? msgBody : e.getMessage() )
                .timestamp(Timestamp.from(Instant.now()))
                .status(String.valueOf(status.value()))
                .build();

        return new ResponseEntity<>(error ,  status );
    }





}
