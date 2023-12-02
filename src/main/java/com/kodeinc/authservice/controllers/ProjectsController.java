package com.kodeinc.authservice.controllers;

import com.kodeinc.authservice.configs.security.JwtUtils;
import com.kodeinc.authservice.models.dtos.requests.ProjectRequest;
import com.kodeinc.authservice.models.dtos.requests.SearchRequest;
import com.kodeinc.authservice.models.dtos.responses.CustomPage;
import com.kodeinc.authservice.models.dtos.responses.ProjectResponseDTO;
import com.kodeinc.authservice.models.entities.Project;
import com.kodeinc.authservice.services.BasicService;
import com.kodeinc.authservice.services.ProjectService;
import com.kodeinc.authservice.services.impl.ProjectServiceImpl;
import com.kodeinc.authservice.services.impl.RoleServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/projects")
@Validated
public class ProjectsController  extends BaseController<ProjectResponseDTO>{


    @Autowired
    ProjectService service;

    // CLIENT SHOULD HAVE PERMISSION PROJECTS
    // WILL ALSO LOOK AT ACCESS LEVEL OF CREATE


    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectResponseDTO> create(
            HttpServletRequest httpServletRequest,
            @RequestBody @Valid ProjectRequest request){
        return  ResponseEntity.ok(service.create(httpServletRequest,request));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomPage<ProjectResponseDTO>>  getList(
            HttpServletRequest httpServletRequest,
           @RequestParam(value="query",required = false) String query,
           @RequestParam(value="offset",defaultValue = "0")  int offset,
           @RequestParam(value="limit",defaultValue = "20")    int limit,
           @RequestParam(value="sort_by",defaultValue = "updated_at") String sortBy,
           @RequestParam(value="sort_type",defaultValue = "updated_at") String sortType
           ){
        SearchRequest request = new SearchRequest(query,offset,limit,sortBy,sortType);
        return ResponseEntity.ok( service.list(httpServletRequest,request));
    }

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectResponseDTO> getById( HttpServletRequest httpServletRequest,@PathVariable(value = "id") long id){
        return  ResponseEntity.ok(service.getByID(httpServletRequest,id));
    }


    @PutMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectResponseDTO> update( HttpServletRequest httpServletRequest,@PathVariable(value = "id") long id,@RequestBody ProjectRequest request){
        return  ResponseEntity.ok(service.update(httpServletRequest,id,request));
    }

    @DeleteMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity delete( HttpServletRequest httpServletRequest,@PathVariable(value = "id") long id){
        service.delete(httpServletRequest, id);
        return ResponseEntity.accepted().build();
    }

}
