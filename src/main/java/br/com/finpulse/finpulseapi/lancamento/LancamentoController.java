package br.com.finpulse.finpulseapi.lancamento;

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
@RequestMapping("/lancamentos")
@CacheConfig(cacheNames = "lancamentos")
@Slf4j
@Tag(name = "Lançamento", description = "Gerenciamento de lançamentos")
public class LancamentoController {

    @Autowired
    private LancamentoService lancamentoService;

    @GetMapping
    @Cacheable
    @Operation(summary = "Listar todos os lançamentos")
    public List<Lancamento> findAll() {
        return lancamentoService.findAll();
    }

    @GetMapping("{id}")
    @Operation(summary = "Listar Lançamento por id")
    public ResponseEntity<Lancamento> getById(@PathVariable Long id) {
        return ResponseEntity.of(Optional.ofNullable(lancamentoService.getById(id)));
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @CacheEvict(allEntries = true)
    @Operation(summary = "Cadastrar Lançamento")
    @ApiResponses({
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "400")
    })
    public ResponseEntity<Lancamento> create(@RequestBody Lancamento lancamento) {
        Lancamento createdLancamento = lancamentoService.create(lancamento);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdLancamento.getId())
                .toUri();

        return ResponseEntity.created(uri).body(createdLancamento);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @CacheEvict(allEntries = true)
    @Operation(summary = "Deletar Lançamento")
    @ApiResponses({
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "404"),
            @ApiResponse(responseCode = "401")
    })
    public void delete(@PathVariable Long id) {
        log.info("Apagando lançamento com ID {}", id);
        lancamentoService.delete(id);
    }

    @PutMapping("{id}")
    @CacheEvict(allEntries = true)
    @Operation(summary = "Atualizar Lançamento")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400"),
            @ApiResponse(responseCode = "401"),
            @ApiResponse(responseCode = "404")
    })
    public ResponseEntity<Lancamento> update(@PathVariable Long id, @RequestBody Lancamento updatedLancamento) {
        log.info("Atualizando lançamento com id {} para {}", id, updatedLancamento);
        Lancamento lancamento = lancamentoService.update(id, updatedLancamento);
        return ResponseEntity.ok(lancamento);
    }
}
