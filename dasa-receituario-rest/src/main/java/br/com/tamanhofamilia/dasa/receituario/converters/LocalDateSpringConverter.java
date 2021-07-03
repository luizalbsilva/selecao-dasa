package br.com.tamanhofamilia.dasa.receituario.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class LocalDateSpringConverter implements Converter<String, LocalDate> {

    @Override
    public LocalDate convert(String value) {
        if(value != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(value, formatter);
        }else {
            return null;
        }
    }

}
