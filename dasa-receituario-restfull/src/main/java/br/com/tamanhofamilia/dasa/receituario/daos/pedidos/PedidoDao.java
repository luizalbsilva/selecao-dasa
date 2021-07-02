package br.com.tamanhofamilia.dasa.receituario.daos.pedidos;

import br.com.tamanhofamilia.dasa.receituario.models.pedidos.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource(path = "pedidos", collectionResourceRel = "pedidos")
public interface PedidoDao extends JpaRepository<Pedido, Long> {
}
