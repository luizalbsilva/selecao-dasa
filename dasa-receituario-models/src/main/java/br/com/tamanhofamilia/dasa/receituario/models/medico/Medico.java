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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@EqualsAndHashCode(of = "idMedico")
@Entity
@Table(schema = TableHelper.TABLE_SCHEMA, name = "medicos")
public class Medico {
    @ApiModelProperty("Identificador do Médico")
    @Id
    @Column(name = "id_medico", columnDefinition = "SERIAL", updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idMedico;

    @ApiModelProperty("Número no Conselho Profissional")
    @NotNull
    @Min(1)
    @Column(name = "numero_conselho", nullable = false)
    private Long numeroConselho;

    @ApiModelProperty("UF do Conselho Regional")
    @NotNull
    @Length(min = 2, max = 2)
    @Column(name = "uf_conselho", length = 2, nullable = false)
    private String ufConselho;

    @ApiModelProperty("Identificador do Conselho")
    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_conselho", columnDefinition = "integer")
    private Conselho conselho;
}
