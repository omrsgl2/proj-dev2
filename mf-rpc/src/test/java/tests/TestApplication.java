package tests;

import org.openlegacy.datasource.OLDatasourceAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;

/**
 * Simple application to bootstrap tests 
 */
@SpringBootApplication(exclude = {OLDatasourceAutoConfiguration.class, SecurityAutoConfiguration.class})
public class TestApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(TestApplication.class, args);
	}
	
}
