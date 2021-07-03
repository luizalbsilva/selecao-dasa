package br.com.tamanhofamilia.dasa.receituario.models.paciente;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
@Data
public class Endereco {
    @Column(name = "endereco", columnDefinition = "varchar(255) not null", nullable = false)
    private String rua;
    @Column(name = "bairro", columnDefinition = "varchar(255)")
    private String bairro;
}
