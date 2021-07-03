package br.com.tamanhofamilia.dasa.receituario.services.pedidos;

import br.com.tamanhofamilia.dasa.receituario.daos.pedidos.PedidoDao;
import br.com.tamanhofamilia.dasa.receituario.models.pedidos.Pedido;
import br.com.tamanhofamilia.dasa.receituario.services.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PedidosService implements IPedidosService {
    private final PedidoDao dao;

    @Autowired
    PedidosService(PedidoDao dao) {
        this.dao = dao;
    }

    @Override
    public Page<Pedido> findAll(Pageable pageable) {
        return dao.findAll(pageable);
    }

    @Override
    public int create(Pedido pedido) {
        final Pedido saved = dao.save(pedido);
        return saved.getIdPedido();
    }

    @Override
    public void update(Pedido pedido) throws DataNotFoundException {
        if ( !dao.existsById(pedido.getIdPedido()) ) {
            throw new DataNotFoundException(String.format("Pedido não encontrado. Id: %d", pedido.getIdPedido()) );
        }
        dao.save(pedido);
    }

    @Override
    public Optional<Pedido> getById(int id) {
        return dao.findById(id);
    }

    @Override
    public void delete(int id) throws DataNotFoundException {
        if (!dao.existsById(id)) {
            throw new DataNotFoundException(String.format("Pedido não encontrado. Id: %d", id) );
        }
        dao.deleteById(id);
    }
}
