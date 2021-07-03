package br.com.tamanhofamilia.dasa.receituario.services.exames;

import br.com.tamanhofamilia.dasa.receituario.models.exame.Exame;
import br.com.tamanhofamilia.dasa.receituario.services.DataNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IExamesService {
    Page<Exame> findAll(Pageable pageable);

    int create(Exame exame);
    void update(Exame exame) throws DataNotFoundException;

    Optional<Exame> getById(int id);

    void delete(int id) throws DataNotFoundException;
}
