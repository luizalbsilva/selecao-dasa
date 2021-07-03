package br.com.tamanhofamilia.dasa.receituario.models.paciente;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentosPF {
    @Column(name = "rg", length = 9)
    private String rg;

    @NotNull
    @Column(name = "cpf", nullable = false, length = 11)
    private String cpf;
}
