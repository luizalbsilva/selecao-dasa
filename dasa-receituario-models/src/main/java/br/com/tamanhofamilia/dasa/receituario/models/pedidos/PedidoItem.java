package br.com.tamanhofamilia.dasa.receituario.models.pedidos;

import br.com.tamanhofamilia.dasa.receituario.models.TableHelper;
import br.com.tamanhofamilia.dasa.receituario.models.exame.Exame;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
    @ManyToOne
    @JoinColumn(name = "id_pedido", nullable = false)
    private Pedido pedido;
    @ManyToOne
    @JoinColumn(name = "id_exame", nullable = false)
    private Exame exame;
}
