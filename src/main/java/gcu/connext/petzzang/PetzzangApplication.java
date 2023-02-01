package gcu.connext.petzzang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
public class PetzzangApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetzzangApplication.class, args);
	}

}
