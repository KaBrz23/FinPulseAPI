package br.com.finpulse.finpulseapi.cliente;

import br.com.finpulse.finpulseapi.cliente.dto.ClienteProfileResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Cliente> findAll() {
        return repository.findAll();
    }

    public Cliente create(Cliente cliente) {
        cliente.setSenha(passwordEncoder.encode(cliente.getSenha()));
        return repository.save(cliente);
    }

    public ClienteProfileResponse getUserProfile(String email) {
        return repository.findByEmail(email)
                .map(ClienteProfileResponse::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
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

        if (updatedCliente.getSenha() != null && !updatedCliente.getSenha().isEmpty()) {
            existingCliente.setSenha(passwordEncoder.encode(updatedCliente.getSenha()));
        }

        return repository.save(existingCliente);
    }

    public void delete(Long id) {
        Cliente cliente = getById(id);
        repository.delete(cliente);
    }
}
