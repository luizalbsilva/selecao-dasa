package br.com.tamanhofamilia.dasa.receituario.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IService<T> {
    Page<T> findAll(Pageable pageable);

    int create(T exame);
    void update(T exame) throws DataNotFoundException;

    Optional<T> getById(int id);

    void delete(int id) throws DataNotFoundException;
}
