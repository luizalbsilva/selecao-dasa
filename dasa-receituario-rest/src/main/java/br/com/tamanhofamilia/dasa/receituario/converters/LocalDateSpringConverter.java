package br.com.tamanhofamilia.dasa.receituario.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Conversor LocalDate
 */
@Component
public class LocalDateSpringConverter implements Converter<String, LocalDate> {
    /** {@inheritDoc} */
    @Override
    public LocalDate convert(String value) {
        if(value != null) {
            var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(value, formatter);
        } else {
            return null;
        }
    }

}
