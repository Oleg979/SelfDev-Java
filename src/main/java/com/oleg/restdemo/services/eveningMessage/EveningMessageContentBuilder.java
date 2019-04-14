package com.oleg.restdemo.services.eveningMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EveningMessageContentBuilder {
    private TemplateEngine templateEngine;

    @Autowired
    public EveningMessageContentBuilder(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    String build(String name) {
        Context context = new Context();
        context.setVariable("name", name);
        return templateEngine.process("EveningMessage", context);
    }
}
