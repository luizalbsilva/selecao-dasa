package br.com.tamanhofamilia.dasa.receituario.services.pedidos;

import br.com.tamanhofamilia.dasa.receituario.daos.pedidos.PedidoDao;
import br.com.tamanhofamilia.dasa.receituario.models.pedidos.Pedido;
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
class PedidosServiceTest {
    @InjectMocks
    PedidosService service;

    @Mock
    PedidoDao dao;

    @Mock
    Pageable pageable;

    @Test
    void findAll() {
        service.findAll(pageable);

        verify(dao).findAll(pageable);
    }

    @Test
    void create() {
        Pedido pedido = new Pedido();
        when(dao.save(pedido)).thenAnswer(e -> {
            Pedido exam = e.getArgument(0);
            exam.setIdPedido(1);
            return exam;
        });

        assertSame(1, service.create(pedido));

        verify(dao).save(pedido);
    }


    @Test
    void update() throws DataNotFoundException {
        final int id = 1;
        when(dao.existsById(id)).thenReturn(true);
        Pedido pedido = Pedido.builder().idPedido(id).build();

        service.update(pedido);

        verify(dao).save(pedido);
    }

    @Test
    void updateNotFound() {
        Pedido pedido = Pedido.builder().idPedido(1).build();

        assertThrows(DataNotFoundException.class, () -> {
            service.update(pedido);
        });
    }

    @Test
    void getById() {
        final int id = 1;
        final Optional<Pedido> daoReturn = Optional.empty();
        when(dao.findById(id)).thenReturn(daoReturn);

        final Optional<Pedido> toCheck = service.getById(id);

        assertSame(daoReturn, toCheck);
        verify(dao).findById(id);
    }

    @Test
    void delete() throws DataNotFoundException {
        final int id = 1;
        when(dao.existsById(id))
                .thenReturn(true);

        service.delete(id);

        verify(dao).deleteById(id);
    }

    @Test
    void deleteNotFound() {
        assertThrows(DataNotFoundException.class, () -> {
            final int id = 1;
            when(dao.existsById(id))
                    .thenReturn(false);
            service.delete(id);
        });
    }
}