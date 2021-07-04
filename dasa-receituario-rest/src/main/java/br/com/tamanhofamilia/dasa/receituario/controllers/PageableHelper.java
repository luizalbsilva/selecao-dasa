package br.com.tamanhofamilia.dasa.receituario.controllers;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;

public class PageableHelper {
    private PageableHelper() { }

    public static Pageable create(Optional<Integer> startPage, Optional<Integer> endPage, Optional<String> sortField) {
        Pageable pageable = null;
        if (startPage.isEmpty() && endPage.isEmpty() && sortField.isEmpty()) {
            pageable = Pageable.unpaged();
        } else {
            int startpg = startPage.orElse(0);
            int endpg = endPage.orElse(1);
            if (endpg < startpg) {
                endpg = startpg;
            }
            if (sortField.isEmpty()) {
                pageable = PageRequest.of(startpg, endpg);
            } else {
                pageable = PageRequest.of(startpg, endpg, Sort.by(sortField.get()));
            }
        }
        return pageable;
    }
}
