package com.asot.springbootdemo1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.asot.springbootdemo1",
        "com.asot.shared"
})
@EntityScan(basePackages = {
        "com.asot.springbootdemo1.model",
        "com.asot.shared.model"
})
@EnableJpaRepositories(basePackages = {
        "com.asot.shared.repository"
})
public class Springbootdemo1Application {

    public static void main(String[] args) {
        SpringApplication.run(Springbootdemo1Application.class, args);
    }

}
