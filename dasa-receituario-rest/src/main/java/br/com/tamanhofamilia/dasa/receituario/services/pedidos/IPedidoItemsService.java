package br.com.tamanhofamilia.dasa.receituario.services.pedidos;

import br.com.tamanhofamilia.dasa.receituario.models.pedidos.PedidoItem;
import br.com.tamanhofamilia.dasa.receituario.services.IService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPedidoItemsService extends IService<PedidoItem, Long> {
    Page<PedidoItem> findAllFromPedido(int idPedido, Pageable pageable);
}
