package com.cursomc;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import com.cursomc.domain.Categoria;
import com.cursomc.domain.Produto;
import com.cursomc.repository.CategoriaRepository;
import com.cursomc.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CursomcApplication {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @PostConstruct
    void init () {
        final Categoria cat1 = new Categoria(null, "Informática");
        final Categoria cat2 = new Categoria(null, "Escritório");

        final Produto p1 = new Produto(null, "Computador", 2000.0);
        final Produto p2 = new Produto(null, "Impressora", 800.0);
        final Produto p3 = new Produto(null, "Mouse", 80.0);

        cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
        cat2.getProdutos().addAll(Arrays.asList(p2));

        p1.getCategorias().addAll(Arrays.asList(cat1));
        p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
        p3.getCategorias().addAll(Arrays.asList(cat1));

        categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
        produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
    }

    public static void main (final String[] args) {
        SpringApplication.run(CursomcApplication.class, args);
    }

}
