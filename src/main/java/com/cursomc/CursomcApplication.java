package com.cursomc;

import java.text.ParseException;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CursomcApplication {

    @PostConstruct
    void init () throws ParseException {
    }

    public static void main (final String[] args) {
        SpringApplication.run(CursomcApplication.class, args);
    }

}
