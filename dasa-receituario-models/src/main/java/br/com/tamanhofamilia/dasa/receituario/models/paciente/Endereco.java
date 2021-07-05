package br.com.tamanhofamilia.dasa.receituario.models.paciente;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

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

    /** Cidade */
    @Length(min = 2, max = 100)
    @NotNull
    @ApiModelProperty("Cidade")
    @Column(name = "cidade", columnDefinition = "varchar(100)", nullable = false)
    private String cidade;

    /** UF */
    @NotNull
    @Length(min = 2, max = 2)
    @Pattern(message = "Estado inválido", regexp = "AC|AL|AP|AM|BA|CE|DF|ES|GO|MA|MT|MS|MG|PA|PB|PR|PE|PI|RR|RO|RJ|RN|RS|SC|SP|SE|TO")
    @ApiModelProperty("UF")
    @Column(name = "uf", length = 2, nullable = false)
    private String uf;
}
