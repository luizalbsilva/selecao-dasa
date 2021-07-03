package br.com.tamanhofamilia.dasa.receituario.models.paciente;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class DocumentosPF {
    @Column(name = "rg", length = 9)
    private String rg;

    @Column(name = "cpf", nullable = false, length = 11)
    private String cpf;
}
