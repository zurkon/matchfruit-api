package br.com.fiap.matchfruit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class MatchfruitapiApplication extends SpringBootServletInitializer {
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	    	return application.sources(Application.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(MatchfruitapiApplication.class, args);
	}

}
