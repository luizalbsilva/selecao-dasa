package br.com.tamanhofamilia.dasa.receituario.services.pedidos;

import br.com.tamanhofamilia.dasa.receituario.daos.pedidos.PedidoItemDao;
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
    PedidoItemDao dao;

    @Mock
    Pageable pageable;

    @Test
    void findAll() {
        service.findAll(pageable);

        verify(dao).findAll(pageable);
    }

    @Test
    void create() {
        PedidoItem pedidoItem = new PedidoItem();
        when(dao.save(pedidoItem)).thenAnswer(e -> {
            PedidoItem exam = e.getArgument(0);
            exam.setIdPedidoItem(1L);
            return exam;
        });

        assertSame(1L, service.create(pedidoItem).longValue());

        verify(dao).save(pedidoItem);

    }


    @Test
    void update() throws DataNotFoundException {
        final long id = 1;
        when(dao.existsById(id)).thenReturn(true);
        PedidoItem pedidoItem = PedidoItem.builder().idPedidoItem(id).build();

        service.update(pedidoItem);

        verify(dao).save(pedidoItem);
    }

    @Test
    void updateNotFound() {
        PedidoItem pedidoItem = PedidoItem.builder().idPedidoItem(1L).build();

        assertThrows(DataNotFoundException.class, () -> {
            service.update(pedidoItem);
        });
    }

    @Test
    void getById() {
        final long id = 1;
        final Optional<PedidoItem> daoReturn = Optional.empty();
        when(dao.findById(id)).thenReturn(daoReturn);

        final Optional<PedidoItem> toCheck = service.getById(id);

        assertSame(daoReturn, toCheck);
        verify(dao).findById(id);
    }

    @Test
    void delete() throws DataNotFoundException {
        final long id = 1;
        when(dao.existsById(id))
                .thenReturn(true);

        service.delete(id);

        verify(dao).deleteById(id);
    }

    @Test
    void deleteNotFound() {
        assertThrows(DataNotFoundException.class, () -> {
            final long id = 1;
            when(dao.existsById(id))
                    .thenReturn(false);
            service.delete(id);
        });
    }
}