package com.oleg.restdemo.services;

import com.oleg.restdemo.models.Task;
import com.oleg.restdemo.repos.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTodayTasks(String name) {
        LocalDate date = LocalDate.now();
        int day = date.getDayOfMonth();
        int month = date.getMonthValue();
        return taskRepository
                .findAllByUserName(name)
                .stream()
                .filter(t -> t.getCreationDate().getMonthValue() == month && t.getCreationDate().getDayOfMonth() == day)
                .collect(Collectors.toList());
    }
}
