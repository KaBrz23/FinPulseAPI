package br.com.finpulse.finpulseapi.message;

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
@RequestMapping("/messages")
@CacheConfig(cacheNames = "messages")
@Slf4j
@Tag(name = "Message", description = "Gerenciamento de mensagens")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping
    @Cacheable
    @Operation(summary = "Listar todas as mensagens")
    public List<Message> findAll() {
        return messageService.findAll();
    }

    @GetMapping("{id}")
    @Operation(summary = "Listar Mensagem por id")
    public ResponseEntity<Message> getById(@PathVariable Long id) {
        return ResponseEntity.of(Optional.ofNullable(messageService.getById(id)));
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @CacheEvict(allEntries = true)
    @Operation(summary = "Cadastrar Mensagem")
    @ApiResponses({
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "400")
    })
    public ResponseEntity<Message> create(@RequestBody Message message) {
        Message createdMessage = messageService.create(message);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdMessage.getId())
                .toUri();

        return ResponseEntity.created(uri).body(createdMessage);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @CacheEvict(allEntries = true)
    @Operation(summary = "Deletar Mensagem")
    @ApiResponses({
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "404"),
            @ApiResponse(responseCode = "401")
    })
    public void delete(@PathVariable Long id) {
        log.info("Apagando mensagem com ID {}", id);
        messageService.delete(id);
    }

    @PutMapping("{id}")
    @CacheEvict(allEntries = true)
    @Operation(summary = "Atualizar Mensagem")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400"),
            @ApiResponse(responseCode = "401"),
            @ApiResponse(responseCode = "404")
    })
    public ResponseEntity<Message> update(@PathVariable Long id, @RequestBody Message updatedMessage) {
        log.info("Atualizando mensagem com id {} para {}", id, updatedMessage);
        Message message = messageService.update(id, updatedMessage);
        return ResponseEntity.ok(message);
    }
}

