package br.com.tamanhofamilia.dasa.receituario.restfulldaos.pedidos;

import br.com.tamanhofamilia.dasa.receituario.daos.pedidos.PedidoDao;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "pedidos", collectionResourceRel = "pedidos")
public interface PedidoRestDao extends PedidoDao {
}
