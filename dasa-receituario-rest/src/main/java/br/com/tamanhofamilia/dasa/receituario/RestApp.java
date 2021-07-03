package br.com.tamanhofamilia.dasa.receituario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@ComponentScan(basePackageClasses = RestApp.class)
public class RestApp {

	public static void main(String[] args) {
		SpringApplication.run(RestApp.class, args);
	}

}
