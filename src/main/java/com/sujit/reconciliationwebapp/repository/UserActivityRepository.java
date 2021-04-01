package com.sujit.reconciliationwebapp.repository;


import com.sujit.reconciliationwebapp.model.UserActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserActivityRepository extends JpaRepository<UserActivity, Long> {

    @Query("Select ua from UserActivity ua where ua.username = ?1")
    Optional<UserActivity> findByUsername(String username);

    UserActivity findFirstByUsername(String username);
    List<UserActivity> findAll();


}
