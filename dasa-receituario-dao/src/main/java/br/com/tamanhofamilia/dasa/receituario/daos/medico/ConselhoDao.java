package br.com.tamanhofamilia.dasa.receituario.daos.medico;

import br.com.tamanhofamilia.dasa.receituario.models.medico.Conselho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConselhoDao extends JpaRepository<Conselho, Integer> {
}
