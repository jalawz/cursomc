package com.cursomc.resources;

import com.cursomc.domain.Pedido;
import com.cursomc.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/pedidos")
public class PedidoResource {

    private final PedidoService service;

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> find (@PathVariable final Integer id) {
        return ResponseEntity.ok(service.find(id));
    }
}