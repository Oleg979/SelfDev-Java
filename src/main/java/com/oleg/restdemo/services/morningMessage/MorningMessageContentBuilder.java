package com.oleg.restdemo.services.morningMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MorningMessageContentBuilder {
    private TemplateEngine templateEngine;

    @Autowired
    public MorningMessageContentBuilder(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    String build(String name) {
        Context context = new Context();
        context.setVariable("name", name);
        return templateEngine.process("MorningMessage", context);
    }
}