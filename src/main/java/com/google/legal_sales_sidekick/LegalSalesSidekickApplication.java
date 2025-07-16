package com.google.legal_sales_sidekick;

import com.google.legal_sales_sidekick.model.User;
import com.google.legal_sales_sidekick.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LegalSalesSidekickApplication {

	public static void main(String[] args) {
		SpringApplication.run(LegalSalesSidekickApplication.class, args);
	}
}
