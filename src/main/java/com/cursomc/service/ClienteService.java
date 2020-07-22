package com.cursomc.service;

import java.util.List;

import javax.transaction.Transactional;

import com.cursomc.domain.Cliente;
import com.cursomc.repository.ClienteRepository;
import com.cursomc.repository.EnderecoRepository;
import com.cursomc.service.exception.DataIntegrityException;
import com.cursomc.service.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;

    private final EnderecoRepository enderecoRepository;

    public List<Cliente> findAll () {
        return repository.findAll();
    }

    public Cliente find (final Integer id) {
        return repository.findById(id).orElseThrow(() ->
                new ObjectNotFoundException(
                        String.format("Objeto não encontrado! Id: %d, Tipo: %s", id, Cliente.class.getName())
                )
        );
    }

    @Transactional
    public Cliente insert (final Cliente cliente) {
        cliente.setId(null);
        final Cliente dbCliente = repository.save(cliente);
        enderecoRepository.saveAll(dbCliente.getEnderecos());
        return dbCliente;
    }

    public Cliente update (final Cliente cliente) {
        final Cliente fromDb = this.find(cliente.getId());
        updateData(fromDb, cliente);
        return repository.save(fromDb);
    }

    public void delete (final Integer id) {
        this.find(id);
        try {
            repository.deleteById(id);
        } catch (final DataIntegrityViolationException ex) {
            throw new DataIntegrityException("Não é possível excluir um cliente que possui pedidos");
        }
    }

    public Page<Cliente> findPageable (final Integer page, final Integer linesPerPage, final String orderBy,
            final String direction) {
        final PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repository.findAll(pageRequest);
    }

    private void updateData (final Cliente fromDb, final Cliente cliente) {
        fromDb.setNome(cliente.getNome());
        fromDb.setEmail(cliente.getEmail());
    }
}
