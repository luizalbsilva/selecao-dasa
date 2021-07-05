package br.com.tamanhofamilia.dasa.receituario.services.pedidos;

import br.com.tamanhofamilia.dasa.receituario.daos.exame.ExameDao;
import br.com.tamanhofamilia.dasa.receituario.daos.pedidos.PedidoDao;
import br.com.tamanhofamilia.dasa.receituario.daos.pedidos.PedidoItemDao;
import br.com.tamanhofamilia.dasa.receituario.models.exame.Exame;
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
    /** Dao de Pedidos */
    private final PedidoDao pedidoDao;
    /** Dao de Exames */
    private final ExameDao exameDao;

    @Autowired
    PedidoItemsService(PedidoItemDao pedidoItemDao, PedidoDao pedidoDao, ExameDao exameDao) {
        this.pedidoItemDao = pedidoItemDao;
        this.pedidoDao = pedidoDao;
        this.exameDao = exameDao;
    }

    /** {@inheritDoc */
    @Override
    public Page<PedidoItem> findAll(Pageable pageable) {
        return pedidoItemDao.findAll(pageable);
    }

    /** {@inheritDoc */
    @Override
    public Long create(PedidoItem data) {
        verificaDadosRelacionados(data);
        final var saved = pedidoItemDao.save(data);
        return saved.getIdPedidoItem();
    }

    /** {@inheritDoc */
    @Override
    public void update(PedidoItem data) throws DataNotFoundException {
        verificaDadosRelacionados(data);
        if (!pedidoItemDao.existsById(data.getIdPedidoItem())) {
            throw new DataNotFoundException(String.format("Item do Pedido não encontrado. Id: %d", data.getIdPedidoItem()));
        }
        pedidoItemDao.save(data);
    }

    /**
     * Verifica os dados relacionados
     * @param data Dados
     * @throws DataNotFoundException Se estiver faltando dados
     */
    private void verificaDadosRelacionados(PedidoItem data) {
        if (! pedidoDao.existsById(data.getPedido().getIdPedido())){
            throw new DataNotFoundException(
                    String.format("Dado de pedido não encontrado.: %d. %s", data.getPedido().getIdPedido(), data)
            );
        }
        if (! exameDao.existsById(data.getExame().getIdExame())){
            throw new DataNotFoundException(
                    String.format("Dado de exame não encontrado.: %d. %s", data.getExame().getIdExame(), data)
            );
        }
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
