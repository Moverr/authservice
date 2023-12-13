package com.kodeinc.authservice.controllers;

import com.kodeinc.authservice.models.dtos.requests.ProjectResourceRequest;
import com.kodeinc.authservice.models.dtos.requests.SearchRequest;
import com.kodeinc.authservice.models.dtos.responses.CustomPage;
import com.kodeinc.authservice.models.dtos.responses.ProjectResourceResponse;
import com.kodeinc.authservice.services.ProjectResourceService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Muyinda Rogers
 * @Date 2023-12-13
 * @Email moverr@gmail.com
 */

@RestController
@RequestMapping("/v1/projects/resources")
@Validated
public class ProjectsResourcesController extends BaseController<ProjectResourceResponse> {


    @Autowired
    ProjectResourceService service;

    // CLIENT SHOULD HAVE PERMISSION PROJECTS_RESOURCES
    // WILL ALSO LOOK AT ACCESS LEVEL OF CREATE


    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectResourceResponse> create(
            HttpServletRequest httpServletRequest,
            @RequestBody @Valid ProjectResourceRequest request){
        return  ResponseEntity.ok(service.create(httpServletRequest,request));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomPage<ProjectResourceResponse>>  getList(
            HttpServletRequest httpServletRequest,
            @RequestParam(value="query",required = false) String query,
            @RequestParam(value="offset",defaultValue = "0")  int offset,
            @RequestParam(value="limit",defaultValue = "20")    int limit,
            @RequestParam(value="sort_by",defaultValue = "id") String sortBy,
            @RequestParam(value="sort_type",defaultValue = "desc") String sortType
    ){
        SearchRequest request = new SearchRequest(query,offset,limit,sortBy,sortType);
        return ResponseEntity.ok( service.list(httpServletRequest,request));
    }

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectResourceResponse> getById( HttpServletRequest httpServletRequest,@PathVariable(value = "id") long id){
        return  ResponseEntity.ok(service.getByID(httpServletRequest,id));
    }


    @PutMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectResourceResponse> update( HttpServletRequest httpServletRequest,@PathVariable(value = "id") long id,@RequestBody ProjectResourceRequest request){
        return  ResponseEntity.ok(service.update(httpServletRequest,id,request));
    }

    @DeleteMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delete( HttpServletRequest httpServletRequest,@PathVariable(value = "id") long id){
        service.delete(httpServletRequest, id);
        return ResponseEntity.accepted().build();
    }

}
