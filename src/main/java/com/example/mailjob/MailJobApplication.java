package com.example.mailjob;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan(value = "com.example.mailjob.listener")
public class MailJobApplication {

    public static void main(String[] args) {
        SpringApplication.run(MailJobApplication.class, args);
    }

}
