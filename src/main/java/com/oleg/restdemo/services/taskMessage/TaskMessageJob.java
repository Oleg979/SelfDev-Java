package com.oleg.restdemo.services.taskMessage;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TaskMessageJob implements Job {
    private  TaskMessageService taskMessageService;

    @Autowired
    public void setTaskMessageService(TaskMessageService taskMessageService) {
        this.taskMessageService = taskMessageService;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        String title = dataMap.getString("title");
        String name = dataMap.getString("name");
        String email = dataMap.getString("email");
        taskMessageService.prepareAndSend(email, name, title);
    }
}


