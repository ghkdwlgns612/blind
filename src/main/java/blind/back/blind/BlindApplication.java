package blind.back.blind;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BlindApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlindApplication.class, args);
	}

}
