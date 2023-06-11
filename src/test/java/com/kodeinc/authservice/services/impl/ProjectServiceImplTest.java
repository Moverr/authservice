package com.kodeinc.authservice.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kodeinc.authservice.models.dtos.exceptions.CustomBadRequestException;
import com.kodeinc.authservice.models.dtos.exceptions.CustomNotFoundException;
import com.kodeinc.authservice.models.dtos.requests.ProjectRequestDTO;
import com.kodeinc.authservice.models.dtos.responses.ProjectResponseDTO;
import com.kodeinc.authservice.models.entities.Project;
import com.kodeinc.authservice.repositories.ProjectRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mapping.model.Property;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectServiceImplTest {


    ObjectMapper mapper;
    @Mock
    ProjectRepository repository;

    @InjectMocks
    ProjectServiceImpl projectService;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void givenInputThrowPropertyExistsException() throws JsonProcessingException {

        ProjectRequestDTO request =   mapper.readValue(getProperty(), ProjectRequestDTO.class);

        Project p = new Project();
        p.setId(1L);
        List<Project> propertyList = new ArrayList<>();
        propertyList.add(p);
        when(repository.findAllByNameAndCode(any(),any())).thenReturn(propertyList);
        CustomBadRequestException exception = assertThrows(CustomBadRequestException.class,()->{
            projectService.create(request);
        });
        String expectedMessage = "Project Already Exists";
        String actualMessage = exception.getMessage();
        assertEquals(actualMessage,expectedMessage);
    }


    @Test
    void givenInputSaveRecord() throws JsonProcessingException {

        ProjectRequestDTO request =   mapper.readValue(getProperty(), ProjectRequestDTO.class);

        Project p = new Project();
        p.setId(1L);
        p.setName(request.getName());
        p.setCode(request.getCode());
        p.setCallbackUrl(request.getCallbackUrl());

        List<Project> propertyList = new ArrayList<>();
        when(repository.findAllByNameAndCode(any(),any())).thenReturn(propertyList);
        when(repository.save(any())).thenReturn(p);

        ProjectResponseDTO response  =  projectService.create(request);
        assertEquals(response.getId(),p.getId());

    }

    @Test
    void givenInputOnUpdateThrowPropertyDoesNotExistsException() throws JsonProcessingException {

        ProjectRequestDTO request =   mapper.readValue(getProperty(), ProjectRequestDTO.class);

        when(repository.findById(any())).thenReturn(Optional.empty());
        CustomNotFoundException exception = assertThrows(CustomNotFoundException.class,()->{
            projectService.update(1l,request);
        });
        String expectedMessage = "Record does not exist";
        String actualMessage = exception.getMessage();
        assertEquals(actualMessage,expectedMessage);
    }
    @Test
    void givenInputOnUpdateThrowPropertyUniqueConstraintException() throws JsonProcessingException {

        ProjectRequestDTO request =   mapper.readValue(getProperty(), ProjectRequestDTO.class);

        Project p = new Project();
        p.setId(1L);
        List<Project> propertyList = new ArrayList<>();
        propertyList.add(p);

        when(repository.findById(any())).thenReturn(Optional.of(p));
        when(repository.findByNameAndCodeAndNotID(any(),any(),any())).thenReturn(propertyList);

        CustomBadRequestException exception = assertThrows(CustomBadRequestException.class,()->{
            projectService.update(1l,request);
        });
        String expectedMessage = "Project Already Exists";
        String actualMessage = exception.getMessage();
        assertEquals(actualMessage,expectedMessage);
    }
    @Test
    void getByID() {
    }

    @Test
    void list() {
    }

    @Test
    void delete() {
    }

    private String getProperty(){
        return  "{\n" +
                "  \"name\": \"string\",\n" +
                "  \"code\": \"frrr\",\n" +
                "  \"callback_url\": \"string\"\n" +
                "}";
    }
}