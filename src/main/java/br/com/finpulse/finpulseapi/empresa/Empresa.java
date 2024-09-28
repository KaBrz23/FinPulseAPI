package br.com.finpulse.finpulseapi.empresa;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_empresa")
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @NotNull
    @Column(name = "id_cliente")
    Long idCliente;

    @NotNull
    @NotBlank
    @Column(name = "nome_empresa")
    String nomeEmpresa;

    @NotNull
    @NotBlank
    @Size(min = 14, max = 20)
    @Column(name = "cnpj")
    String cnpj;

    @NotNull
    @Column(name = "data_constituicao")
    LocalDate dataConstituicao;

    @Column(name = "nire")
    String nire;

    @Column(name = "inscricao_estadual")
    String inscricaoEstadual;

    @Column(name = "inscricao_municipal")
    String inscricaoMunicipal;

    @Column(name = "telefone")
    String telefone;

    @Column(name = "site")
    String site;

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
