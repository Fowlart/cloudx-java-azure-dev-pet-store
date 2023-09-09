package io.swagger.configuration;

import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;


@Configuration
public class DataSourceConfig {

    // get the password from the key vault
    private String getPassword() {

        String keyVaultName = System.getenv("KEY_VAULT_NAME");

        String keyVaultUri = "https://" + keyVaultName + ".vault.azure.net";

        SecretClient secretClient = new SecretClientBuilder()
                .vaultUrl(keyVaultUri)
                .credential(new DefaultAzureCredentialBuilder().build())
                .buildClient();

        // get the secret value from the key vault
       return secretClient.getSecret("PostgresUserPassword").getValue();
    }

    private String getPostgresUrl() {

        String keyVaultName = System.getenv("KEY_VAULT_NAME");

        String keyVaultUri = "https://" + keyVaultName + ".vault.azure.net";

        SecretClient secretClient = new SecretClientBuilder()
                .vaultUrl(keyVaultUri)
                .credential(new DefaultAzureCredentialBuilder().build())
                .buildClient();

        // get the secret value from the key vault
       return secretClient.getSecret("PostgresUrl").getValue();
    }

    private String getPostgresUser() {

        String keyVaultName = System.getenv("KEY_VAULT_NAME");

        String keyVaultUri = "https://" + keyVaultName + ".vault.azure.net";

        SecretClient secretClient = new SecretClientBuilder()
                .vaultUrl(keyVaultUri)
                .credential(new DefaultAzureCredentialBuilder().build())
                .buildClient();

        // get the secret value from the key vault
       return secretClient.getSecret("PostgresUserName").getValue();
    }

    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.postgresql.Driver");
        dataSourceBuilder.url(getPostgresUrl());
        dataSourceBuilder.username(getPostgresUser());
        dataSourceBuilder.password(getPassword());
        return dataSourceBuilder.build();
    }

}
