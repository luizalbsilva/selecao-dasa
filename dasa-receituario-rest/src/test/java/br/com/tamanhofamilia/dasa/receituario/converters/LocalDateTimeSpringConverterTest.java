package br.com.tamanhofamilia.dasa.receituario.converters;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class LocalDateTimeSpringConverterTest {
    private LocalDateTimeSpringConverter converter = new LocalDateTimeSpringConverter();

    @Test
    void convert() {
        assertEquals(
                LocalDateTime.of(1971, Month.MAY, 18, 16, 20,15),
                converter.convert("1971-05-18T16:20:15"));
        assertNull(converter.convert(null));
    }
}