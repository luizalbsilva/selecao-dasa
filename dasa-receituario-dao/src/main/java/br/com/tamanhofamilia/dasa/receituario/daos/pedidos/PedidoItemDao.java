package br.com.tamanhofamilia.dasa.receituario.daos.pedidos;

import br.com.tamanhofamilia.dasa.receituario.models.pedidos.PedidoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoItemDao extends JpaRepository<PedidoItem, Integer> {
}
