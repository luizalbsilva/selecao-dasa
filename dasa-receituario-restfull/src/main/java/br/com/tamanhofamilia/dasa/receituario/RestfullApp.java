package br.com.tamanhofamilia.dasa.receituario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "br.com.tamanhofamilia.dasa.receituario.restfulldaos")
@EntityScan(basePackages = "br.com.tamanhofamilia.dasa.receituario.models")
@EnableJpaRepositories(basePackages = "br.com.tamanhofamilia.dasa.receituario.restfulldaos")
public class RestfullApp {

    public static void main(String[] args) {
        SpringApplication.run(RestfullApp.class, args);
    }
}
