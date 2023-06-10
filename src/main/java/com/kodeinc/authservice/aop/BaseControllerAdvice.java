package com.kodeinc.authservice.aop;

import com.kodeinc.authservice.models.dtos.exceptions.CustomBadRequestException;
import com.kodeinc.authservice.models.dtos.exceptions.CustomForbiddenRequestException;
import com.kodeinc.authservice.models.dtos.exceptions.CustomNotFoundException;
import com.kodeinc.authservice.models.dtos.exceptions.CustomUnAuthorizedException;
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

        HttpStatus status = HttpStatus.NOT_FOUND;
        return getErrorResponseDTOResponseEntity(e, status);
    }



    @ExceptionHandler(CustomNotFoundException.class) // exception handled
    public ResponseEntity<ErrorResponseDTO> handleNotFoundRequest(
            Exception e
    ) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        return getErrorResponseDTOResponseEntity(e, status);
    }


    @ExceptionHandler(CustomUnAuthorizedException.class) // exception handled
    public ResponseEntity<ErrorResponseDTO> handleNotAuthorizedRequest(
            Exception e
    ) {

        HttpStatus status = HttpStatus.UNAUTHORIZED;
        return getErrorResponseDTOResponseEntity(e, status);
    }

    @ExceptionHandler(CustomForbiddenRequestException.class) // exception handled
    public ResponseEntity<ErrorResponseDTO> handleForbiddenRequest(
            Exception e
    ) {

        HttpStatus status = HttpStatus.FORBIDDEN;
        return getErrorResponseDTOResponseEntity(e, status);
    }


    @ExceptionHandler(Exception.class) // exception handled
    public ResponseEntity<ErrorResponseDTO> handleGenericError(
            Exception e
    ) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return getErrorResponseDTOResponseEntity(e, status);
    }




    private ResponseEntity<ErrorResponseDTO> getErrorResponseDTOResponseEntity(Exception e, HttpStatus status) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        String stackTrace = stringWriter.toString();

        log.info(e.getMessage());
        log.debug(stackTrace);

        ErrorResponseDTO error = ErrorResponseDTO.builder()
                .code(status.hashCode())
                .message(e.getMessage())
                .timestamp(Timestamp.from(Instant.now()))
                .build();

        return new ResponseEntity<>(error ,  status );
    }





}
