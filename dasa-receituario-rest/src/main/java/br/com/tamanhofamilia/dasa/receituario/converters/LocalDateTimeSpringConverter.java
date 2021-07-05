package br.com.tamanhofamilia.dasa.receituario.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/** Conversor LocalDateTime */
@Component
public class LocalDateTimeSpringConverter implements Converter<String, LocalDateTime> {
    /** {@inheritDoc} */
    @Override
    public LocalDateTime convert(String value) {
        if(value != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            return LocalDateTime.parse(value, formatter);
        }else {
            return null;
        }
    }

}