package com.oleg.restdemo.services;

import com.oleg.restdemo.models.Task;
import com.oleg.restdemo.repos.TaskRepository;
import com.oleg.restdemo.services.taskMessage.TaskMessageJob;
import com.oleg.restdemo.services.taskMessage.TaskMessageService;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.DateUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMessageService taskMessageService;
    private final Scheduler scheduler;

    @Autowired
    public TaskService(TaskRepository taskRepository, TaskMessageService taskMessageService, Scheduler scheduler) {
        this.taskRepository = taskRepository;
        this.taskMessageService = taskMessageService;
        this.scheduler = scheduler;
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

    public void sendTaskMessage(Task task) throws SchedulerException {
        JobDetail job1 = JobBuilder.newJob(TaskMessageJob.class)
                .withIdentity(task.getTitle(), "group1")
                .usingJobData("title", task.getTitle())
                .usingJobData("name", task.getUser().getName())
                .usingJobData("email", task.getUser().getEmail())
                .build();

        LocalDate date = LocalDate.now();
        String[] parts = task.getTime().split(":");

        Trigger trigger1 = TriggerBuilder.newTrigger()
                .withIdentity("cronTrigger" + task.getId(), "group1")
                .startAt(DateUtils.create(date.getYear(), date.getMonthValue(), date.getDayOfMonth(), parts[0], parts[1]).getTime())
                .build();

        scheduler.scheduleJob(job1, trigger1);
        scheduler.start();

    }
}
