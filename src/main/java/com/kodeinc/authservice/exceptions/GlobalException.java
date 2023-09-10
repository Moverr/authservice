package com.kodeinc.authservice.exceptions;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class GlobalException {
    private Date timestamp;
    private String trace;
    private String message;
    private String path;
}
