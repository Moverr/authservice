package com.kodeinc.authservice.services.impl;

import com.kodeinc.authservice.models.dtos.exceptions.CustomBadRequestException;
import com.kodeinc.authservice.models.dtos.exceptions.CustomNotFoundException;
import com.kodeinc.authservice.models.dtos.requests.ProjectRequestDTO;
import com.kodeinc.authservice.models.dtos.requests.SearchRequestDTO;
import com.kodeinc.authservice.models.dtos.responses.CustomPage;
import com.kodeinc.authservice.models.dtos.responses.ProjectResponseDTO;
import com.kodeinc.authservice.models.entities.Project;
import com.kodeinc.authservice.repositories.ProjectRepository;
import com.kodeinc.authservice.services.BasicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
class ProjectServiceImpl implements BasicService<ProjectRequestDTO, ProjectResponseDTO, Project>{

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


    @Transactional
    @Override
    public ProjectResponseDTO update(long id, ProjectRequestDTO request) {
       Optional<Project> optionalProject =  repository.findById(id);
       if(optionalProject.isEmpty()){
           throw new CustomNotFoundException("Record does not exist");
       }


        List<Project> projectList =  repository.findByNameAndCodeAndNotID(id,request.getName(), request.getCode());
        if(!projectList.isEmpty()){
            throw new CustomBadRequestException("Project Already Exists");
        }


       Project project = optionalProject.get();
       Project newRecordUpdate  = populate(request);
       newRecordUpdate.setId(project.getId());
        return this.populate(repository.save(newRecordUpdate));
    }

    @Override
    public Optional<ProjectResponseDTO> getByID(long id){
        Optional<Project> optionalProject =  repository.findById(id);
        if(optionalProject.isEmpty()){
            throw new CustomNotFoundException("Record does not exist");
        }
        return Optional.of(populate(optionalProject.get()));
    }

    @Override
    public CustomPage<ProjectResponseDTO> list(SearchRequestDTO query) {
        Sort sort =   switch (query.getSortBy()){
            case "code" -> Sort.by("code") ;
           default -> Sort.by("name");
        };

        sort = switch (query.getSortType()){
          case "asc" -> sort.ascending();
          default -> sort.descending();
        };


        Pageable pageable = PageRequest.of(query.getOffset(), query.getLimit(),sort);
         Page<Project>  projects  = repository.findAll(pageable);

        List<ProjectResponseDTO> responses  =  projects.stream().map(this::populate).collect(Collectors.toList());

        CustomPage<ProjectResponseDTO> customResponse = new CustomPage<>();
        customResponse.setData(responses);
        customResponse.setPageNumber(projects.getNumber());
        customResponse.setPageSize(projects.getSize());
        customResponse.setPageNumber(projects.getNumber());

         return  customResponse;
    }

    @Override
    public void delete(long id) {
        Optional<Project> optionalProject =  repository.findById(id);
        if(optionalProject.isEmpty()){
            throw new CustomNotFoundException("Record does not exist");
        }
        Project project = optionalProject.get();
        //permanent delete
       repository.delete(project);
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
