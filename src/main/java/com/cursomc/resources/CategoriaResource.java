package com.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import com.cursomc.domain.Categoria;
import com.cursomc.dto.CategoriaDTO;
import com.cursomc.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RequiredArgsConstructor
@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

    private final CategoriaService service;

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> findAll () {
        final List<Categoria> categorias = service.findAll();
        final List<CategoriaDTO> dtoList = categorias.stream().map(CategoriaDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<CategoriaDTO>> findPage (
            @RequestParam(value = "page", defaultValue = "0") final Integer page,
            @RequestParam(value = "size", defaultValue = "3") final Integer size,
            @RequestParam(value = "orderBy", defaultValue = "nome") final String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") final String direction) {
        final Page<Categoria> categorias = service.findPageable(page, size, orderBy, direction);
        final Page<CategoriaDTO> dtoList = categorias.map(CategoriaDTO::new);
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> find (@PathVariable final Integer id) {
        return ResponseEntity.ok(service.find(id));
    }

    @PostMapping
    public ResponseEntity<Void> insert (@RequestBody final Categoria categoria) {
        final Categoria categoriaInserida = service.insert(categoria);
        final URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(categoriaInserida.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update (@PathVariable final Integer id, @RequestBody final Categoria categoria) {
        categoria.setId(id);
        service.update(categoria);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable final Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}