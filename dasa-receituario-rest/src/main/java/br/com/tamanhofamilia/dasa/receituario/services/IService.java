package br.com.tamanhofamilia.dasa.receituario.services;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IService<T, ID> {
    Page<T> findAll(Pageable pageable);

    ID create(T exame);
    void update(T exame) throws DataNotFoundException;

    Optional<T> getById(@NonNull ID id);

    void delete(@NonNull ID id) throws DataNotFoundException;
}
