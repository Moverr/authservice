package com.kodeinc.authservice.repositories;

import com.kodeinc.authservice.models.entities.User;
import com.kodeinc.authservice.models.entities.entityenums.GeneralStatusEnum;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

/**
 * @author Muyinda Rogers
 * @Date 2023-09-17
 * @Email moverr@gmail.com
 */
public interface UserRepository extends JpaRepository<User,Long> {
    @Transactional(readOnly = true)
    Optional<User> findByUsername(String username);

    long count();

    @Transactional(readOnly = true)
    @Query("SELECT U FROM User U where U.status like :status  ")
    List<User> findUsers( @Param("status") String status, Pageable pageable );



    @Transactional(readOnly = true)
    @Query("SELECT U FROM User U  JOIN U.roles R   where U.status like :status  AND  R.id = :roleID   ")
    List<User> findUsersRole(@Param("roleID") long roleID, @Param("status") String status, Pageable pageable );

    @Transactional(readOnly = true)
    @Query("SELECT U FROM User U   JOIN U.projects  P where U.status like :status  AND  P.id = :projectID ")
    List<User> findUsersProject(@Param("projectID") long projectID, @Param("status") String status, Pageable pageable );



}
