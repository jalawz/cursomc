package com.cursomc.resources;

import java.net.URI;

import javax.validation.Valid;

import com.cursomc.domain.Categoria;
import com.cursomc.domain.Pedido;
import com.cursomc.dto.CategoriaDTO;
import com.cursomc.service.PedidoService;
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
@RequestMapping("/pedidos")
public class PedidoResource {

    private final PedidoService service;

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> find (@PathVariable final Integer id) {
        return ResponseEntity.ok(service.find(id));
    }

    @PostMapping
    public ResponseEntity<Void> insert (@Valid @RequestBody final Pedido request) {
        final Pedido pedidoInserido = service.insert(request);
        final URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(pedidoInserido.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}