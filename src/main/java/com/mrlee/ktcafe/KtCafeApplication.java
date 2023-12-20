package com.mrlee.ktcafe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class KtCafeApplication {

	public static void main(String[] args) {
		SpringApplication.run(KtCafeApplication.class, args);
	}

}
