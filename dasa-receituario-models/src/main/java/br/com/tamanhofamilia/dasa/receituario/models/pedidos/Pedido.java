package br.com.tamanhofamilia.dasa.receituario.models.pedidos;

import br.com.tamanhofamilia.dasa.receituario.models.TableHelper;
import br.com.tamanhofamilia.dasa.receituario.models.medico.Medico;
import br.com.tamanhofamilia.dasa.receituario.models.paciente.Paciente;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Data
@ToString
@EqualsAndHashCode(of = "idPedido")
@Entity
@Table(schema = TableHelper.TABLE_SCHEMA, name = "pedidos")
public class Pedido {
    @Id
    @Column(name = "id_pedido", columnDefinition = "SERIAL", updatable = false)
    private Long idPedido;
    @Column(name = "data_validade", columnDefinition = "DATE", nullable = false)
    private LocalDate dataValidade;
    @ManyToOne
    @JoinColumn(name = "id_medico", nullable = false)
    private Medico medico;
    @ManyToOne
    @JoinColumn(name = "id_paciente", nullable = false)
    private Paciente paciente;
}
