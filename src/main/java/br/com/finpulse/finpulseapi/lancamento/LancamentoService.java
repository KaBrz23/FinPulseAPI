package br.com.finpulse.finpulseapi.lancamento;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LancamentoService {

    @Autowired
    private LancamentoRepository repository;

    public List<Lancamento> findAll() {
        return repository.findAll();
    }

    public Lancamento create(Lancamento lancamento) {
        return repository.save(lancamento);
    }

    public Lancamento getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lancamento não encontrada com ID: " + id));
    }

    public Lancamento update(Long id, Lancamento updatedLancamento) {
        Lancamento existingLancamento = getById(id);
        existingLancamento.setIdentificador(updatedLancamento.getIdentificador());
        existingLancamento.setValor(updatedLancamento.getValor());
        existingLancamento.setData(updatedLancamento.getData());
        existingLancamento.setTipo(updatedLancamento.getTipo());
        return repository.save(existingLancamento);
    }

    public void delete(Long id) {
        Lancamento lancamento = getById(id);
        repository.delete(lancamento);
    }
}
