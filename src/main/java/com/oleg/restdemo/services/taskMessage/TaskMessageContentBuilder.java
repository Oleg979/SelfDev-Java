package com.oleg.restdemo.services.taskMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class TaskMessageContentBuilder {
    private TemplateEngine templateEngine;

    @Autowired
    public TaskMessageContentBuilder(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    String build(String name, String title) {
        Context context = new Context();
        context.setVariable("name", name);
        context.setVariable("title", title);
        return templateEngine.process("TaskMessage", context);
    }
}
