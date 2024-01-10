package com.kodeinc.authservice.controllers;

import com.kodeinc.authservice.models.dtos.requests.ProjectRequest;
import com.kodeinc.authservice.models.dtos.requests.SearchRequest;
import com.kodeinc.authservice.models.dtos.responses.CustomPage;
import com.kodeinc.authservice.models.dtos.responses.ProjectResponse;
import com.kodeinc.authservice.services.ProjectService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/projects")
@Validated
public class ProjectsController  extends BaseController<ProjectResponse>{


    @Autowired
    ProjectService service;

    // CLIENT SHOULD HAVE PERMISSION PROJECTS
    // WILL ALSO LOOK AT ACCESS LEVEL OF CREATE


    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectResponse> create(
            HttpServletRequest httpServletRequest,
            @RequestBody @Valid ProjectRequest request){
        return  ResponseEntity.ok(service.create(httpServletRequest,request));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomPage<ProjectResponse>>  getList(
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
    public ResponseEntity<ProjectResponse> getById(HttpServletRequest httpServletRequest, @PathVariable(value = "id") long id){
        return  ResponseEntity.ok(service.getByID(httpServletRequest,id));
    }


    @PutMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectResponse> update(HttpServletRequest httpServletRequest, @PathVariable(value = "id") long id, @RequestBody ProjectRequest request){
        return  ResponseEntity.ok(service.update(httpServletRequest,id,request));
    }

    @DeleteMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delete( HttpServletRequest httpServletRequest,@PathVariable(value = "id") long id){
        service.delete(httpServletRequest, id);
        return ResponseEntity.accepted().build();
    }

}
