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
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@ToString
@EqualsAndHashCode(of = "idConselho")
@Entity
@Table(schema = TableHelper.TABLE_SCHEMA, name = "conselho")
public class Conselho implements Serializable {
    @Id
    @Column(name = "id_conselho", columnDefinition = "SERIAL", updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idConselho;
    @Column(columnDefinition = "varchar(255) NOT NULL", nullable = false, unique = true)
    private String nome;
    @ManyToOne
    @JoinColumn(name = "subordinado_a")
    private Conselho subordinadoA;
}
