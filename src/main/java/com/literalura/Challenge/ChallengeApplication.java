package com.literalura.Challenge;

import com.literalura.Challenge.principal.Principal;
import com.literalura.Challenge.repository.AutorRepository;
import com.literalura.Challenge.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.literalura.Challenge"})
public class ChallengeApplication implements CommandLineRunner {

	@Autowired
	private  LibroRepository repository;
	@Autowired
	private  AutorRepository autorRepository;

	public static void main(String[] args) {
		SpringApplication.run(ChallengeApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {

		Principal principal = new Principal(autorRepository, repository);
		principal.muestraElMenu();
	}
}
