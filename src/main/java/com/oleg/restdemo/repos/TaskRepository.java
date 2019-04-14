package com.oleg.restdemo.repos;

import com.oleg.restdemo.models.Task;
import com.oleg.restdemo.models.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
     List<Task> findAllByUserName(String username);

     @Query(value = "SELECT * FROM tasks " +
             "WHERE extract(MONTH FROM creationDate) = :m " +
             "AND extract(DAY FROM creationDate) = :d " +
             "AND user_id = :id",
             nativeQuery = true)
     List<Task> findAllTodayTaskOfUser(@Param("m") int month, @Param("d") int day, @Param("id") Long id);
}
