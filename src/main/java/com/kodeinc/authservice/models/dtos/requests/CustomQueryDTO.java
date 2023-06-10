package com.kodeinc.authservice.models.dtos.requests;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CustomQueryDTO {
    private String query;
    private long offset;
    private long limit;
    private  String sortBy;
    private String sort="DESC";
}
