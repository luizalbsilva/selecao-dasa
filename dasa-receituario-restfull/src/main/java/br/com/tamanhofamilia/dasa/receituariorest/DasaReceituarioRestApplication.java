package br.com.tamanhofamilia.dasa.receituariorest;

import br.com.tamanhofamilia.dasa.receituario.daos.exame.ExameDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "br.com.tamanhofamilia.dasa.receituario.models")
@ComponentScan(basePackages = "br.com.tamanhofamilia.dasa")
@EnableJpaRepositories(basePackages = "br.com.tamanhofamilia.dasa")
public class DasaReceituarioRestApplication {
	public static void main(String[] args) {
		SpringApplication.run(DasaReceituarioRestApplication.class, args);
	}
}
