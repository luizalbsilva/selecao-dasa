package br.com.tamanhofamilia.dasa.receituario.services.pedidos;

import br.com.tamanhofamilia.dasa.receituario.daos.exame.ExameDao;
import br.com.tamanhofamilia.dasa.receituario.daos.pedidos.PedidoDao;
import br.com.tamanhofamilia.dasa.receituario.daos.pedidos.PedidoItemDao;
import br.com.tamanhofamilia.dasa.receituario.models.exame.Exame;
import br.com.tamanhofamilia.dasa.receituario.models.pedidos.Pedido;
import br.com.tamanhofamilia.dasa.receituario.models.pedidos.PedidoItem;
import br.com.tamanhofamilia.dasa.receituario.services.DataNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PedidoItemsServiceTest {
    @InjectMocks
    PedidoItemsService service;

    @Mock
    PedidoItemDao pedidoItemDao;

    @Mock
    PedidoDao pedidoDao;

    @Mock
    ExameDao exameDao;

    @Mock
    Pageable pageable;

    @Test
    void findAll() {
        service.findAll(pageable);

        verify(pedidoItemDao).findAll(pageable);
    }

    @Test
    void create() {
        final int idPedido = 1;
        final int idExame = 2;
        PedidoItem pedidoItem = PedidoItem.builder()
                .pedido(Pedido.builder().idPedido(idPedido).build())
                .exame(Exame.builder().idExame(idExame).build())
                .build();
        when(pedidoDao.existsById(idPedido)).thenReturn(true);
        when(exameDao.existsById(idExame)).thenReturn(true);
        when(pedidoItemDao.save(pedidoItem)).thenAnswer(e -> {
            PedidoItem exam = e.getArgument(0);
            exam.setIdPedidoItem(1L);
            return exam;
        });

        assertSame(1L, service.create(pedidoItem).longValue());

        verify(pedidoItemDao).save(pedidoItem);

    }


    @Test
    void createPedidoComProblemas() {
        final int idPedido = 1;
        final int idExame = 2;
        PedidoItem pedidoItem = PedidoItem.builder()
                .pedido(Pedido.builder().idPedido(idPedido).build())
                .exame(Exame.builder().idExame(idExame).build())
                .build();
        when(pedidoDao.existsById(idPedido)).thenReturn(false);

        assertThrows(DataNotFoundException.class, () ->  service.create(pedidoItem).longValue());
    }


    @Test
    void update() throws DataNotFoundException {
        final long id = 3;
        final int idPedido = 4;
        final int idExame = 5;
        PedidoItem pedidoItem = PedidoItem.builder().idPedidoItem(id)
                .pedido(Pedido.builder().idPedido(idPedido).build())
                .exame(Exame.builder().idExame(idExame).build())
                .build();
        when(pedidoDao.existsById(idPedido)).thenReturn(true);
        when(exameDao.existsById(idExame)).thenReturn(true);
        when(pedidoItemDao.existsById(id)).thenReturn(true);

        service.update(pedidoItem);

        verify(pedidoItemDao).save(pedidoItem);
    }

    @Test
    void updateExameInexistente() throws DataNotFoundException {
        final long id = 3;
        final int idPedido = 4;
        final int idExame = 5;
        PedidoItem pedidoItem = PedidoItem.builder().idPedidoItem(id)
                .pedido(Pedido.builder().idPedido(idPedido).build())
                .exame(Exame.builder().idExame(idExame).build())
                .build();
        when(pedidoDao.existsById(idPedido)).thenReturn(true);
        when(exameDao.existsById(idExame)).thenReturn(false);

        assertThrows(DataNotFoundException.class,()-> service.update(pedidoItem));
    }

    @Test
    void updateNotFound() {
        final int idPedido = 7;
        final int idExame = 8;
        final long idItem = 9;
        PedidoItem pedidoItem = PedidoItem.builder().idPedidoItem(idItem)
                .pedido(Pedido.builder().idPedido(idPedido).build())
                .exame(Exame.builder().idExame(idExame).build())
                .build();
        when(pedidoDao.existsById(idPedido)).thenReturn(true);
        when(exameDao.existsById(idExame)).thenReturn(true);

        assertThrows(DataNotFoundException.class, () -> {
            service.update(pedidoItem);
        });
    }

    @Test
    void getById() {
        final long id = 1;
        final Optional<PedidoItem> daoReturn = Optional.empty();
        when(pedidoItemDao.findById(id)).thenReturn(daoReturn);

        final Optional<PedidoItem> toCheck = service.getById(id);

        assertSame(daoReturn, toCheck);
        verify(pedidoItemDao).findById(id);
    }

    @Test
    void delete() throws DataNotFoundException {
        final long id = 1;
        when(pedidoItemDao.existsById(id))
                .thenReturn(true);

        service.delete(id);

        verify(pedidoItemDao).deleteById(id);
    }

    @Test
    void deleteNotFound() {
        assertThrows(DataNotFoundException.class, () -> {
            final long id = 1;
            when(pedidoItemDao.existsById(id))
                    .thenReturn(false);
            service.delete(id);
        });
    }

    @Test
    void findAllFromPedido() {
        service.findAllFromPedido(1, pageable);

        verify(pedidoItemDao).findByPedidoId(1, pageable);
    }
}