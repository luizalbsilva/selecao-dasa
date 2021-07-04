package br.com.tamanhofamilia.dasa.receituario.services;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IService<T, I> {
    Page<T> findAll(Pageable pageable);

    I create(T exame);
    void update(T exame) throws DataNotFoundException;

    Optional<T> getById(@NonNull I id);

    void delete(@NonNull I id) throws DataNotFoundException;
}
