package br.com.finpulse.finpulseapi.cliente;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
@Table(name = "tb_clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @NotBlank
    @Column(name = "nome")
    private String nome;

    @NotNull
    @Email
    @NotBlank
    @Column(name = "email")
    private String email;

    @NotNull
    @NotBlank
    @Size(min = 11, max = 14)
    @Column(name = "cpf")
    private String cpf;

    @Column(name = "telefone")
    private String telefone;

    @NotNull
    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @NotNull
    @NotBlank
    @Size(min = 8, max = 255)
    @Column(name = "senha")
    private String senha;
}
