package com.luciano.apppedidos.resources;

import com.luciano.apppedidos.entities.Produto;
import com.luciano.apppedidos.entities.dtos.CategoriaDTO;
import com.luciano.apppedidos.entities.dtos.ProdutoDTO;
import com.luciano.apppedidos.resources.utils.URL;
import com.luciano.apppedidos.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {

    @Autowired
    private ProdutoService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Produto> findById(@PathVariable Integer id) {
        Produto obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping
    public ResponseEntity<Page<ProdutoDTO>> findPage(
            @RequestParam(value = "nome", defaultValue = "") String nome,
            @RequestParam(value = "categorias", defaultValue = "") String categorias,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPAge", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        String nomeDecoded = URL.decodeParam(nome);
        List<Integer> ids = URL.decodeIntList(categorias);
        Page<ProdutoDTO> listDTO = service.search(nomeDecoded, ids, page, linesPerPage, orderBy, direction)
                .map(obj -> new ProdutoDTO(obj));
        return ResponseEntity.ok().body(listDTO);
    }

}
