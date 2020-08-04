package com.cursomc.service;

import java.util.List;

import com.cursomc.domain.Categoria;
import com.cursomc.domain.Produto;
import com.cursomc.repository.CategoriaRepository;
import com.cursomc.repository.ProdutoRepository;
import com.cursomc.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Produto find (final Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(
                        String.format("Objeto não encontrado! Id: %d, Tipo: %s", id, Produto.class.getName())));
    }

    public Page<Produto> search (final String nome, final List<Integer> ids, final Integer page,
            final Integer linesPerPage, final String orderBy, final String direction) {
        final PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);

        final List<Categoria> categorias = categoriaRepository.findAllById(ids);

        return repository.findDistinctByNomeIgnoreCaseContainingAndCategoriasIn(nome, categorias, pageRequest);
    }
}
