package com.cursomc.resources;

import com.cursomc.domain.Categoria;
import com.cursomc.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

    private final CategoriaService service;

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> find (@PathVariable final Integer id) {
        return ResponseEntity.ok(service.buscar(id));
    }
}