package com.cursomc.service;

import com.cursomc.domain.Categoria;
import com.cursomc.repository.CategoriaRepository;
import com.cursomc.service.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author paulomenezes
 * @version : $<br/>
 * : $
 * @since 15/06/2020 16:50
 */
@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository repository;

    public Categoria buscar (final Integer id) {
        return repository.findById(id).orElseThrow(() ->
                new ObjectNotFoundException(
                        String.format("Objeto não encontrado! Id: %d, Tipo: %s", id, Categoria.class.getName())
                )
        );
    }
}
