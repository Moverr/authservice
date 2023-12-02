package com.kodeinc.authservice.aop;

import com.kodeinc.authservice.exceptions.*;
import com.kodeinc.authservice.models.dtos.responses.ErrorResponseDTO;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.ServletException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.time.Instant;

@Slf4j
@ControllerAdvice
public class BaseControllerAdvice {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponseDTO> handleForbiddenException(AccessDeniedException e) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        return getErrorResponseDTOResponseEntity(e, status,null);
    }


    @ExceptionHandler(ServletException.class)
    public ResponseEntity<ErrorResponseDTO> handleServletException(ServletException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return getErrorResponseDTOResponseEntity(e, status,null);

    }

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


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
       // DefaultErrorType errorType = new BindingErrorType(ErrorCode.INVALID_INPUT_VALUE, error.getBindingResult());

       // return new ErrorResponse(errorType).responseEntity();

        HttpStatus status = HttpStatus.UNAUTHORIZED;

        return getErrorResponseDTOResponseEntity(e, status,e.getMessage());

    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponseDTO> handleExpiredJwtException(ExpiredJwtException e) {
//        DefaultErrorType errorType = new DefaultErrorType(ErrorCode.JWT_EXPIRED);
//        return new ErrorResponse(errorType)
//                .responseEntity();

        HttpStatus status = HttpStatus.UNAUTHORIZED;

        return getErrorResponseDTOResponseEntity(e, status,e.getMessage());

    }







    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<ErrorResponseDTO> handleMalformedJwtException(MalformedJwtException e) {
//        DefaultErrorType errorType = new DefaultErrorType(ErrorCode.JWT_MALFORMED);
//        return new ErrorResponse(errorType)
//                .responseEntity();

        HttpStatus status = HttpStatus.UNAUTHORIZED;

        return getErrorResponseDTOResponseEntity(e, status,e.getMessage());

    }

    @ExceptionHandler(UnsupportedJwtException.class)
    public ResponseEntity<ErrorResponseDTO> handleUnsupportedJwtException(UnsupportedJwtException e) {
//        DefaultErrorType errorType = new DefaultErrorType(ErrorCode.JWT_UNSUPPORTED);
//        return new ErrorResponse(errorType)
//                .responseEntity();

        HttpStatus status = HttpStatus.UNAUTHORIZED;

        return getErrorResponseDTOResponseEntity(e, status,e.getMessage());


    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ErrorResponseDTO> handleSignatureException(SignatureException e) {
//        DefaultErrorType errorType = new DefaultErrorType(ErrorCode.JWT_SIG_INVALID);
//        return new ErrorResponse(errorType)
//                .responseEntity();

        HttpStatus status = HttpStatus.UNAUTHORIZED;

        return getErrorResponseDTOResponseEntity(e, status,e.getMessage());

    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorResponseDTO> handleJwtException(JwtException e) {
//        DefaultErrorType errorType = new DefaultErrorType(ErrorCode.JWT_EXCEPTION);
//        return new ErrorResponse(errorType)
//                .responseEntity();

        HttpStatus status = HttpStatus.UNAUTHORIZED;

        return getErrorResponseDTOResponseEntity(e, status,e.getMessage());



    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponseDTO> handleAuthenticationException(AuthenticationException e) {
//        DefaultErrorType errorType = new DefaultErrorType(ErrorCode.HANDLE_ACCESS_DENIED);
//        return new ErrorResponse(errorType).responseEntity();


        HttpStatus status = HttpStatus.UNAUTHORIZED;

        return getErrorResponseDTOResponseEntity(e, status,e.getMessage());


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
