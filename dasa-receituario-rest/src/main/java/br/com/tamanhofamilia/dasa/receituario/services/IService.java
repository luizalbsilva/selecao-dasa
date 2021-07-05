package br.com.tamanhofamilia.dasa.receituario.services;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Servicos básico de crud
 * @param <T> Entidade
 * @param <I> Identificador
 */
public interface IService<T, I> {
    /**
     * Lista todos os dados, com recursos de paginação
     * @param pageable Recursos de paginação
     * @return Dados requisitados
     */
    Page<T> findAll(Pageable pageable);

    /**
     * Cria novo registro
     * @param data Dados a serem persistidos
     * @return Identificador criado
     */
    I create(T data);

    /**
     * Altera os dados do registro
     * @param data Dados a serem alterados
     * @throws DataNotFoundException Registro não encontrado
     */
    void update(T data) throws DataNotFoundException;

    /**
     * Recupera os dados do registro pelo identificador
     * @param id Identificador do registro
     * @return Dados do registro
     */
    Optional<T> getById(@NonNull I id);

    /**
     * Apaga os dados do registro
     * @param id Identificador do registro
     * @throws DataNotFoundException
     */
    void delete(@NonNull I id) throws DataNotFoundException;
}
