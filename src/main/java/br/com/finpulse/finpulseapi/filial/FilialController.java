package br.com.finpulse.finpulseapi.filial;

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
@RequestMapping("/filiais")
@CacheConfig(cacheNames = "filiais")
@Slf4j
@Tag(name = "Filial", description = "Gerenciamento de filiais")
public class FilialController {

    @Autowired
    private FilialService filialService;

    @GetMapping
    @Cacheable
    @Operation(summary = "Listar todas as filiais")
    public List<Filial> findAll() {
        return filialService.findAll();
    }

    @GetMapping("{id}")
    @Operation(summary = "Listar Filial por id")
    public ResponseEntity<Filial> getById(@PathVariable Long id) {
        return ResponseEntity.of(Optional.ofNullable(filialService.getById(id)));
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @CacheEvict(allEntries = true)
    @Operation(summary = "Cadastrar Filial")
    @ApiResponses({
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "400")
    })
    public ResponseEntity<Filial> create(@RequestBody Filial filial) {
        Filial createdFilial = filialService.create(filial);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdFilial.getId())
                .toUri();

        return ResponseEntity.created(uri).body(createdFilial);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @CacheEvict(allEntries = true)
    @Operation(summary = "Deletar Filial")
    @ApiResponses({
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "404"),
            @ApiResponse(responseCode = "401")
    })
    public void delete(@PathVariable Long id) {
        log.info("Apagando filial com ID {}", id);
        filialService.delete(id);
    }

    @PutMapping("{id}")
    @CacheEvict(allEntries = true)
    @Operation(summary = "Atualizar Filial")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400"),
            @ApiResponse(responseCode = "401"),
            @ApiResponse(responseCode = "404")
    })
    public ResponseEntity<Filial> update(@PathVariable Long id, @RequestBody Filial updatedFilial) {
        log.info("Atualizando filial com id {} para {}", id, updatedFilial);
        Filial filial = filialService.update(id, updatedFilial);
        return ResponseEntity.ok(filial);
    }
}
