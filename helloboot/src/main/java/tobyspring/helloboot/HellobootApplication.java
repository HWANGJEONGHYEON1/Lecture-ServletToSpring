package tobyspring.helloboot;

import config.MySpringBootAnnotation;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@MySpringBootAnnotation
public class HellobootApplication {

	@Bean
	ApplicationRunner applicationRunner(Environment environment) {
		return args -> {
			String property = environment.getProperty("my.name");
			String profile = environment.getProperty("profile");

			System.out.println("my.name " + property);
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(HellobootApplication.class, args);
	}
}


