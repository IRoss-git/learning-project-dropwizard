package util;

import com.jw.ApiClient;
import com.jw.ServerConfiguration;

import java.util.HashMap;
import java.util.List;

public final class ApiClientUtil {

    public static ApiClient getApiClient(){
        ApiClient apiClient = new ApiClient();
        setHost(apiClient);
        return apiClient;
    }


    private static void setHost(ApiClient client){
        client.setServers(List.of(new ServerConfiguration("http://localhost:8080/payment-error-processor", "No description provided", new HashMap())));
    }
}
