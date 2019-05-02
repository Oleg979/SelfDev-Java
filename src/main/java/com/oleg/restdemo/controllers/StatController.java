package com.oleg.restdemo.controllers;

import com.oleg.restdemo.models.Task;
import com.oleg.restdemo.repos.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("stats")
@CrossOrigin(origins="*")
public class StatController {
    private final TaskRepository taskRepository;

    @Autowired
    public StatController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping
    public Map<String, List<Task>> getStats(@AuthenticationPrincipal String name) {
        return taskRepository
                .findAllByUserName(name)
                .stream()
                .collect(Collectors.groupingBy(t -> t.getCreationDate().toString()));
    }
}
