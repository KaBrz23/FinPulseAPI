package br.com.finpulse.finpulseapi.gasto;

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
@Table(name = "tb_gastos")
public class Gasto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "user_id")
    private Long userId;

    @NotNull
    @NotBlank
    @Column(name = "descricao")
    private String descricao;

    @NotNull
    @Column(name = "valor")
    private BigDecimal valor;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_pagamento")
    private TipoPagamento tipoPagamento;

    @Column(name = "quantidade_parcelas")
    private Integer quantidadeParcelas;

    @Column(name = "categoria_id")
    private Long categoriaId;

    @NotNull
    @Column(name = "data_inicio")
    private LocalDate dataInicio;

    @NotNull
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public enum TipoPagamento {
        RECORRENTE, PARCELADO
    }
}