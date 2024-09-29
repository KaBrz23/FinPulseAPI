package br.com.finpulse.finpulseapi.gasto;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import lombok.extern.slf4j.Slf4j;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/gastos")
@CacheConfig(cacheNames = "gastos")
@Slf4j
@Tag(name = "Gasto", description = "Gerenciamento de gastos")
public class GastoController {

    @Autowired
    private GastoService gastoService;

    @GetMapping
    @Cacheable
    @Operation(summary = "Listar todos os gastos")
    public ResponseEntity<Page<Gasto>> findAll(Pageable pageable) {
        return ResponseEntity.ok(gastoService.findAll(pageable));
    }

    @GetMapping("{id}")
    @Operation(summary = "Listar Gasto por id")
    public ResponseEntity<Gasto> getById(@PathVariable Long id) {
        return ResponseEntity.of(Optional.ofNullable(gastoService.getById(id)));
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @CacheEvict(allEntries = true)
    @Operation(summary = "Cadastrar Gasto")
    @ApiResponses({
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "400")
    })
    public ResponseEntity<Gasto> create(@RequestBody Gasto gasto) {
        Gasto createdGasto = gastoService.create(gasto);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdGasto.getId())
                .toUri();

        return ResponseEntity.created(uri).body(createdGasto);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @CacheEvict(allEntries = true)
    @Operation(summary = "Deletar Gasto")
    @ApiResponses({
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "404"),
            @ApiResponse(responseCode = "401")
    })
    public void delete(@PathVariable Long id) {
        log.info("Apagando gasto com ID {}", id);
        gastoService.delete(id);
    }

    @PutMapping("{id}")
    @CacheEvict(allEntries = true)
    @Operation(summary = "Atualizar Gasto")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400"),
            @ApiResponse(responseCode = "401"),
            @ApiResponse(responseCode = "404")
    })
    public ResponseEntity<Gasto> update(@PathVariable Long id, @RequestBody Gasto updatedGasto) {
        log.info("Atualizando gasto com id {} para {}", id, updatedGasto);
        Gasto gasto = gastoService.update(id, updatedGasto);
        return ResponseEntity.ok(gasto);
    }
}

