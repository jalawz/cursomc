package com.cursomc.repository;

import java.util.List;

import com.cursomc.domain.Categoria;
import com.cursomc.domain.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

    @Transactional(readOnly = true)
    @Query("SELECT DISTINCT p FROM Produto p INNER JOIN p.categorias c WHERE p.nome LIKE %:nome% AND c IN :categorias")
    Page<Produto> search (@Param("nome") final String nome, @Param("categorias") List<Categoria> categorias,
            final Pageable pageRequest);

    @Transactional(readOnly = true)
    Page<Produto> findDistinctByNomeIgnoreCaseContainingAndCategoriasIn (final String nome,
            final List<Categoria> categorias, final Pageable pageRequest);
}
