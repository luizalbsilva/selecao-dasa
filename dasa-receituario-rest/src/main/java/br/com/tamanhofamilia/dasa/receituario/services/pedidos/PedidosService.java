package br.com.tamanhofamilia.dasa.receituario.services.pedidos;

import br.com.tamanhofamilia.dasa.receituario.daos.pedidos.PedidoDao;
import br.com.tamanhofamilia.dasa.receituario.models.pedidos.Pedido;
import br.com.tamanhofamilia.dasa.receituario.services.DataNotFoundException;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/** Serviço de pedidos */
@Service
public class PedidosService implements IPedidosService {
    /** Dao de acesso aos dados de Pedido */
    private final PedidoDao dao;

    @Autowired
    PedidosService(PedidoDao dao) {
        this.dao = dao;
    }

    /** {@inheritDoc */
    @Override
    public Page<Pedido> findAll(Pageable pageable) {
        return dao.findAll(pageable);
    }

    /** {@inheritDoc */
    @Override
    public Integer create(Pedido data) {
        final Pedido saved = dao.save(data);
        return saved.getIdPedido();
    }

    /** {@inheritDoc */
    @Override
    public void update(Pedido data) throws DataNotFoundException {
        if ( !dao.existsById(data.getIdPedido()) ) {
            throw new DataNotFoundException(String.format("Pedido não encontrado. Id: %d", data.getIdPedido()) );
        }
        dao.save(data);
    }

    /** {@inheritDoc */
    @Override
    public Optional<Pedido> getById(@NonNull Integer id) {
        return dao.findById(id);
    }

    /** {@inheritDoc */
    @Override
    public void delete(@NonNull Integer id) throws DataNotFoundException {
        if (!dao.existsById(id)) {
            throw new DataNotFoundException(String.format("Pedido não encontrado. Id: %d", id) );
        }
        dao.deleteById(id);
    }
}
