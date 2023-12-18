package com.kodeinc.authservice.models.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Muyinda Rogers
 * @Date 2023-12-17
 * @Email moverr@gmail.com
 */
@Getter
@Setter
public class PermissionSearchRequest  extends SearchRequest {

    @JsonProperty("resource_id")
    private Long resourceId;


    public PermissionSearchRequest(Long resourceId, String query, int offset, int limit, String sortBy, String sortType) {
        super(query, offset, limit, sortBy, sortType);
        this.resourceId = resourceId;
    }
}
