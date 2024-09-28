package br.com.finpulse.finpulseapi.gasto;

import br.com.finpulse.finpulseapi.filial.Filial;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GastoService {

    @Autowired
    private GastoRepository repository;

    public List<Gasto> findAll() {
        return repository.findAll();
    }

    public Gasto create(Gasto gasto) {
        return repository.save(gasto);
    }

    public Gasto getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Gasto não encontrada com ID: " + id));
    }

    public Gasto update(Long id, Gasto updatedGasto) {
        Gasto existingGasto = getById(id);
        existingGasto.setDescricao(updatedGasto.getDescricao());
        existingGasto.setValor(updatedGasto.getValor());
        existingGasto.setTipoPagamento(updatedGasto.getTipoPagamento());
        existingGasto.setQuantidadeParcelas(updatedGasto.getQuantidadeParcelas());
        existingGasto.setCategoriaId(updatedGasto.getCategoriaId());
        existingGasto.setDataInicio(updatedGasto.getDataInicio());
        return repository.save(existingGasto);
    }

    public void delete(Long id) {
        Gasto gasto = getById(id);
        repository.delete(gasto);
    }
}
