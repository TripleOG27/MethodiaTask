package org.detelin;

import org.detelin.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.ServiceLoader;

@SpringBootApplication
@ComponentScan("org.detelin")
public class Main {
    private final ApplicationService applicationService;

    @Autowired
    public Main(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class)) {
            ApplicationService applicationService = context.getBean(ApplicationService.class);

            applicationService.run();
        }
    }
}