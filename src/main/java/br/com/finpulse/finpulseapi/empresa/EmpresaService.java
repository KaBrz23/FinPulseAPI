package br.com.finpulse.finpulseapi.empresa;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository repository;

    public List<Empresa> findAll() {
        return repository.findAll();
    }

    public Empresa create(Empresa empresa) {
        return repository.save(empresa);
    }

    public Empresa getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada com ID: " + id));
    }

    public Empresa update(Long id, Empresa updatedEmpresa) {
        Empresa existingEmpresa = getById(id);
        existingEmpresa.setNomeEmpresa(updatedEmpresa.getNomeEmpresa());
        existingEmpresa.setCnpj(updatedEmpresa.getCnpj());
        existingEmpresa.setDataConstituicao(updatedEmpresa.getDataConstituicao());
        existingEmpresa.setNire(updatedEmpresa.getNire());
        existingEmpresa.setInscricaoEstadual(updatedEmpresa.getInscricaoEstadual());
        existingEmpresa.setInscricaoMunicipal(updatedEmpresa.getInscricaoMunicipal());
        existingEmpresa.setTelefone(updatedEmpresa.getTelefone());
        existingEmpresa.setSite(updatedEmpresa.getSite());
        existingEmpresa.setEndereco(updatedEmpresa.getEndereco());
        existingEmpresa.setCidade(updatedEmpresa.getCidade());
        existingEmpresa.setEstado(updatedEmpresa.getEstado());
        existingEmpresa.setCep(updatedEmpresa.getCep());
        existingEmpresa.setUpdatedAt(updatedEmpresa.getUpdatedAt());
        return repository.save(existingEmpresa);
    }

    public void delete(Long id) {
        Empresa empresa = getById(id);
        repository.delete(empresa);
    }
}
