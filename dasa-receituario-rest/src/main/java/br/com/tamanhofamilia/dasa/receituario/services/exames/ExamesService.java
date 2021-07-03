package br.com.tamanhofamilia.dasa.receituario.services.exames;

import br.com.tamanhofamilia.dasa.receituario.daos.exame.ExameDao;
import br.com.tamanhofamilia.dasa.receituario.models.exame.Exame;
import br.com.tamanhofamilia.dasa.receituario.services.DataNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExamesService implements IExamesService {
    private final ExameDao dao;

    ExamesService(ExameDao dao) {
        this.dao = dao;
    }

    @Override
    public Page<Exame> findAll(Pageable pageable) {
        return dao.findAll(pageable);
    }

    @Override
    public int create(Exame exame) {
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
    public Optional<Exame> getById(int id) {
        return dao.findById(id);
    }

    @Override
    public void delete(int id) throws DataNotFoundException {
        if (!dao.existsById(id)) {
            throw new DataNotFoundException(String.format("Exame não encontrado. Id: %d", id) );
        }
        dao.deleteById(id);
    }
}
