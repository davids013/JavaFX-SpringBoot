package com.ampliconix.javafxweaverexample;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootExampleApplication {

    public static void main(String[] args) {
        //SpringApplication.run(JavafxWeaverExampleApplication.class, args);
        Application.launch(JavaFxApplication.class, args);
    }
}
