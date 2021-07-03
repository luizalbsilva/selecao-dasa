package br.com.tamanhofamilia.dasa.receituario.models.exame;

import br.com.tamanhofamilia.dasa.receituario.models.TableHelper;
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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Data
@ToString
@EqualsAndHashCode(of = "idExame")
@Entity
@Table(schema = TableHelper.TABLE_SCHEMA, name = "exames")
public class Exame {
    @Id
    @Column(name = "id_exame", columnDefinition = "SERIAL", updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idExame;

    @NotNull
    @Length(min = 5, max = 255)
    @Column(name="descricao")
    private String descricao;
}
