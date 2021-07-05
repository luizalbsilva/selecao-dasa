package br.com.tamanhofamilia.dasa.receituario.daos.exame;

import br.com.tamanhofamilia.dasa.receituario.models.exame.Exame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface que define acesso aos dados de Exame
 * @see Exame
 */
@Repository
public interface ExameDao extends JpaRepository<Exame, Integer> {
}
