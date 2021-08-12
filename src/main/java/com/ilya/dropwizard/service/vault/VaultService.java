package com.ilya.dropwizard.service.vault;

import com.bettercloud.vault.Vault;
import com.bettercloud.vault.VaultConfig;
import com.bettercloud.vault.VaultException;
import com.bettercloud.vault.response.LogicalResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.learn.dropwizard.model.CreateContentDTO;
import com.learn.dropwizard.model.ReadContentDTO;

import javax.ws.rs.NotFoundException;
import java.util.HashMap;
import java.util.Map;


@Service
public class VaultService {

    @Autowired
    private Vault vault;

    public static final String PATH = "kv/test";

    public ReadContentDTO read(String key) {
        ReadContentDTO readContentDTO = new ReadContentDTO();
        try {
            String value = vault.logical().read(PATH).getData().get(key);
            if(value==null){
                throw new NotFoundException("Value with provided key not found");
            }
            readContentDTO.setValue(value);
        } catch (VaultException e) {
            e.printStackTrace();
        }
        return readContentDTO;
    }

    public void write(CreateContentDTO createContentDTO) {
        String key = createContentDTO.getKey();
        String value = createContentDTO.getValue();

        final Map<String, Object> secrets = new HashMap<>();
        secrets.put(key, value);

        try {
            LogicalResponse writeResponse = vault.logical()
                    .write(PATH, secrets);
            System.out.println(writeResponse.toString());
        } catch (VaultException e) {
            e.printStackTrace();
        }

    }
}

