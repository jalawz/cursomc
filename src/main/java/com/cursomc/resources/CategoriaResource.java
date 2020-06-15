package com.cursomc.resources;

import java.util.Arrays;
import java.util.List;

import com.cursomc.domain.Categoria;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

    @GetMapping
    public List<Categoria> listar () {
        final Categoria cat1 = new Categoria(1, "Informática");
        final Categoria cat2 = new Categoria(2, "Escritório");

        final List<Categoria> categorias = Arrays.asList(cat1, cat2);

        return categorias;
    }
}