package com.kodeinc.authservice.aop;

import com.kodeinc.authservice.models.dtos.exceptions.CustomBadRequestException;
import com.kodeinc.authservice.models.dtos.responses.ErrorResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;

@Slf4j
@ControllerAdvice
public class BaseControllerAdvice {
    @ExceptionHandler(CustomBadRequestException.class) // exception handled
    public ResponseEntity<ErrorResponseDTO> handleBadRequest(
            Exception e
    ) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        String stackTrace = stringWriter.toString();

        log.info(e.getMessage());
        log.debug(stackTrace);

        ErrorResponseDTO error = ErrorResponseDTO.builder()
                .code(status.hashCode())
                .message(e.getMessage())
                .build();

        return new ResponseEntity<>(  error
                 ,
                status
        );
    }
}
