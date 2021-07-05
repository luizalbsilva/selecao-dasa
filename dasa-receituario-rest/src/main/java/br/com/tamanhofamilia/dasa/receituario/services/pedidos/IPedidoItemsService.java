package br.com.tamanhofamilia.dasa.receituario.services.pedidos;

import br.com.tamanhofamilia.dasa.receituario.models.pedidos.PedidoItem;
import br.com.tamanhofamilia.dasa.receituario.services.IService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Define os métodos que irão compor os serviços de Itens de Pedido
 */
public interface IPedidoItemsService extends IService<PedidoItem, Long> {
    /**
     * Lista todos os registro para um determinado pedido
     * @param idPedido Identificador do Pedido
     * @param pageable Informações sobre paginação / ordem
     * @return Lista dos dados
     */
    Page<PedidoItem> findAllFromPedido(int idPedido, Pageable pageable);
}
