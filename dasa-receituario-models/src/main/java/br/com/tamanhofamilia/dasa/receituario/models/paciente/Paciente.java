package br.com.tamanhofamilia.dasa.receituario.models.paciente;

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
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@ToString
@EqualsAndHashCode(of = "idPaciente")
@Entity
@Table(schema = TableHelper.TABLE_SCHEMA, name = "pacientes")
public class Paciente {
    @ApiModelProperty("Identificador do Paciente")
    @Id
    @Column(name = "id_paciente", columnDefinition = "SERIAL", updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idPaciente;

    @ApiModelProperty("Nome do Paciente")
    @NotNull
    @Length(min = 5, max = 255)
    @Column(name = "nome", columnDefinition = "varchar(255)", nullable = false)
    private String nome;

    @ApiModelProperty("Nascimento do Paciente")
    @NotNull
    @Column(name = "nascimento", columnDefinition = "date", nullable = false)
    private LocalDate nascimento;

    @ApiModelProperty("Sexo do Paciente")
    @NotNull
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "sexo", columnDefinition = "numeric(1,0)", nullable = false)
    private Sexo sexo;

    @ApiModelProperty("Nome da Mãe")
    @NotNull
    @Length(max = 255)
    @Column(name = "nome_mae", columnDefinition = "varchar(255) not null", nullable = false)
    private String nomeMae;

    @ApiModelProperty("Endereço do Paciente")
    @Embedded
    @Valid
    private Endereco endereco;

    @ApiModelProperty("Telefone de contato - somente dígitos")
    @Length(max = 11)
    @Column(name = "telefone", columnDefinition = "varchar(11)")
    private String telefone;

    @ApiModelProperty("Número do portátil - Somente dígitos")
    @NotNull
    @Length(max = 11)
    @Column(name = "mobile", columnDefinition = "varchar(11)", nullable = false)
    private String mobile;

    @ApiModelProperty("Email de Contato")
    @NotNull
    @Length(min = 5, max = 255)
    @Column(name = "email", columnDefinition = "varchar(255)", nullable = false)
    private String email;

    @ApiModelProperty("Documentos do Paciente")
    @Valid
    @Embedded
    private DocumentosPF documentos = new DocumentosPF();
}
