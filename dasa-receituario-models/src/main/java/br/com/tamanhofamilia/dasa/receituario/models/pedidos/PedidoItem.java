package br.com.tamanhofamilia.dasa.receituario.models.pedidos;

import br.com.tamanhofamilia.dasa.receituario.models.TableHelper;
import br.com.tamanhofamilia.dasa.receituario.models.exame.Exame;
import io.swagger.annotations.ApiModelProperty;
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
import javax.validation.constraints.NotNull;

/** Entidade que representa os itens do pedido */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@ToString
@EqualsAndHashCode(of = "idPedidoItem")
@Entity
@Table(schema = TableHelper.TABLE_SCHEMA, name = "pedido_itens")
public class PedidoItem {
    /** Identificador do item do pedido */
    @ApiModelProperty("Identificador do Item do Pedido")
    @Id
    @Column(name = "id_pedido_item", columnDefinition = "bigserial", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPedidoItem;

    /** Identificador do Pedido */
    @ApiModelProperty("Identificador do Pedido")
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_pedido", columnDefinition = "integer")
    private Pedido pedido;

    /** Exame requisitado */
    @ApiModelProperty("Identificador do Exame")
    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_exame", columnDefinition = "integer")
    private Exame exame;
}
