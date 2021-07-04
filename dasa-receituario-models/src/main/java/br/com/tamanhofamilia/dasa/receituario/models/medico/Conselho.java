package br.com.tamanhofamilia.dasa.receituario.models.medico;

import br.com.tamanhofamilia.dasa.receituario.models.TableHelper;
import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty("Identificador do Conselho Profissional")
    @Id
    @Column(name = "id_conselho", columnDefinition = "SERIAL", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idConselho;

    @ApiModelProperty("Nome do Conselho")
    @NotNull
    @Length(min=2, max = 255)
    @Column(columnDefinition = "varchar(255) NOT NULL", nullable = false, unique = true)
    private String nome;

    @ApiModelProperty("Identificador ao Conselho ao qual está subordinado")
    @ManyToOne(cascade = CascadeType.ALL )
    @JoinColumn(name = "subordinado_a", columnDefinition = "integer")
    private Conselho subordinadoA;
}
