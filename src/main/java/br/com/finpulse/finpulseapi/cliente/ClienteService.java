package br.com.finpulse.finpulseapi.cliente;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository repository;

    public List<Cliente> findAll() {
        return repository.findAll();
    }

    public Cliente create(Cliente cliente) {
        return repository.save(cliente);
    }

    public Cliente getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrada com ID: " + id));
    }

    public Cliente update(Long id, Cliente updatedCliente) {
        Cliente existingCliente = getById(id);
        existingCliente.setNome(updatedCliente.getNome());
        existingCliente.setEmail(updatedCliente.getEmail());
        existingCliente.setCpf(updatedCliente.getCpf());
        existingCliente.setTelefone(updatedCliente.getTelefone());
        existingCliente.setSenha(updatedCliente.getSenha());
        return repository.save(existingCliente);
    }

    public void delete(Long id) {
        Cliente cliente = getById(id);
        repository.delete(cliente);
    }
}
