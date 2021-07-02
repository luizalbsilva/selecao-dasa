package br.com.tamanhofamilia.dasa.receituario.daos.medico;

import br.com.tamanhofamilia.dasa.receituario.models.medico.Conselho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "conselhos", collectionResourceRel = "conselhos")
public interface ConselhoDao extends JpaRepository<Conselho, Long> {
}
