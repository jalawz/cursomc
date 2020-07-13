package com.cursomc.service;

import com.cursomc.domain.Pedido;
import com.cursomc.repository.PedidoRepository;
import com.cursomc.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

    public Pedido buscar (final Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(
                        String.format("Objeto não encontrado! Id: %d, Tipo: %s", id, Pedido.class.getName())));
    }
}
