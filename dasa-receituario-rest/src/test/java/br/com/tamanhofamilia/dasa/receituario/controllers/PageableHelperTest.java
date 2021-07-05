package br.com.tamanhofamilia.dasa.receituario.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PageableHelperTest {

    @Test
    void createNoArgs() {
        assertSame(Pageable.unpaged(), PageableHelper.create(Optional.empty(), Optional.empty(), Optional.empty()));
    }

    @Test
    void createOnlyPageNumber() {
        var pageable = PageableHelper.create(Optional.of(5), Optional.empty(), Optional.empty());
        assertEquals(5, pageable.getPageNumber());
        assertEquals(15, pageable.getPageSize());
        assertFalse(pageable.getSort().isSorted());
    }

    @Test
    void createOnlyPageSize() {
        var pageable = PageableHelper.create(Optional.empty(), Optional.of(5), Optional.empty());
        assertEquals(0, pageable.getPageNumber());
        assertEquals(5, pageable.getPageSize());
        assertFalse(pageable.getSort().isSorted());
    }

    @Test
    void createOnlyOrder() {
        var pageable = PageableHelper.create(Optional.empty(), Optional.empty(), Optional.of("Campo1"));
        assertEquals(0, pageable.getPageNumber());
        assertEquals(15, pageable.getPageSize());
        assertTrue(pageable.getSort().isSorted());
        final List<Sort.Order> orders = pageable.getSort().get().collect(Collectors.toList());
        assertEquals(1, orders.size() );
        assertEquals("Campo1", orders.get(0).getProperty() );
    }
}