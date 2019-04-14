package com.oleg.restdemo;

import com.oleg.restdemo.models.Task;
import com.oleg.restdemo.models.ApplicationUser;
import com.oleg.restdemo.repos.TaskRepository;
import com.oleg.restdemo.repos.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class DBPreloader {
    @Bean
    CommandLineRunner initDatabase(TaskRepository taskRepository, UserRepository userRepository) {
        return args -> {
            log.info("Added to database: " + userRepository.save(new ApplicationUser("Oleg", "olegsolovev506@gmail.com", "$2a$10$BnUNQ/jkJh7gLXgLfoxHEu1xY.4yekhN8Xsh2X.jyd6iJ2A1NmKHG")));
        };
    }
}
