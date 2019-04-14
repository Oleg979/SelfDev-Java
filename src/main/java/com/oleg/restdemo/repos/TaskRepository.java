package com.oleg.restdemo.repos;

import com.oleg.restdemo.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
     List<Task> findAllByUserName(String username);
}
