package br.com.tamanhofamilia.dasa.receituario.models.paciente;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class DocumentosPF {
    @Column(name = "rg", columnDefinition = "char(9)")
    private String rg;

    @Column(name = "cpf", columnDefinition = "char(11)", nullable = false)
    private String cpf;
}
