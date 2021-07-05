package br.com.tamanhofamilia.dasa.receituario.models.paciente;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 * Documentos pessoais
 */
@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentosPF {
    /** Registro Geral */
    @Column(name = "rg", length = 9)
    @ApiModelProperty("Número do RG")
    private String rg;

    /** CPF */
    @NotNull
    @Column(name = "cpf", nullable = false, length = 11)
    @ApiModelProperty("Número do CPF")
    private String cpf;
}
