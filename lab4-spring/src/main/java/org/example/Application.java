package org.example;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
		dotenv.entries().forEach(entry ->
				System.setProperty(entry.getKey(), entry.getValue())
		);

		String dbUrl = System.getProperty("spring.datasource.url", "");
		if (dbUrl.contains("host.docker.internal")) {
			try {
				java.net.InetAddress.getByName("host.docker.internal");
			} catch (Exception e) {
				System.setProperty("spring.datasource.url",
						dbUrl.replace("host.docker.internal", "localhost"));
			}
		}

		SpringApplication.run(Application.class, args);
	}
}