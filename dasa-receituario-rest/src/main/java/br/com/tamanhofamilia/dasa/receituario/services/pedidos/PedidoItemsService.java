package br.com.tamanhofamilia.dasa.receituario.services.pedidos;

import br.com.tamanhofamilia.dasa.receituario.daos.pedidos.PedidoItemDao;
import br.com.tamanhofamilia.dasa.receituario.models.pedidos.PedidoItem;
import br.com.tamanhofamilia.dasa.receituario.services.DataNotFoundException;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Serviço de Itens de pedido
 */
@Service
public class PedidoItemsService implements IPedidoItemsService {
    /** Dao de Itens de pedido */
    private final PedidoItemDao pedidoItemDao;

    @Autowired
    PedidoItemsService(PedidoItemDao pedidoItemDao) {
        this.pedidoItemDao = pedidoItemDao;
    }

    /** {@inheritDoc */
    @Override
    public Page<PedidoItem> findAll(Pageable pageable) {
        return pedidoItemDao.findAll(pageable);
    }

    /** {@inheritDoc */
    @Override
    public Long create(PedidoItem data) {
        final var saved = pedidoItemDao.save(data);
        return saved.getIdPedidoItem();
    }

    /** {@inheritDoc */
    @Override
    public void update(PedidoItem data) throws DataNotFoundException {
        if (!pedidoItemDao.existsById(data.getIdPedidoItem())) {
            throw new DataNotFoundException(String.format("Item do Pedido não encontrado. Id: %d", data.getIdPedidoItem()));
        }
        pedidoItemDao.save(data);
    }

    /** {@inheritDoc */
    @Override
    public Optional<PedidoItem> getById(@NonNull Long id) {
        return pedidoItemDao.findById(id);
    }

    /** {@inheritDoc */
    @Override
    public void delete(@NonNull Long id) throws DataNotFoundException {
        if (!pedidoItemDao.existsById(id)) {
            throw new DataNotFoundException(String.format("Item do Pedido não encontrado. Id: %d", id));
        }
        pedidoItemDao.deleteById(id);
    }

    /** {@inheritDoc */
    @Override
    public Page<PedidoItem> findAllFromPedido(int idPedido, Pageable pageable) {
        return pedidoItemDao.findByPedidoId(idPedido, pageable);
    }
}
