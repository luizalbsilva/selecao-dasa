package br.com.tamanhofamilia.dasa.receituario.models.medico;

import br.com.tamanhofamilia.dasa.receituario.models.TableHelper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@ToString
@EqualsAndHashCode(of = "idMedico")
@Entity
@Table(schema = TableHelper.TABLE_SCHEMA, name = "medicos")
public class Medico {
    @Id
    @Column(name = "id_medico", columnDefinition = "SERIAL", updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idMedico;
    @Column(name = "numero_conselho", nullable = false)
    private Long numeroConselho;
    @Column(name = "uf_conselho", length = 2, nullable = false)
    private String ufConselho;
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_conselho", columnDefinition = "integer")
    private Conselho conselho;
}
