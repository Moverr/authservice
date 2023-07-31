package com.kodeinc.authservice.models.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SearchRequest {

    private String query;
    private int offset;
    private int limit;
    private String sortBy;
    private String sortType;

}
