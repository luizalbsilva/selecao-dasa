package br.com.tamanhofamilia.dasa.receituario.services.exames;

import br.com.tamanhofamilia.dasa.receituario.daos.exame.ExameDao;
import br.com.tamanhofamilia.dasa.receituario.models.exame.Exame;
import br.com.tamanhofamilia.dasa.receituario.services.DataNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExamesServiceTest {
    @InjectMocks
    ExamesService service;

    @Mock
    ExameDao dao;

    @Mock
    Pageable pageable;

    @Test
    void findAll() {
        service.findAll(pageable);

        verify(dao).findAll(pageable);
    }

    @Test
    void create() {
        Exame exame = new Exame();
        when(dao.save(exame)).thenAnswer(e -> {
            Exame exam = e.getArgument(0);
            exam.setIdExame(1);
            return exam;
        });

        assertSame(1, service.create(exame));

        verify(dao).save(exame);

    }


    @Test
    void update() throws DataNotFoundException {
        final int id = 1;
        when(dao.existsById(id)).thenReturn(true);
        Exame exame = Exame.builder().idExame(id).build();

        service.update(exame);

        verify(dao).save(exame);
    }

    @Test
    void updateNotFound() {
        Exame exame = Exame.builder().idExame(1).build();

        assertThrows(DataNotFoundException.class, () -> {
            service.update(exame);
        });
    }

    @Test
    void getById() {
        final int id = 1;
        final Optional<Exame> daoReturn = Optional.empty();
        when(dao.findById(id)).thenReturn(daoReturn);

        final Optional<Exame> toCheck = service.getById(id);

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