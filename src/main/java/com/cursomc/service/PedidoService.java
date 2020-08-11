package com.cursomc.service;

import java.util.Date;

import com.cursomc.domain.PagamentoComBoleto;
import com.cursomc.domain.Pedido;
import com.cursomc.domain.enums.EstadoPagamento;
import com.cursomc.repository.ItemPedidoRepository;
import com.cursomc.repository.PagamentoRepository;
import com.cursomc.repository.PedidoRepository;
import com.cursomc.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private BoletoService boletoService;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    public Pedido find (final Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(
                        String.format("Objeto não encontrado! Id: %d, Tipo: %s", id, Pedido.class.getName())));
    }

    @Transactional
    public Pedido insert (final Pedido pedido) {
        pedido.setId(null);
        pedido.setInstante(new Date());
        pedido.getPagamento().setEstado(EstadoPagamento.PENDENTE);
        pedido.getPagamento().setPedido(pedido);

        if (pedido.getPagamento() instanceof PagamentoComBoleto) {
            final PagamentoComBoleto pagto = (PagamentoComBoleto) pedido.getPagamento();
            boletoService.preencherPagamentoComBoleto(pagto, pedido.getInstante());
        }
        final Pedido pedidoSalvo = repository.save(pedido);
        pagamentoRepository.save(pedido.getPagamento());

        pedido.getItens().forEach(itemPedido -> {
            itemPedido.setDesconto(0.0);
            itemPedido.setPreco(produtoService.find(itemPedido.getProduto().getId()).getPreco());
            itemPedido.setPedido(pedidoSalvo);
        });
        itemPedidoRepository.saveAll(pedidoSalvo.getItens());

        return pedidoSalvo;
    }
}
