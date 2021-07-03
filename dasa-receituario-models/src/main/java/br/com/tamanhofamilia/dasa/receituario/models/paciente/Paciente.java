package br.com.tamanhofamilia.dasa.receituario.models.paciente;

import br.com.tamanhofamilia.dasa.receituario.models.TableHelper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Data
@ToString
@EqualsAndHashCode(of = "idPaciente")
@Entity
@Table(schema = TableHelper.TABLE_SCHEMA, name = "pacientes")
public class Paciente {
    @Id
    @Column(name = "id_paciente", columnDefinition = "SERIAL", updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idPaciente;

    @Column(name = "nome", columnDefinition = "varchar(255)", nullable = false)
    private String nome;

    @Column(name = "nascimento", columnDefinition = "date", nullable = false)
    private LocalDate nascimento;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "sexo", columnDefinition = "numeric(1,0)", nullable = false)
    private Sexo sexo;

    @Column(name = "nome_mae", columnDefinition = "varchar(255) not null", nullable = false)
    private String nomeMae;

    @Embedded
    private Endereco endereco;

    @Column(name = "telefone", columnDefinition = "varchar(11)")
    private String telefone;

    @Column(name = "mobile", columnDefinition = "varchar(11)", nullable = false)
    private String mobile;

    @Column(name = "email", columnDefinition = "varchar(255)", nullable = false)
    private String email;

    @Embedded
    private DocumentosPF documentos = new DocumentosPF();
}
