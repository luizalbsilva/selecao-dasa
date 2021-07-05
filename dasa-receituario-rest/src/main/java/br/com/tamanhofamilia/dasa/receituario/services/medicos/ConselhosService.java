package br.com.tamanhofamilia.dasa.receituario.services.medicos;

import br.com.tamanhofamilia.dasa.receituario.daos.medico.ConselhoDao;
import br.com.tamanhofamilia.dasa.receituario.models.medico.Conselho;
import br.com.tamanhofamilia.dasa.receituario.services.DataNotFoundException;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Serviço de conselhos profissionais
 */
@Service
public class ConselhosService implements IConselhosService {
    /** Dao de acesso aos Conselhos Profissionais */
    private final ConselhoDao dao;

    @Autowired
    public ConselhosService(ConselhoDao dao) {
        this.dao = dao;
    }

    /** {@inheritDoc} */
    @Override
    public Page<Conselho> findAll(Pageable pageable) {
        return dao.findAll(pageable);
    }

    /** {@inheritDoc} */
    @Override
    public Integer create(Conselho data) {
        final Conselho saved = dao.save(data);
        return saved.getIdConselho();
    }

    /** {@inheritDoc} */
    @Override
    public void update(Conselho data) throws DataNotFoundException {
        if (!dao.existsById(data.getIdConselho())) {
            throw new DataNotFoundException(String.format("Exame não encontrado. Id: %d", data.getIdConselho()));
        }
        dao.save(data);
    }

    /** {@inheritDoc} */
    @Override
    public Optional<Conselho> getById(@NonNull Integer id) {
        return dao.findById(id);
    }

    /** {@inheritDoc} */
    @Override
    public void delete(@NonNull Integer id) throws DataNotFoundException {
        if (!dao.existsById(id)) {
            throw new DataNotFoundException(String.format("Conselho não encontrado. Id: %d", id));
        }
        dao.deleteById(id);
    }
}
