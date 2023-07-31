package com.kodeinc.authservice.services;

import com.kodeinc.authservice.models.dtos.requests.SearchRequest;
import com.kodeinc.authservice.models.dtos.responses.CustomPage;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
 public  interface BasicService <T,R,X>{


    R create(T request);
    R update(long id,T request);
    CustomPage<R> list(SearchRequest query);
    Optional<R> getByID(long id);
    void delete(long id);


}
