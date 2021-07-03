package br.com.tamanhofamilia.dasa.receituario.restfulldaos.pedidos;

import br.com.tamanhofamilia.dasa.receituario.daos.pedidos.PedidoItemDao;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "pedido_itens", collectionResourceRel = "pedido_itens")
public interface PedidoItemRestDao extends PedidoItemDao {
}
