package com.kodeinc.authservice.controllers;

import com.kodeinc.authservice.models.dtos.requests.ProjectRequestDTO;
import com.kodeinc.authservice.models.dtos.requests.SearchRequestDTO;
import com.kodeinc.authservice.models.dtos.responses.ProjectResponseDTO;
import com.kodeinc.authservice.services.ProjectService;
import com.kodeinc.authservice.services.impl.ProjectServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/projects")
@Validated
public class ProjectsController {


    @Autowired
    ProjectServiceImpl service;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectResponseDTO> create(@RequestBody @Valid ProjectRequestDTO request){
        ProjectResponseDTO responseDTO =    service.create(request);
        return  ResponseEntity.ok(responseDTO);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProjectResponseDTO>>  getList(
           @RequestParam(value="query",required = false) String query,
           @RequestParam(value="offset",defaultValue = "0")  int offset,
           @RequestParam(value="limit",defaultValue = "20")    int limit,
           @RequestParam(value="sort_by",defaultValue = "updated_at") String sortBy,
           @RequestParam(value="sort_type",defaultValue = "updated_at") String sortType

           ){
        SearchRequestDTO request = new SearchRequestDTO(query,offset,limit,sortBy,sortType);
        return ResponseEntity.ok(null);
    }

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectResponseDTO> getById(@PathVariable(value = "id") long id){
        return null;
    }


    @PutMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectResponseDTO> update(@PathVariable(value = "id") long id,@RequestBody ProjectRequestDTO request){
        return null;
    }

    @DeleteMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity delete(@PathVariable(value = "id") long id){
        return ResponseEntity.ok().build();
    }

}
