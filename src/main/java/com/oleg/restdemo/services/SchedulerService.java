package com.oleg.restdemo.services;

import com.oleg.restdemo.models.ApplicationUser;
import com.oleg.restdemo.repos.TaskRepository;
import com.oleg.restdemo.repos.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SchedulerService {
    private  final String MORNING_CRON = "00 30 7 * * *";
    private static final String EVENING_CRON = "00 30 23 * * *";

    private final MorningMessageService morningMessageService;
    private final EveningMessageService eveningMessageService;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public SchedulerService(MorningMessageService morningMessageService,
                            EveningMessageService eveningMessageService,
                            UserRepository userRepository,
                            TaskRepository taskRepository) {
        this.morningMessageService = morningMessageService;
        this.eveningMessageService = eveningMessageService;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    @Scheduled(cron = MORNING_CRON)
    public void sendMorningMessage() {
        List<ApplicationUser> users = userRepository.findAll();
        users.forEach(user -> {
            morningMessageService.prepareAndSend(user.getEmail(), user.getName());
            log.info("Morning message sent to " + user.getName());
        });
    }

    @Scheduled(cron = EVENING_CRON)
    public void sendEveningMessage() {
        List<ApplicationUser> users = userRepository.findAll();
        users.forEach(user -> {
            eveningMessageService.prepareAndSend(user.getEmail(), user.getName());
            log.info("Evening message sent to " + user.getName());
        });
    }
}
