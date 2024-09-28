package br.com.finpulse.finpulseapi.metas;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MetaService {

    @Autowired
    private MetaRepository repository;

    public List<Meta> findAll() {
        return repository.findAll();
    }

    public Meta create(Meta meta) {
        return repository.save(meta);
    }

    public Meta getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Meta não encontrada com ID: " + id));
    }

    public Meta update(Long id, Meta updatedMeta) {
        Meta existingMeta = getById(id);
        existingMeta.setMetaFaturamento(updatedMeta.getMetaFaturamento());
        return repository.save(existingMeta);
    }

    public void delete(Long id) {
        Meta meta = getById(id);
        repository.delete(meta);
    }
}
