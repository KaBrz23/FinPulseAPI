package br.com.finpulse.finpulseapi.metas;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_metas")
public class Meta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @NotNull
    @Column(name = "user_id")
    Long userId;

    @NotNull
    @Column(name = "meta_faturamento")
    BigDecimal metaFaturamento;

    @NotNull
    @Column(name = "created_at")
    LocalDateTime createdAt;
}
