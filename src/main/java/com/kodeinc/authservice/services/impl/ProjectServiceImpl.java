package com.kodeinc.authservice.services.impl;

import com.kodeinc.authservice.models.dtos.exceptions.CustomBadRequestException;
import com.kodeinc.authservice.models.dtos.requests.ProjectRequestDTO;
import com.kodeinc.authservice.models.dtos.requests.SearchRequestDTO;
import com.kodeinc.authservice.models.dtos.responses.ProjectResponseDTO;
import com.kodeinc.authservice.models.entities.Project;
import com.kodeinc.authservice.repositories.ProjectRepository;
import com.kodeinc.authservice.services.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Slf4j
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository repository;

    @Transactional
    @Override
    public ProjectResponseDTO create(ProjectRequestDTO request) throws CustomBadRequestException {
        log.info("Entered the create method");
       List<Project> projectList =  repository.findAllByNameAndCode(request.getName(), request.getCode());
       if(!projectList.isEmpty()){
           throw new CustomBadRequestException("Project Already Exists");
       }
        return populate(repository.save(populate(request)));

    }


    @Override
    public ProjectResponseDTO update(long id, ProjectRequestDTO request) {
        return null;
    }

    @Override
    public Page<ProjectResponseDTO> list(SearchRequestDTO query) {
        return null;
    }

    @Override
    public void archive(long id) {

    }

    private Project populate(ProjectRequestDTO request) {
        Project entity  = new Project();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setCallbackUrl(request.getCallbackUrl());
        return  entity;
    }


    private ProjectResponseDTO populate(Project entity) {
     return ProjectResponseDTO
                .builder()
                .id(entity.getId())
                .name(entity.getName())
                .code(entity.getCode())
                .callbackUrl(entity.getCallbackUrl())
                .build()  ;

    }



}
