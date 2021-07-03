package br.com.tamanhofamilia.dasa.receituario.services.exames;

import br.com.tamanhofamilia.dasa.receituario.daos.exame.ExameDao;
import br.com.tamanhofamilia.dasa.receituario.models.exame.Exame;
import br.com.tamanhofamilia.dasa.receituario.services.DataNotFoundException;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExamesService implements IExamesService {
    private final ExameDao dao;

    @Autowired
    ExamesService(ExameDao dao) {
        this.dao = dao;
    }

    @Override
    public Page<Exame> findAll(Pageable pageable) {
        return dao.findAll(pageable);
    }

    @Override
    public Integer create(Exame exame) {
        final Exame saved = dao.save(exame);
        return saved.getIdExame();
    }

    @Override
    public void update(Exame exame) throws DataNotFoundException {
        if ( !dao.existsById(exame.getIdExame()) ) {
            throw new DataNotFoundException(String.format("Exame não encontrado. Id: %d", exame.getIdExame()) );
        }
        dao.save(exame);
    }

    @Override
    public Optional<Exame> getById(@NonNull Integer id) {
        return dao.findById(id);
    }

    @Override
    public void delete(@NonNull Integer id) throws DataNotFoundException {
        if (!dao.existsById(id)) {
            throw new DataNotFoundException(String.format("Exame não encontrado. Id: %d", id) );
        }
        dao.deleteById(id);
    }
}
