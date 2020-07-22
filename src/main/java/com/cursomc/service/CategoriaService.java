package com.cursomc.service;

import java.util.List;

import com.cursomc.domain.Categoria;
import com.cursomc.repository.CategoriaRepository;
import com.cursomc.service.exception.DataIntegrityException;
import com.cursomc.service.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    public List<Categoria> findAll () {
        return repository.findAll();
    }

    public Categoria find (final Integer id) {
        return repository.findById(id).orElseThrow(() ->
                new ObjectNotFoundException(
                        String.format("Objeto não encontrado! Id: %d, Tipo: %s", id, Categoria.class.getName())
                )
        );
    }

    public Categoria insert (final Categoria categoria) {
        categoria.setId(null);
        return repository.save(categoria);
    }

    public Categoria update (final Categoria categoria) {
        final Categoria fromDB = this.find(categoria.getId());
        updateData(fromDB, categoria);
        return repository.save(fromDB);
    }

    public void delete (final Integer id) {
        this.find(id);
        try {
            repository.deleteById(id);
        } catch (final DataIntegrityViolationException ex) {
            throw new DataIntegrityException("Não é possível excluir uma categoria que possui Produtos");
        }
    }

    public Page<Categoria> findPageable (final Integer page, final Integer linesPerPage, final String orderBy,
            final String direction) {
        final PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repository.findAll(pageRequest);
    }

    private void updateData (final Categoria fromDB, final Categoria categoria) {
        fromDB.setNome(categoria.getNome());
    }
}
