package com.luciano.apppedidos.services;

import com.luciano.apppedidos.entities.Cliente;
import com.luciano.apppedidos.entities.ItemPedido;
import com.luciano.apppedidos.entities.PagamentoComBoleto;
import com.luciano.apppedidos.entities.Pedido;
import com.luciano.apppedidos.entities.enums.EstadoPagamento;
import com.luciano.apppedidos.repositories.ItemPedidoRepository;
import com.luciano.apppedidos.repositories.PagamentoRepository;
import com.luciano.apppedidos.repositories.PedidoRepository;
import com.luciano.apppedidos.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

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

    public Pedido findById(Integer id) {
        Optional<Pedido> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado: Id " + id + " Tipo: " + Pedido.class.getName()));
    }

    @Transactional
    public Pedido insert(Pedido obj) {
        obj.setId(null);
        obj.setInstante(new Date());
        obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
        obj.getPagamento().setPedido(obj);
        if (obj.getPagamento() instanceof PagamentoComBoleto) {
            PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
            boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
        }

        obj = repository.save(obj);
        pagamentoRepository.save(obj.getPagamento());

        for (ItemPedido ip : obj.getItens()) {
            ip.setDesconto(0.0);
            ip.setPreco(produtoService.findById(ip.getProduto().getId()).getPreco());
            ip.setPedido(obj);
        }
        itemPedidoRepository.saveAll(obj.getItens());
        return obj;
    }
}
