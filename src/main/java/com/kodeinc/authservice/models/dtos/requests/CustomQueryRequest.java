package com.kodeinc.authservice.models.dtos.requests;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomQueryRequest {
    private String query;
    private long offset;
    private long limit;
    private  String sortBy;
    private String sort="DESC";
}
