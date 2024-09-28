package br.com.finpulse.finpulseapi.message;

import br.com.finpulse.finpulseapi.filial.Filial;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository repository;

    public List<Message> findAll() {
        return repository.findAll();
    }

    public Message create(Message message) {
        return repository.save(message);
    }

    public Message getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Message não encontrada com ID: " + id));
    }

    public Message update(Long id, Message updatedMessage) {
        Message existingMessage = getById(id);
        existingMessage.setChatId(updatedMessage.getChatId());
        existingMessage.setUserId(updatedMessage.getUserId());
        existingMessage.setRole(updatedMessage.getRole());
        existingMessage.setContent(updatedMessage.getContent());
        return repository.save(existingMessage);
    }

    public void delete(Long id) {
        Message message = getById(id);
        repository.delete(message);
    }
}
