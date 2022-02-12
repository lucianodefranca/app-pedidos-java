package com.luciano.apppedidos.services;

import com.luciano.apppedidos.entities.Cliente;
import repositories.ClienteRepository;
import com.luciano.apppedidos.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    public Cliente findById(Integer id) {
        Optional<Cliente> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado: Id " + id + " Tipo: " + Cliente.class.getName()));
     }
}
