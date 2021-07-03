package br.com.tamanhofamilia.dasa.receituario.daos.medico;

import br.com.tamanhofamilia.dasa.receituario.models.medico.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicoDao extends JpaRepository<Medico, Integer> {
}
