package br.com.tamanhofamilia.dasa.receituario.daos.pedidos;

import br.com.tamanhofamilia.dasa.receituario.models.pedidos.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoDao extends JpaRepository<Pedido, Integer> {
}
