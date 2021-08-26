package com.ilya.service.service.vault;

import com.bettercloud.vault.Vault;
import com.bettercloud.vault.VaultException;
import com.bettercloud.vault.response.LogicalResponse;
import com.learn.dropwizard.model.CreateDataDTO;
import com.learn.dropwizard.model.ReadDataDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.HashMap;
import java.util.Map;


@Service
public class VaultService {

    @Autowired
    private Vault vault;

    public static final String PATH = "kv/test";

    public ReadDataDTO read(String key) {
        ReadDataDTO readDataDTO = new ReadDataDTO();
        try {
            String value = vault.logical().read(PATH).getData().get(key);
            if(value==null){
                throw new NotFoundException("Value with provided key not found");
            }
            readDataDTO.setValue(value);
        } catch (VaultException e) {
            e.printStackTrace();
        }
        return readDataDTO;
    }

    public void write(CreateDataDTO createDataDTO) {
        String key = createDataDTO.getKey();
        String value = createDataDTO.getValue();

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

