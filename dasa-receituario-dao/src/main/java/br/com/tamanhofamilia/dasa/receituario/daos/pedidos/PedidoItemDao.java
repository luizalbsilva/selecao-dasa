package br.com.tamanhofamilia.dasa.receituario.daos.pedidos;

import br.com.tamanhofamilia.dasa.receituario.models.pedidos.PedidoItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoItemDao extends JpaRepository<PedidoItem, Long> {
    @Query("from PedidoItem p where p.pedido.idPedido = ?1")
    Page<PedidoItem> findByPedidoId(int idPedido, Pageable pageable);
}
