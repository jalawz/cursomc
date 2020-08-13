package com.cursomc.service;

import java.util.Date;

import com.cursomc.domain.PagamentoComBoleto;
import com.cursomc.domain.Pedido;
import com.cursomc.domain.enums.EstadoPagamento;
import com.cursomc.repository.ItemPedidoRepository;
import com.cursomc.repository.PagamentoRepository;
import com.cursomc.repository.PedidoRepository;
import com.cursomc.service.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository repository;

    private final BoletoService boletoService;

    private final PagamentoRepository pagamentoRepository;

    private final ProdutoService produtoService;

    private final ClienteService clienteService;

    private final ItemPedidoRepository itemPedidoRepository;

    public Pedido find (final Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(
                        String.format("Objeto não encontrado! Id: %d, Tipo: %s", id, Pedido.class.getName())));
    }

    @Transactional
    public Pedido insert (final Pedido pedido) {
        pedido.setId(null);
        pedido.setInstante(new Date());
        pedido.setCliente(clienteService.find(pedido.getCliente().getId()));
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
            itemPedido.setProduto(produtoService.find(itemPedido.getProduto().getId()));
            itemPedido.setPreco(itemPedido.getProduto().getPreco());
            itemPedido.setPedido(pedidoSalvo);
        });
        itemPedidoRepository.saveAll(pedidoSalvo.getItens());
        System.out.println(pedidoSalvo);
        return pedidoSalvo;
    }
}
