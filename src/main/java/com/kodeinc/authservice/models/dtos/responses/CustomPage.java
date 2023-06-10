package com.kodeinc.authservice.models.dtos.responses;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CustomPage<T> {
    private long pageNumber;
    private long pageSize;
    private long totalElements;
    private long offset;
    private List<T> items;
}
