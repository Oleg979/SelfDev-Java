package com.oleg.restdemo.controllers;

import com.oleg.restdemo.exceptions.TaskNotFoundException;
import com.oleg.restdemo.models.ApplicationUser;
import com.oleg.restdemo.models.Task;
import com.oleg.restdemo.repos.TaskRepository;
import com.oleg.restdemo.repos.UserRepository;
import com.oleg.restdemo.services.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("tasks")
@Slf4j
public class TaskController {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskService taskService;

    private boolean hasNoAccessToTask(String name, Task task) {
        return !task.getUser().equals(userRepository.findByName(name).get());
    }

    @Autowired
    public TaskController(TaskRepository taskRepository, UserRepository userRepository, TaskService taskService) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> getAllTasks(@AuthenticationPrincipal String name) {
        return taskRepository.findAllByUserName(name);
    }

    @GetMapping("{id}")
    public Task getTaskById(@PathVariable Long id, @AuthenticationPrincipal String name) throws IllegalAccessException {
        Task task = taskRepository
                .findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        if(hasNoAccessToTask(name, task))
            throw new IllegalAccessException("You have no access to this task");
        return task;
    }

    @PostMapping
    public Task addTask(@RequestBody Task task, @AuthenticationPrincipal String name) {
        ApplicationUser user = userRepository.findByName(name).get();
        task.setUser(user);
        task.setCreationDate(LocalDate.now());
        user.getTasks().add(task);
        return taskRepository.save(task);
    }

    @PutMapping
    public Task editTask(@RequestBody Task task, @AuthenticationPrincipal String name) throws IllegalAccessException {
        Task t = taskRepository
                .findById(task.getId())
                .orElseThrow(() -> new TaskNotFoundException(task.getId()));
        if(hasNoAccessToTask(name, task))
            throw new IllegalAccessException("You have no access to this task");
         t.setTag(task.getTag());
         t.setTitle(task.getTitle());
         t.setTime(task.getTime());
         return taskRepository.save(t);
    }

    @DeleteMapping("{id}")
    public Task deleteTask(@PathVariable Long id, @AuthenticationPrincipal String name) throws IllegalAccessException {
        Task task = taskRepository
                .findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        if(hasNoAccessToTask(name, task))
            throw new IllegalAccessException("You have no access to this task");
        taskRepository.deleteById(id);
        return task;
    }

    @GetMapping("check/{id}")
    public Task checkTask(@PathVariable Long id, @AuthenticationPrincipal String name) throws IllegalAccessException {
        Task task = taskRepository
                .findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        if(hasNoAccessToTask(name, task))
            throw new IllegalAccessException("You have no access to this task");
        task.setChecked(true);
        taskRepository.save(task);
        return task;
    }

    @GetMapping("uncheck/{id}")
    public Task uncheckTask(@PathVariable Long id, @AuthenticationPrincipal String name) throws IllegalAccessException {
        Task task = taskRepository
                .findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        if(hasNoAccessToTask(name, task))
            throw new IllegalAccessException("You have no access to this task");
        task.setChecked(false);
        taskRepository.save(task);
        return task;
    }

    @GetMapping("today")
    public List<Task> getTodayTasks(@AuthenticationPrincipal String name) {
       return taskService.getAllTodayTasks(name);
    }
}
