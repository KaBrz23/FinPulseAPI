package br.com.finpulse.finpulseapi.chat;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {
    @Autowired
    private ChatRepository repository;

    public List<Chat> findAll() {
        return repository.findAll();
    }

    public Chat create(Chat chat) {
        return repository.save(chat);
    }

    public Chat getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Chat não encontrada com ID: " + id));
    }

    public Chat update(Long id, Chat updatedChat) {
        Chat existingChat = getById(id);
        existingChat.setTitle(updatedChat.getTitle());
        return repository.save(existingChat);
    }

    public void delete(Long id) {
        Chat chat = getById(id);
        repository.delete(chat);
    }
}
