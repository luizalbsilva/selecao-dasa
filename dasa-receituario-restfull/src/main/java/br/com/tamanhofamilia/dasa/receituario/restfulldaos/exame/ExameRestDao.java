package br.com.tamanhofamilia.dasa.receituario.restfulldaos.exame;

import br.com.tamanhofamilia.dasa.receituario.daos.exame.ExameDao;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "exames", collectionResourceRel = "exames")
public interface ExameRestDao extends ExameDao {
}
