package DesignPatterns.service;

import java.util.Optional;

import DesignPatterns.model.Cliente;

public interface ClienteService {
    
    Iterable<Cliente> buscarTodos();

    Optional<Cliente> buscarPorId(Long id);

    void inserir(Cliente cliente);

    void atualizar(Long id, Cliente cliente);

    void deletar(Long id);
}
