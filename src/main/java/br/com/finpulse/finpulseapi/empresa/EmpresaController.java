package br.com.finpulse.finpulseapi.empresa;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/empresas")
@CacheConfig(cacheNames = "empresas")
@Slf4j
@Tag(name = "Empresa", description = "Gerenciamento de empresas")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @GetMapping
    @Cacheable
    @Operation(summary = "Listar todas as empresas")
    public List<Empresa> findAll() {
        return empresaService.findAll();
    }

    @GetMapping("{id}")
    @Operation(summary = "Listar Empresa por id")
    public ResponseEntity<Empresa> getById(@PathVariable Long id) {
        return ResponseEntity.of(Optional.ofNullable(empresaService.getById(id)));
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @CacheEvict(allEntries = true)
    @Operation(summary = "Cadastrar Empresa")
    @ApiResponses({
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "400")
    })
    public ResponseEntity<Empresa> create(@RequestBody Empresa empresa) {
        Empresa createdEmpresa = empresaService.create(empresa);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdEmpresa.getId())
                .toUri();

        return ResponseEntity.created(uri).body(createdEmpresa);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @CacheEvict(allEntries = true)
    @Operation(summary = "Deletar Empresa")
    @ApiResponses({
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "404"),
            @ApiResponse(responseCode = "401")
    })
    public void delete(@PathVariable Long id) {
        log.info("Apagando empresa com ID {}", id);
        empresaService.delete(id);
    }

    @PutMapping("{id}")
    @CacheEvict(allEntries = true)
    @Operation(summary = "Atualizar Empresa")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400"),
            @ApiResponse(responseCode = "401"),
            @ApiResponse(responseCode = "404")
    })
    public ResponseEntity<Empresa> update(@PathVariable Long id, @RequestBody Empresa updatedEmpresa) {
        log.info("Atualizando empresa com id {} para {}", id, updatedEmpresa);
        Empresa empresa = empresaService.update(id, updatedEmpresa);
        return ResponseEntity.ok(empresa);
    }
}

