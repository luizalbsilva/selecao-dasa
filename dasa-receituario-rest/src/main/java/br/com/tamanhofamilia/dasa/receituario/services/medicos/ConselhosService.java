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

@Service
public class ConselhosService implements IConselhosService {
    private final ConselhoDao dao;

    @Autowired
    public ConselhosService(ConselhoDao dao) {
        this.dao = dao;
    }


    @Override
    public Page<Conselho> findAll(Pageable pageable) {
        return dao.findAll(pageable);
    }

    @Override
    public Integer create(Conselho conselho) {
        final Conselho saved = dao.save(conselho);
        return saved.getIdConselho();
    }

    @Override
    public void update(Conselho conselho) throws DataNotFoundException {
        if ( !dao.existsById(conselho.getIdConselho()) ) {
            throw new DataNotFoundException(String.format("Exame não encontrado. Id: %d", conselho.getIdConselho()) );
        }
        dao.save(conselho);
    }

    @Override
    public Optional<Conselho> getById(@NonNull Integer id) {
        return dao.findById(id);
    }

    @Override
    public void delete(@NonNull Integer id) throws DataNotFoundException {
        if (!dao.existsById(id)) {
            throw new DataNotFoundException(String.format("Conselho não encontrado. Id: %d", id) );
        }
        dao.deleteById(id);
    }
}
