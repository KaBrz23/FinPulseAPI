package br.com.finpulse.finpulseapi.chat;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.util.Optional;


import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/chats")
@CacheConfig(cacheNames = "chats")
@Slf4j
@Tag(name = "Chat", description = "Gerenciamento de chats")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping
    @Cacheable
    @Operation(summary = "Listar todos os chats")
    public List<Chat> findAll() {
        return chatService.findAll();
    }

    @GetMapping("{id}")
    @Operation(summary = "Listar Chat por id")
    public ResponseEntity<Chat> getById(@PathVariable Long id) {
        return ResponseEntity.of(Optional.ofNullable(chatService.getById(id)));
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @CacheEvict(allEntries = true)
    @Operation(summary = "Cadastrar Chat")
    @ApiResponses({
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "400")
    })
    public ResponseEntity<Chat> create(@RequestBody Chat chat) {
        Chat createdChat = chatService.create(chat);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdChat.getId())
                .toUri();

        return ResponseEntity.created(uri).body(createdChat);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @CacheEvict(allEntries = true)
    @Operation(summary = "Deletar Chat")
    @ApiResponses({
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "404"),
            @ApiResponse(responseCode = "401")
    })
    public void delete(@PathVariable Long id) {
        log.info("Apagando chat com ID {}", id);
        chatService.delete(id);
    }

    @PutMapping("{id}")
    @CacheEvict(allEntries = true)
    @Operation(summary = "Atualizar Chat")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400"),
            @ApiResponse(responseCode = "401"),
            @ApiResponse(responseCode = "404")
    })
    public ResponseEntity<Chat> update(@PathVariable Long id, @RequestBody Chat updatedChat) {
        log.info("Atualizando chat com id {} para {}", id, updatedChat);
        Chat chat = chatService.update(id, updatedChat);
        return ResponseEntity.ok(chat);
    }
}
