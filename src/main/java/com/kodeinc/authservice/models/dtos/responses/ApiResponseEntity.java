package com.kodeinc.authservice.models.dtos.responses;

import com.kodeinc.authservice.exceptions.GlobalException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;

public final class ApiResponseEntity {

    private ApiResponseEntity() {
    }

    private static <T> ResponseEntity<ApiResponse<T>> generateResponse(T body, String message, boolean success, HttpStatus httpStatus) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setStatus(success);
        if (success) {
            response.setSuccessCode(httpStatus.value());
            response.setSuccessDescription(message);
        } else {
            response.setErrorCode(httpStatus.value());
            response.setErrorDescription(message);
        }
        if (body != null) {
            response.setData(body);
        }
        return ResponseEntity.status(httpStatus).body(response);
    }

    public static <T> ResponseEntity<ApiResponse<T>> ok(T body, String successDescription) {
        return generateResponse(body, successDescription, true, HttpStatus.OK);
    }

    public static <T> ResponseEntity<ApiResponse<T>> ok(String successDescription) {
        return ok(null, successDescription);
    }

    public static <T> ResponseEntity<ApiResponse<T>> serverError(T body, String errorDescription) {
        return generateResponse(body, errorDescription, false, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static <T> ResponseEntity<ApiResponse<T>> serverError(String errorDescription) {
        return serverError(null, errorDescription);
    }

    public static ResponseEntity<ApiResponse<GlobalException>> fatal(Throwable ex, String path) {
        ApiResponse<GlobalException> response = new ApiResponse<>();
        response.setStatus(false);
        response.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setErrorDescription(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        GlobalException body =   GlobalException.builder()
                .timestamp(new Date())
                .message(ex.getMessage())
                .path(path)
                .trace(ExceptionUtils.getStackTrace(ex))
                .build();

        response.setData(body);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    public static <T> ResponseEntity<ApiResponse<T>> created(T body, String successDescription) {
        return generateResponse(body, successDescription, true, HttpStatus.CREATED);
    }

    public static <T> ResponseEntity<ApiResponse<T>> created(String successDescription) {
        return created(null, successDescription);
    }

    public static <T> ResponseEntity<ApiResponse<T>> accepted(T body, String successDescription) {
        return generateResponse(body, successDescription, true, HttpStatus.ACCEPTED);
    }

    public static <T> ResponseEntity<ApiResponse<T>> accepted(String successDescription) {
        return accepted(null, successDescription);
    }

    public static <T> ResponseEntity<ApiResponse<T>> notFound(T body, String description) {
        return generateResponse(body, description, false, HttpStatus.NOT_FOUND);
    }

    public static <T> ResponseEntity<ApiResponse<T>> notFound(String description) {
        return notFound(null, description);
    }

    public static <T> ResponseEntity<ApiResponse<T>> badRequest(T body, String description) {
        return generateResponse(body, description, false, HttpStatus.BAD_REQUEST);
    }

    public static <T> ResponseEntity<ApiResponse<T>> badRequest(String description) {
        return badRequest(null, description);
    }

    public static <T> ResponseEntity<ApiResponse<T>> forbidden(T body, String description) {
        return generateResponse(body, description, false, HttpStatus.FORBIDDEN);
    }

    public static <T> ResponseEntity<ApiResponse<T>> forbidden(String description) {
        return forbidden(null, description);
    }

    public static <T> ResponseEntity<ApiResponse<T>> unAuthorized(T body, String description) {
        return generateResponse(body, description, false, HttpStatus.UNAUTHORIZED);
    }

    public static <T> ResponseEntity<ApiResponse<T>> unAuthorized(String description) {
        return unAuthorized(null, description);
    }

    public static <T> ResponseEntity<ApiResponse<T>> conflict(T body, String description) {
        return generateResponse(body, description, false, HttpStatus.CONFLICT);
    }

    public static <T> ResponseEntity<ApiResponse<T>> conflict(String description) {
        return conflict(null, description);
    }
}
