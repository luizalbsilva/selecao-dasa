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

/** Serviço de exame */
@Service
public class ExamesService implements IExamesService {
    /** Dao de acesso aos dados de Exames */
    private final ExameDao dao;

    @Autowired
    ExamesService(ExameDao dao) {
        this.dao = dao;
    }

    /** {@inheritDoc} */
    @Override
    public Page<Exame> findAll(Pageable pageable) {
        return dao.findAll(pageable);
    }

    /** {@inheritDoc} */
    @Override
    public Integer create(Exame data) {
        final Exame saved = dao.save(data);
        return saved.getIdExame();
    }

    /** {@inheritDoc} */
    @Override
    public void update(Exame data) throws DataNotFoundException {
        if ( !dao.existsById(data.getIdExame()) ) {
            throw new DataNotFoundException(String.format("Exame não encontrado. Id: %d", data.getIdExame()) );
        }
        dao.save(data);
    }

    /** {@inheritDoc} */
    @Override
    public Optional<Exame> getById(@NonNull Integer id) {
        return dao.findById(id);
    }

    /** {@inheritDoc} */
    @Override
    public void delete(@NonNull Integer id) throws DataNotFoundException {
        if (!dao.existsById(id)) {
            throw new DataNotFoundException(String.format("Exame não encontrado. Id: %d", id) );
        }
        dao.deleteById(id);
    }
}
