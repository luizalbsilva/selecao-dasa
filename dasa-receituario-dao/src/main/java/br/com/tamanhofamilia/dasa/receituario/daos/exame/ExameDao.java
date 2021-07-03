package br.com.tamanhofamilia.dasa.receituario.daos.exame;

import br.com.tamanhofamilia.dasa.receituario.models.exame.Exame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExameDao extends JpaRepository<Exame, Integer> {
}
