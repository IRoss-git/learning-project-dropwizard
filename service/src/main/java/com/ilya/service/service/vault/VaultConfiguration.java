package com.ilya.service.service.vault;

import com.bettercloud.vault.Vault;
import com.bettercloud.vault.VaultConfig;
import com.bettercloud.vault.VaultException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:service.properties")
public class VaultConfiguration {

    @Value("${vault.address}")
    private String address;

    @Value("${vault.token}")
    private String token;

    @Bean
    public VaultConfig vaultConfig() throws VaultException {
        VaultConfig vaultConfig = new VaultConfig();
        vaultConfig.address(address)
                .engineVersion(2)
                .token(token)
                .openTimeout(5)
                .readTimeout(30)
                .build();

        return vaultConfig;
    }

    @Bean
    public Vault vault() throws VaultException {
        return new Vault(vaultConfig());
    }
}
