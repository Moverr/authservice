package com.kodeinc.authservice.controllers;

import com.kodeinc.authservice.models.dtos.requests.ProjectRequestDTO;
import com.kodeinc.authservice.models.dtos.requests.SearchRequestDTO;
import com.kodeinc.authservice.models.dtos.responses.CustomPage;
import com.kodeinc.authservice.models.dtos.responses.ProjectResponseDTO;
import com.kodeinc.authservice.services.impl.ProjectServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("v1/projects")
@Validated
public class ProjectsController {


    @Autowired
    ProjectServiceImpl service;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectResponseDTO> create(@RequestBody @Valid ProjectRequestDTO request){
        return  ResponseEntity.ok(service.create(request));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomPage<ProjectResponseDTO>>  getList(
           @RequestParam(value="query",required = false) String query,
           @RequestParam(value="offset",defaultValue = "0")  int offset,
           @RequestParam(value="limit",defaultValue = "20")    int limit,
           @RequestParam(value="sort_by",defaultValue = "updated_at") String sortBy,
           @RequestParam(value="sort_type",defaultValue = "updated_at") String sortType

           ){
        return ResponseEntity.ok( service.list(new SearchRequestDTO(query,offset,limit,sortBy,sortType)));
    }

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<ProjectResponseDTO>> getById(@PathVariable(value = "id") long id){
        return  ResponseEntity.ok(service.getByID(id));
    }


    @PutMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectResponseDTO> update(@PathVariable(value = "id") long id,@RequestBody ProjectRequestDTO request){
        return  ResponseEntity.ok(service.update(id,request));
    }

    @DeleteMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity delete(@PathVariable(value = "id") long id){
        service.delete(id);
        return ResponseEntity.accepted().build();
    }

}
