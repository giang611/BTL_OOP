package org.thuvien.Login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thuvien.JPA;

@Configuration
public class dataInit {
    @Autowired
    private JPA jpa;
    @Bean
    public CommandLineRunner getCommandLineRunner(){return args -> {};}
}
