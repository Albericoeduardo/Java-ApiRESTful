package DesignPatterns.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import DesignPatterns.model.Cliente;
import DesignPatterns.model.ClienteRepository;
import DesignPatterns.model.Endereco;
import DesignPatterns.model.EnderecoRepository;
import DesignPatterns.service.ClienteService;
import DesignPatterns.service.ViaCepService;

public class ClienteServiceImpl implements ClienteService{

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private ViaCepService viaCepService;

    @Override
    public Iterable<Cliente> buscarTodos() {
        return clienteRepository.findAll();
    }

    @Override
    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }

    @Override
    public void inserir(Cliente cliente) {
        salvarClienteComCep(cliente);
    }

    @Override
    public void atualizar(Long id, Cliente cliente) {
       Optional<Cliente> clienteBd = clienteRepository.findById(id);
       if(clienteBd.isPresent()){
            salvarClienteComCep(cliente);
       }
    }

    @Override
    public void deletar(Long id) {
        clienteRepository.deleteById(id);
    }

    private void salvarClienteComCep(Cliente cliente){
        String cep = cliente.getEndereco().getCep();
        Endereco endereco = enderecoRepository.findById(cep).orElseGet(() ->{
            Endereco novoEndereco = viaCepService.consultarCep(cep);
            enderecoRepository.save(novoEndereco);
            return novoEndereco;
        });
        cliente.setEndereco(endereco);
        clienteRepository.save(cliente);
    }
}
