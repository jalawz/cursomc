package com.cursomc.resources;

import com.cursomc.domain.Cliente;
import com.cursomc.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/clientes")
public class ClienteResource {

    private final ClienteService service;

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> find (@PathVariable final Integer id) {
        return ResponseEntity.ok(service.find(id));
    }
}