package com.cursomc.resources;

import java.util.List;

import com.cursomc.domain.Categoria;
import com.cursomc.domain.Produto;
import com.cursomc.dto.CategoriaDTO;
import com.cursomc.dto.ProdutoDTO;
import com.cursomc.resources.utils.URL;
import com.cursomc.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/produtos")
public class ProdutoResource {

    private final ProdutoService service;

    @GetMapping("/{id}")
    public ResponseEntity<Produto> find (@PathVariable final Integer id) {
        return ResponseEntity.ok(service.find(id));
    }

    @GetMapping
    public ResponseEntity<Page<ProdutoDTO>> findPage (
            @RequestParam(value = "nome", defaultValue = "") final String nome,
            @RequestParam(value = "categorias", defaultValue = "") final String categorias,
            @RequestParam(value = "page", defaultValue = "0") final Integer page,
            @RequestParam(value = "size", defaultValue = "24") final Integer size,
            @RequestParam(value = "orderBy", defaultValue = "nome") final String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") final String direction) {

        final String nomeDecoded = URL.decodeParam(nome);
        final List<Integer> ids = URL.decodeIntList(categorias);
        final Page<Produto> produtos = service.search(nome, ids, page, size, orderBy, direction);
        final Page<ProdutoDTO> dtoList = produtos.map(ProdutoDTO::new);
        return ResponseEntity.ok(dtoList);
    }
}