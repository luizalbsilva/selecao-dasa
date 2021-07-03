package br.com.tamanhofamilia.dasa.receituario.services.medicos;

import br.com.tamanhofamilia.dasa.receituario.daos.medico.ConselhoDao;
import br.com.tamanhofamilia.dasa.receituario.models.medico.Conselho;
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
class ConselhosServiceTest {
    @InjectMocks
    ConselhosService service;

    @Mock
    ConselhoDao dao;

    @Mock
    Pageable pageable;

    @Test
    void findAll() {
        service.findAll(pageable);

        verify(dao).findAll(pageable);
    }

    @Test
    void create() {
        Conselho conselho = Conselho.builder().nome("CFM").build();
        when(dao.save(conselho)).thenAnswer(e -> {
            Conselho cons = e.getArgument(0);
            cons.setIdConselho(1);
            return cons;
        });

        assertSame(1, service.create(conselho));

        verify(dao).save(conselho);

    }


    @Test
    void update() throws DataNotFoundException {
        final int id = 1;
        when(dao.existsById(id)).thenReturn(true);
        Conselho conselho = Conselho.builder().idConselho(id).build();

        service.update(conselho);

        verify(dao).save(conselho);
    }

    @Test
    void updateNotFound() {
        Conselho conselho = Conselho.builder().idConselho(1).build();

        assertThrows(DataNotFoundException.class, () -> {
            service.update(conselho);
        });
    }

    @Test
    void getById() {
        final int id = 1;
        final Optional<Conselho> daoReturn = Optional.empty();
        when(dao.findById(id)).thenReturn(daoReturn);

        final Optional<Conselho> toCheck = service.getById(id);

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