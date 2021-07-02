package br.com.tamanhofamilia.dasa.receituario.daos.exame;

import br.com.tamanhofamilia.dasa.receituario.models.exame.Exame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "exames", collectionResourceRel = "exames")
public interface ExameDao extends JpaRepository<Exame, Long> {
}
