package io.swagger;


import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;
import com.chtrembl.petstore.order.model.ContainerEnvironment;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableCaching
@EnableSwagger2
@ComponentScan(basePackages = { "io.swagger", "com.chtrembl.petstore.order.api", "io.swagger.configuration", "com.chtrembl.petstore.order.data"})
@EntityScan(basePackages = "com.chtrembl.petstore.order.model")
public class Swagger2SpringBoot implements CommandLineRunner {

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public SecretClient secretClient() {
		String keyVaultUri = "https://fowlart-key-vault.vault.azure.net/";

		SecretClient secretClient = new SecretClientBuilder()
				.vaultUrl(keyVaultUri)
				.credential(new DefaultAzureCredentialBuilder().build())
				.buildClient();

		return secretClient;
	}

	/**
	@Bean
	public ContainerEnvironment containerEnvvironment() {
		return new ContainerEnvironment();
	}
	**/

	@Override
	public void run(String... arg0) throws Exception {
		if (arg0.length > 0 && arg0[0].equals("exitcode")) {
			throw new ExitException();
		}
	}

	public static void main(String[] args) throws Exception {
		new SpringApplication(Swagger2SpringBoot.class).run(args);
	}

	class ExitException extends RuntimeException implements ExitCodeGenerator {
		private static final long serialVersionUID = 1L;

		@Override
		public int getExitCode() {
			return 10;
		}

	}
}
