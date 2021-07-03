package br.com.tamanhofamilia.dasa.receituario.models.pedidos;

import br.com.tamanhofamilia.dasa.receituario.models.TableHelper;
import br.com.tamanhofamilia.dasa.receituario.models.exame.Exame;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@ToString
@EqualsAndHashCode(of = "idPedidoItem")
@Entity
@Table(schema = TableHelper.TABLE_SCHEMA, name = "pedido_itens")
public class PedidoItem {
    @Id
    @Column(name = "id_pedido_item", columnDefinition = "serial", updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idPedidoItem;
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_pedido", columnDefinition = "integer")
    private Pedido pedido;
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_exame", columnDefinition = "integer")
    private Exame exame;
}
