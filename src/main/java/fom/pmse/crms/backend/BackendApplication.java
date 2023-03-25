package fom.pmse.crms.backend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class BackendApplication {

	public static void main(String[] args) {
		log.info("Starting BackendApplication");
		SpringApplication.run(BackendApplication.class, args);
	}

}
