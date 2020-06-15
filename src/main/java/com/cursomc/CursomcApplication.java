package com.cursomc;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import com.cursomc.domain.Categoria;
import com.cursomc.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CursomcApplication {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @PostConstruct
    void init () {
        final Categoria cat1 = new Categoria(null, "Informática");
        final Categoria cat2 = new Categoria(null, "Escritório");

        categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
    }

    public static void main (String[] args) {
        SpringApplication.run(CursomcApplication.class, args);
    }

}
