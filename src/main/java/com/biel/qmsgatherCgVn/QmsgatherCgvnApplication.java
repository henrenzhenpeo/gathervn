package com.biel.qmsgatherCgVn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class QmsgatherCgvnApplication {

    public static void main(String[] args) {
        SpringApplication.run(QmsgatherCgvnApplication.class, args);
    }

}
