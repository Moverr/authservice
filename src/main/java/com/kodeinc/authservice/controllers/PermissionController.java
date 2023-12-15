package com.kodeinc.authservice.controllers;

import com.kodeinc.authservice.models.dtos.responses.PermissionResponse;
import com.kodeinc.authservice.models.dtos.responses.ProjectResponseDTO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Muyinda Rogers
 * @Date 2023-12-14
 * @Email moverr@gmail.com
 */

@RestController
@RequestMapping("/v1/permissions")
@Validated
public class PermissionController extends BaseController<PermissionResponse>{
}
