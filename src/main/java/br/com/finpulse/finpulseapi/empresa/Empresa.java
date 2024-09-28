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
    private Long id;

    @NotNull
    @Column(name = "id_cliente")
    private Long idCliente;

    @NotNull
    @NotBlank
    @Column(name = "nome_empresa")
    private String nomeEmpresa;

    @NotNull
    @NotBlank
    @Size(min = 14, max = 20)
    @Column(name = "cnpj")
    private String cnpj;

    @NotNull
    @Column(name = "data_constituicao")
    private LocalDate dataConstituicao;

    @Column(name = "nire")
    private String nire;

    @Column(name = "inscricao_estadual")
    private String inscricaoEstadual;

    @Column(name = "inscricao_municipal")
    private String inscricaoMunicipal;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "site")
    private String site;

    @Column(name = "endereco")
    private String endereco;

    @Column(name = "cidade")
    private String cidade;

    @Column(name = "estado")
    private String estado;

    @Column(name = "cep")
    private String cep;

    @NotNull
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @NotNull
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
