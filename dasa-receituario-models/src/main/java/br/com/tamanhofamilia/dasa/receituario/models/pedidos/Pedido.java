package br.com.tamanhofamilia.dasa.receituario.models.pedidos;

import br.com.tamanhofamilia.dasa.receituario.models.TableHelper;
import br.com.tamanhofamilia.dasa.receituario.models.medico.Medico;
import br.com.tamanhofamilia.dasa.receituario.models.paciente.Paciente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@ToString
@EqualsAndHashCode(of = "idPedido")
@Entity
@Table(schema = TableHelper.TABLE_SCHEMA, name = "pedidos")
public class Pedido {
    @Id
    @Column(name = "id_pedido", columnDefinition = "SERIAL", updatable = false)
    private Integer idPedido;

    @NotNull
    @Column(name = "data_validade", columnDefinition = "DATE", nullable = false)
    private LocalDate dataValidade;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_medico", columnDefinition = "integer")
    private Medico medico;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_paciente", columnDefinition = "integer")
    private Paciente paciente;
}
