package br.com.tamanhofamilia.dasa.receituario.models.medico;

import br.com.tamanhofamilia.dasa.receituario.models.TableHelper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@ToString
@EqualsAndHashCode(of = "idConselho")
@Entity
@Table(schema = TableHelper.TABLE_SCHEMA, name = "conselhos")
public class Conselho implements Serializable {
    @Id
    @Column(name = "id_conselho", columnDefinition = "SERIAL", updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idConselho;

    @NotNull
    @Length(min=2, max = 255)
    @Column(columnDefinition = "varchar(255) NOT NULL", nullable = false, unique = true)
    private String nome;

    @ManyToOne(cascade = CascadeType.ALL )
    @JoinColumn(name = "subordinado_a", columnDefinition = "integer")
    private Conselho subordinadoA;
}
