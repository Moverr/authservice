package com.kodeinc.authservice.services;

import com.kodeinc.authservice.models.dtos.requests.ProjectRequestDTO;
import com.kodeinc.authservice.models.dtos.requests.SearchRequestDTO;
import com.kodeinc.authservice.models.dtos.responses.CustomPage;
import com.kodeinc.authservice.models.dtos.responses.RoleResponseDTO;

import java.util.Optional;

 interface BasicService <T,R,X>{


    R create(T request);
    R update(long id,T request);
    CustomPage<R> list(SearchRequestDTO query);
    Optional<R> getByID(long id);
    void delete(long id);


}
