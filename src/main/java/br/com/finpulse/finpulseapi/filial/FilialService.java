package br.com.finpulse.finpulseapi.filial;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilialService {

    @Autowired
    private FilialRepository repository;

    public List<Filial> findAll() {
        return repository.findAll();
    }

    public Filial create(Filial filial) {
        return repository.save(filial);
    }

    public Filial getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Filial não encontrada com ID: " + id));
    }

    public Filial update(Long id, Filial updatedFilial) {
        Filial existingFilial = getById(id);
        existingFilial.setCnpj(updatedFilial.getCnpj());
        existingFilial.setInscricaoEstadual(updatedFilial.getInscricaoEstadual());
        existingFilial.setInscricaoMunicipal(updatedFilial.getInscricaoMunicipal());
        existingFilial.setTelefone(updatedFilial.getTelefone());
        existingFilial.setEndereco(updatedFilial.getEndereco());
        existingFilial.setCidade(updatedFilial.getCidade());
        existingFilial.setEstado(updatedFilial.getEstado());
        existingFilial.setCep(updatedFilial.getCep());
        return repository.save(existingFilial);
    }

    public void delete(Long id) {
        Filial filial = getById(id);
        repository.delete(filial);
    }
}