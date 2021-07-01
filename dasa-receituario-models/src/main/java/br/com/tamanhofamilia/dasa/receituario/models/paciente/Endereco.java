package br.com.tamanhofamilia.dasa.receituario.models.paciente;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Data
public class Endereco {
    @Column(name = "endereco", columnDefinition = "varchar(255) not null", nullable = false)
    private String endereco;
    @Column(name = "bairro", columnDefinition = "varchar(255)")
    private String bairro;
}
