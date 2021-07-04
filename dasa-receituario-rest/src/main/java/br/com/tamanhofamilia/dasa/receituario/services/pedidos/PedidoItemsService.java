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

@Service
public class PedidoItemsService implements IPedidoItemsService {
    private final PedidoItemDao pedidoItemDao;

    @Autowired
    PedidoItemsService(PedidoItemDao pedidoItemDao) {
        this.pedidoItemDao = pedidoItemDao;
    }

    @Override
    public Page<PedidoItem> findAll(Pageable pageable) {
        return pedidoItemDao.findAll(pageable);
    }

    @Override
    public Long create(PedidoItem pedidoItem) {
        final var saved = pedidoItemDao.save(pedidoItem);
        return saved.getIdPedidoItem();
    }

    @Override
    public void update(PedidoItem pedidoItem) throws DataNotFoundException {
        if ( !pedidoItemDao.existsById(pedidoItem.getIdPedidoItem()) ) {
            throw new DataNotFoundException(String.format("Item do Pedido não encontrado. Id: %d", pedidoItem.getIdPedidoItem()) );
        }
        pedidoItemDao.save(pedidoItem);
    }

    @Override
    public Optional<PedidoItem> getById(@NonNull Long id) {
        return pedidoItemDao.findById(id);
    }

    @Override
    public void delete(@NonNull Long id) throws DataNotFoundException {
        if (!pedidoItemDao.existsById(id)) {
            throw new DataNotFoundException(String.format("Item do Pedido não encontrado. Id: %d", id) );
        }
        pedidoItemDao.deleteById(id);
    }

    @Override
    public Page<PedidoItem> findAllFromPedido(int idPedido, Pageable pageable) {
        return pedidoItemDao.findByPedidoId(idPedido, pageable);
    }
}
