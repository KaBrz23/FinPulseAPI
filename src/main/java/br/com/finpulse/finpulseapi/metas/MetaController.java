package br.com.finpulse.finpulseapi.metas;

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
@RequestMapping("/metas")
@CacheConfig(cacheNames = "metas")
@Slf4j
@Tag(name = "Meta", description = "Gerenciamento de metas")
public class MetaController {

    @Autowired
    private MetaService metaService;

    @GetMapping
    @Cacheable
    @Operation(summary = "Listar todas as metas")
    public List<Meta> findAll() {
        return metaService.findAll();
    }

    @GetMapping("{id}")
    @Operation(summary = "Listar Meta por id")
    public ResponseEntity<Meta> getById(@PathVariable Long id) {
        return ResponseEntity.of(Optional.ofNullable(metaService.getById(id)));
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @CacheEvict(allEntries = true)
    @Operation(summary = "Cadastrar Meta")
    @ApiResponses({
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "400")
    })
    public ResponseEntity<Meta> create(@RequestBody Meta meta) {
        Meta createdMeta = metaService.create(meta);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdMeta.getId())
                .toUri();

        return ResponseEntity.created(uri).body(createdMeta);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @CacheEvict(allEntries = true)
    @Operation(summary = "Deletar Meta")
    @ApiResponses({
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "404"),
            @ApiResponse(responseCode = "401")
    })
    public void delete(@PathVariable Long id) {
        log.info("Apagando meta com ID {}", id);
        metaService.delete(id);
    }

    @PutMapping("{id}")
    @CacheEvict(allEntries = true)
    @Operation(summary = "Atualizar Meta")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400"),
            @ApiResponse(responseCode = "401"),
            @ApiResponse(responseCode = "404")
    })
    public ResponseEntity<Meta> update(@PathVariable Long id, @RequestBody Meta updatedMeta) {
        log.info("Atualizando meta com id {} para {}", id, updatedMeta);
        Meta meta = metaService.update(id, updatedMeta);
        return ResponseEntity.ok(meta);
    }
}
