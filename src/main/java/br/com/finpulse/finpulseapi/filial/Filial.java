package br.com.finpulse.finpulseapi.filial;

import jakarta.persistence.*;
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
@Table(name = "tb_filial")
public class Filial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @NotNull
    @Column(name = "id_empresa")
    Long idEmpresa;

    @NotNull
    @NotBlank
    @Size(min = 14, max = 20)
    @Column(name = "cnpj")
    String cnpj;

    @Column(name = "inscricao_estadual")
    String inscricaoEstadual;

    @Column(name = "inscricao_municipal")
    String inscricaoMunicipal;

    @Column(name = "telefone")
    String telefone;

    @Column(name = "endereco")
    String endereco;

    @Column(name = "cidade")
    String cidade;

    @Column(name = "estado")
    String estado;

    @Column(name = "cep")
    String cep;

    @NotNull
    @Column(name = "created_at")
    LocalDateTime createdAt;

    @NotNull
    @Column(name = "updated_at")
    LocalDateTime updatedAt;
}
