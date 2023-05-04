package fom.pmse.crms.backend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@Slf4j
public class BackendApplication {

	public static void main(String[] args) {
		log.info("Starting BackendApplication");
		SpringApplication.run(BackendApplication.class, args);
	}

}
