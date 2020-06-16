package com.cursomc;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import com.cursomc.domain.Categoria;
import com.cursomc.domain.Cidade;
import com.cursomc.domain.Estado;
import com.cursomc.domain.Produto;
import com.cursomc.repository.CategoriaRepository;
import com.cursomc.repository.CidadeRepository;
import com.cursomc.repository.EstadoRepository;
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

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

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

        final Estado est1 = new Estado(null, "Minas Gerais");
        final Estado est2 = new Estado(null, "São Paulo");

        final Cidade c1 = new Cidade(null, "Uberlândia", est1);
        final Cidade c2 = new Cidade(null, "São Paulo", est2);
        final Cidade c3 = new Cidade(null, "Campinas", est2);

        est1.getCidades().addAll(Arrays.asList(c1));
        est2.getCidades().addAll(Arrays.asList(c2, c3));

        estadoRepository.saveAll(Arrays.asList(est1, est2));
        cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
    }

    public static void main (final String[] args) {
        SpringApplication.run(CursomcApplication.class, args);
    }

}
