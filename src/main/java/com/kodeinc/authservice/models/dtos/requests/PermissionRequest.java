package com.kodeinc.authservice.models.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kodeinc.authservice.models.entities.entityenums.PermissionLevelEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissionRequest {

    @JsonProperty("create")
    @NotNull(message = "Create permission level   is mandatory ")
    private PermissionLevelEnum create;

    @JsonProperty("read")
    @NotNull(message = "Read permission level   is mandatory ")
    private PermissionLevelEnum read;

    @JsonProperty("delete")
    @NotNull(message = "Delete permission level   is mandatory ")
    private PermissionLevelEnum delete;

    @JsonProperty("update")
    @NotNull(message = "Update permission level   is mandatory ")
    private PermissionLevelEnum update;

    @JsonProperty("comment")
    @NotNull(message = "Comment permission level   is mandatory ")
    private PermissionLevelEnum comment;

    @JsonProperty("is_system")
    private boolean isSystem;

    @JsonProperty("resource_id")
    @NotNull(message = "Resource ID is mandatory ")
    private long resourceID;

}
