package br.com.tamanhofamilia.dasa.receituario.converters;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class LocalDateSpringConverterTest {
    private LocalDateSpringConverter converter = new LocalDateSpringConverter();

    @Test
    void convert() {
        assertEquals(LocalDate.of(1930, Month.AUGUST, 5), converter.convert("1930-08-05") );
        assertNull( converter.convert(null) );
    }
}