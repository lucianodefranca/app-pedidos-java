package com.luciano.apppedidos.services;

import com.luciano.apppedidos.entities.Cliente;
import com.luciano.apppedidos.entities.Pedido;
import com.luciano.apppedidos.repositories.PedidoRepository;
import com.luciano.apppedidos.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

    public Pedido findById(Integer id) {
        Optional<Pedido> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado: Id " + id + " Tipo: " + Pedido.class.getName()));
    }
}
