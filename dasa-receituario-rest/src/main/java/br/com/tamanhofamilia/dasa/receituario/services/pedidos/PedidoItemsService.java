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
    private final PedidoItemDao dao;

    @Autowired
    PedidoItemsService(PedidoItemDao dao) {
        this.dao = dao;
    }

    @Override
    public Page<PedidoItem> findAll(Pageable pageable) {
        return dao.findAll(pageable);
    }

    @Override
    public Long create(PedidoItem pedidoItem) {
        final PedidoItem saved = dao.save(pedidoItem);
        return saved.getIdPedidoItem();
    }

    @Override
    public void update(PedidoItem pedidoItem) throws DataNotFoundException {
        if ( !dao.existsById(pedidoItem.getIdPedidoItem()) ) {
            throw new DataNotFoundException(String.format("Item do Pedido não encontrado. Id: %d", pedidoItem.getIdPedidoItem()) );
        }
        dao.save(pedidoItem);
    }

    @Override
    public Optional<PedidoItem> getById(@NonNull Long id) {
        return dao.findById(id);
    }

    @Override
    public void delete(@NonNull Long id) throws DataNotFoundException {
        if (!dao.existsById(id)) {
            throw new DataNotFoundException(String.format("Item do Pedido não encontrado. Id: %d", id) );
        }
        dao.deleteById(id);
    }
}
