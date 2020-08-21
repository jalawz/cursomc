package com.cursomc.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import com.cursomc.domain.Categoria;
import com.cursomc.domain.Cidade;
import com.cursomc.domain.Cliente;
import com.cursomc.domain.Endereco;
import com.cursomc.domain.Estado;
import com.cursomc.domain.ItemPedido;
import com.cursomc.domain.Pagamento;
import com.cursomc.domain.PagamentoComBoleto;
import com.cursomc.domain.PagamentoComCartao;
import com.cursomc.domain.Pedido;
import com.cursomc.domain.Produto;
import com.cursomc.domain.enums.EstadoPagamento;
import com.cursomc.domain.enums.TipoCliente;
import com.cursomc.repository.CategoriaRepository;
import com.cursomc.repository.CidadeRepository;
import com.cursomc.repository.ClienteRepository;
import com.cursomc.repository.EnderecoRepository;
import com.cursomc.repository.EstadoRepository;
import com.cursomc.repository.ItemPedidoRepository;
import com.cursomc.repository.PagamentoRepository;
import com.cursomc.repository.PedidoRepository;
import com.cursomc.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DBService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public void instantiateTestDatabase () throws ParseException {
        final Categoria cat1 = new Categoria(null, "Informática");
        final Categoria cat2 = new Categoria(null, "Escritório");
        final Categoria cat3 = new Categoria(null, "Cama, mesa e Banho");
        final Categoria cat4 = new Categoria(null, "Eletrônicos");
        final Categoria cat5 = new Categoria(null, "Jardinagem");
        final Categoria cat6 = new Categoria(null, "Decoração");
        final Categoria cat7 = new Categoria(null, "Perfumaria");

        final Produto p1 = new Produto(null, "Computador", 2000.0);
        final Produto p2 = new Produto(null, "Impressora", 800.0);
        final Produto p3 = new Produto(null, "Mouse", 80.0);
        final Produto p4 = new Produto(null, "Mesa de Escritorio", 300.0);
        final Produto p5 = new Produto(null, "Toalha", 50.0);
        final Produto p6 = new Produto(null, "Colcha", 200.0);
        final Produto p7 = new Produto(null, "TV True Color", 1200.0);
        final Produto p8 = new Produto(null, "Roçadeira", 800.0);
        final Produto p9 = new Produto(null, "Abajour", 100.0);
        final Produto p10 = new Produto(null, "Pendente", 180.0);
        final Produto p11 = new Produto(null, "Shampoo", 90.0);

        cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
        cat2.getProdutos().addAll(Arrays.asList(p2, p4));
        cat3.getProdutos().addAll(Arrays.asList(p5, p6));
        cat4.getProdutos().addAll(Arrays.asList(p1, p2, p3, p7));
        cat5.getProdutos().addAll(Arrays.asList(p8));
        cat6.getProdutos().addAll(Arrays.asList(p9, p10));
        cat7.getProdutos().addAll(Arrays.asList(p11));

        p1.getCategorias().addAll(Arrays.asList(cat1, cat4));
        p2.getCategorias().addAll(Arrays.asList(cat1, cat2, cat4));
        p3.getCategorias().addAll(Arrays.asList(cat1, cat4));
        p4.getCategorias().addAll(Arrays.asList(cat2));
        p5.getCategorias().addAll(Arrays.asList(cat3));
        p6.getCategorias().addAll(Arrays.asList(cat3));
        p7.getCategorias().addAll(Arrays.asList(cat4));
        p8.getCategorias().addAll(Arrays.asList(cat5));
        p9.getCategorias().addAll(Arrays.asList(cat6));
        p10.getCategorias().addAll(Arrays.asList(cat6));
        p11.getCategorias().addAll(Arrays.asList(cat7));

        categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
        produtoRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));

        final Estado est1 = new Estado(null, "Minas Gerais");
        final Estado est2 = new Estado(null, "São Paulo");

        final Cidade c1 = new Cidade(null, "Uberlândia", est1);
        final Cidade c2 = new Cidade(null, "São Paulo", est2);
        final Cidade c3 = new Cidade(null, "Campinas", est2);

        est1.getCidades().addAll(Arrays.asList(c1));
        est2.getCidades().addAll(Arrays.asList(c2, c3));

        estadoRepository.saveAll(Arrays.asList(est1, est2));
        cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));

        final Cliente cli1 = new Cliente(null, "Maria Silva", "paulomenezes.web@gmail.com", "34680664819",
                TipoCliente.PESSOA_FISICA, encoder.encode("123"));
        cli1.getTelefones().addAll(Arrays.asList("982010547", "983868785"));

        final Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 203", "Jardim das Dores", "38222112", cli1,
                c1);
        final Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "87878787", cli1, c2);

        cli1.getEnderecos().addAll(Arrays.asList(e1, e2));

        clienteRepository.saveAll(Arrays.asList(cli1));
        enderecoRepository.saveAll(Arrays.asList(e1, e2));

        final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        final Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 09:32"), cli1, e1);
        final Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 12:23"), cli1, e2);

        final Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
        ped1.setPagamento(pagto1);

        final Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2,
                sdf.parse("20/10/2017 00:00"), null);
        ped2.setPagamento(pagto2);

        cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));

        pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
        pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));

        final ItemPedido ip1 = new ItemPedido(ped1, p1, 0.0, 1, 2000.0);
        final ItemPedido ip2 = new ItemPedido(ped1, p3, 0.0, 2, 80.0);
        final ItemPedido ip3 = new ItemPedido(ped2, p2, 100.0, 1, 800.0);

        ped1.getItens().addAll(Arrays.asList(ip1, ip2));
        ped2.getItens().addAll(Arrays.asList(ip3));

        p1.getItens().addAll(Arrays.asList(ip1));
        p2.getItens().addAll(Arrays.asList(ip3));
        p3.getItens().addAll(Arrays.asList(ip2));

        itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
    }
}
