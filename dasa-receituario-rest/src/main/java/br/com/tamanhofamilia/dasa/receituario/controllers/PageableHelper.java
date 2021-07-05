package br.com.tamanhofamilia.dasa.receituario.controllers;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;

/**
 * Helper para auxiliar com paginação nos controllers
 */
public class PageableHelper {
    private static final int DEFAULT_PAGE_SIZE = 15;

    private PageableHelper() { }

    /**
     * Cria objeto de paginação de acordo com os parametros passados
     * @param startPage Página inicial
     * @param endPage Página final
     * @param sortField Campo de ordenação
     * @return Objeto de paginação criado
     */
    public static Pageable create(Optional<Integer> startPage, Optional<Integer> endPage, Optional<String> sortField) {
        Pageable pageable = null;
        if (startPage.isEmpty() && endPage.isEmpty() && sortField.isEmpty()) {
            pageable = Pageable.unpaged();
        } else {
            final int startpg = startPage.orElse(0);
            final int endpg = endPage.orElse(DEFAULT_PAGE_SIZE);
            if (sortField.isEmpty()) {
                pageable = PageRequest.of(startpg, endpg);
            } else {
                pageable = PageRequest.of(startpg, endpg, Sort.by(sortField.get()));
            }
        }
        return pageable;
    }
}
