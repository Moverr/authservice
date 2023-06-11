package com.kodeinc.authservice.repositories;

import com.kodeinc.authservice.models.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Long> {

    @Transactional(readOnly=true)
    List<Project> findAllByNameAndCode(String name, String code);

    @Query("SELECT pr from Project pr where  pr.name like :name and pr.code like :code and pr.id <> :id")
    List<Project> findByNameAndCodeAndNotID(@Param("id") long id, @Param("name") String name, @Param("code") String code);

}
