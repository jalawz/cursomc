package com.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.cursomc.domain.Cliente;
import com.cursomc.dto.ClienteDTO;
import com.cursomc.dto.ClienteNewDTO;
import com.cursomc.service.ClienteService;
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
@RequestMapping("/clientes")
public class ClienteResource {

    private final ClienteService service;

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> findAll () {
        final List<Cliente> Clientes = service.findAll();
        final List<ClienteDTO> dtoList = Clientes.stream().map(ClienteDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<ClienteDTO>> findPage (
            @RequestParam(value = "page", defaultValue = "0") final Integer page,
            @RequestParam(value = "size", defaultValue = "3") final Integer size,
            @RequestParam(value = "orderBy", defaultValue = "nome") final String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") final String direction) {
        final Page<Cliente> Clientes = service.findPageable(page, size, orderBy, direction);
        final Page<ClienteDTO> dtoList = Clientes.map(ClienteDTO::new);
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> find (@PathVariable final Integer id) {
        return ResponseEntity.ok(service.find(id));
    }

    @PostMapping
    public ResponseEntity<Void> insert (@Valid @RequestBody final ClienteNewDTO request) {
        final Cliente clienteInserido = service.insert(Cliente.of(request));
        final URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(clienteInserido.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update (@PathVariable final Integer id,
            @Valid @RequestBody final ClienteDTO request) {
        final Cliente cliente = Cliente.of(request);
        cliente.setId(id);
        service.update(cliente);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable final Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}