package gcu.connext.petzzang.petzzang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class PetzzangApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetzzangApplication.class, args);
	}

}
