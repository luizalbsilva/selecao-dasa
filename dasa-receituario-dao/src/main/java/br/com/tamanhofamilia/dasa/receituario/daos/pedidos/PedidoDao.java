package br.com.tamanhofamilia.dasa.receituario.daos.pedidos;

import br.com.tamanhofamilia.dasa.receituario.models.pedidos.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface que define acesso aos dados do Pedido
 * @see Pedido
 */
@Repository
public interface PedidoDao extends JpaRepository<Pedido, Integer> {
}
