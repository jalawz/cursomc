package com.cursomc.resources;

import java.net.URI;

import com.cursomc.domain.Categoria;
import com.cursomc.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RequiredArgsConstructor
@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

    private final CategoriaService service;

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> find (@PathVariable final Integer id) {
        return ResponseEntity.ok(service.buscar(id));
    }

    @PostMapping
    public ResponseEntity<Void> insert (@RequestBody final Categoria categoria) {
        final Categoria categoriaInserida = service.insert(categoria);
        final URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(categoriaInserida.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}