package br.com.tamanhofamilia.dasa.receituario.daos.pedidos;

import br.com.tamanhofamilia.dasa.receituario.models.pedidos.PedidoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "pedido_itens", collectionResourceRel = "pedido_itens")
public interface PedidoItemDao extends JpaRepository<PedidoItem, Long> {
}
