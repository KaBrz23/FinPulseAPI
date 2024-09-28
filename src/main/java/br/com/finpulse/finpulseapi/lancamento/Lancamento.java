package br.com.finpulse.finpulseapi.lancamento;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_lancamentos")
public class Lancamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "id_usuario")
    private Long idUsuario;

    @NotNull
    @NotBlank
    @Column(name = "identificador")
    private String identificador;

    @NotNull
    @Column(name = "valor")
    private BigDecimal valor;

    @NotNull
    @Column(name = "data")
    private LocalDate data;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private Tipo tipo;

    @NotNull
    @Column(name = "criado_em", updatable = false)
    private LocalDateTime criadoEm;

    @NotNull
    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;

    public enum Tipo {
        ENTRADA, SAIDA
    }
}