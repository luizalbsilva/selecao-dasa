package br.com.tamanhofamilia.dasa.receituario.models.exame;

import br.com.tamanhofamilia.dasa.receituario.models.TableHelper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Columns;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@ToString
@EqualsAndHashCode(of = "idExame")
@Entity
@Table(schema = TableHelper.TABLE_SCHEMA, name = "exames")
public class Exame {
    @Id
    @Column(name = "id_exame", columnDefinition = "SERIAL", updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idExame;
    private String descricao;
}
