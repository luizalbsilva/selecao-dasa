package br.com.tamanhofamilia.dasa.receituario.models.paciente;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Endereço
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
@Data
public class Endereco {
    /** Logradouro e número */
    @ApiModelProperty("Logradouro e número")
    @Column(name = "endereco", columnDefinition = "varchar(255) not null", nullable = false)
    private String rua;

    /** Bairro */
    @ApiModelProperty("Bairro")
    @Column(name = "bairro", columnDefinition = "varchar(255)")
    private String bairro;
}
