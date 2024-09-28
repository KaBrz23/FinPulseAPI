package br.com.finpulse.finpulseapi.message;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @NotNull
    @Column(name = "chat_id")
    Long chatId;

    @Column(name = "user_id")
    Long userId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    Role role;

    @NotNull
    @NotBlank
    @Column(name = "content")
    String content;

    @NotNull
    @Column(name = "created_at")
    LocalDateTime createdAt;

    public enum Role {
        USER, ASSISTANT
    }
}
