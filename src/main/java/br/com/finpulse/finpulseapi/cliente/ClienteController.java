package br.com.finpulse.finpulseapi.cliente;

import br.com.finpulse.finpulseapi.cliente.dto.ClienteProfileResponse;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/clientes")
@CacheConfig(cacheNames = "clientes")
@Slf4j
@Tag(name = "Cliente", description = "Gerenciamento de clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    @Cacheable
    @Operation(summary = "Listar todos os clientes")
    public List<Cliente> findAll() {
        return clienteService.findAll();
    }

    @GetMapping("{id}")
    @Operation(summary = "Listar Cliente por id")
    public ResponseEntity<Cliente> getById(@PathVariable Long id) {
        return ResponseEntity.of(Optional.ofNullable(clienteService.getById(id)));
    }

    @GetMapping("profile")
    @Operation(summary = "Obter perfil do cliente pelo email")
    public ClienteProfileResponse getUserProfile(){
        var email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        return clienteService.getUserProfile(email);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @CacheEvict(allEntries = true)
    @Operation(summary = "Cadastrar Cliente")
    @ApiResponses({
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "400")
    })
    public ResponseEntity<Cliente> create(@RequestBody Cliente cliente, UriComponentsBuilder uriBuilder) {
        clienteService.create(cliente);

        var uri = uriBuilder
                .path("/usuario/{id}")
                .buildAndExpand(cliente.getId())
                .toUri();

        return ResponseEntity
                .created(uri)
                .body(cliente);

    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @CacheEvict(allEntries = true)
    @Operation(summary = "Deletar Cliente")
    @ApiResponses({
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "404"),
            @ApiResponse(responseCode = "401")
    })
    public void delete(@PathVariable Long id) {
        log.info("Apagando cliente com ID {}", id);
        clienteService.delete(id);
    }

    @PutMapping("{id}")
    @CacheEvict(allEntries = true)
    @Operation(summary = "Atualizar Cliente")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400"),
            @ApiResponse(responseCode = "401"),
            @ApiResponse(responseCode = "404")
    })
    public ResponseEntity<Cliente> update(@PathVariable Long id, @RequestBody Cliente cliente) {
        log.info("Atualizando cliente com id {} para {}", id, cliente);
        Cliente updatedCliente = clienteService.update(id, cliente);
        return ResponseEntity.ok(updatedCliente);
    }
}

