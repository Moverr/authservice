package com.kodeinc.authservice.repositories;

import com.kodeinc.authservice.models.entities.User;
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
    @Query("SELECT U FROM users U  JOIN u.roles R JOIN u.projects  P   OFFSET :offset LIMIT :limit ")
    List<User> findUsers(@Param("query") String query, @Param("offset") long offset, @Param("limit") long limit);


    @Transactional(readOnly = true)
    @Query("SELECT U FROM users U  JOIN u.roles R JOIN u.projects  P where  R.id = :roleID OFFSET :offset LIMIT :limit ")
    List<User> findUsersByRole(@Param("query") String query,@Param("roleID") long roleId, @Param("offset") long offset, @Param("limit") long limit);

    @Transactional(readOnly = true)
    @Query("SELECT U FROM users U  JOIN u.roles R JOIN u.projects  P where  P.id = :roleID OFFSET :offset LIMIT :limit ")
    List<User> findUsersByProject(@Param("query") String query,@Param("roleID") long roleId, @Param("offset") long offset, @Param("limit") long limit);


}
