package payment_processor;

import com.jw.ApiException;
import com.jw.ApiResponse;
import com.jw.api.PaymentProcessorApi;
import com.jw.model.CreateUpdatePaymentProcessor;
import com.jw.model.ReadPaymentProcessor;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PaymentProcessorIT {

    private PaymentProcessorApi paymentProcessorApi = new PaymentProcessorApi();

    @Test
    public void createPaymentProcessorTest() throws ApiException {
        CreateUpdatePaymentProcessor createPaymentProcessor = createPaymentProcessor();

        ApiResponse<ReadPaymentProcessor> response = paymentProcessorApi.createPaymentProcessorWithHttpInfo(createPaymentProcessor);

        assertThat(response.getData(),
                allOf(
                        hasProperty("key", equalTo(createPaymentProcessor.getKey())),
                        hasProperty("name", equalTo(createPaymentProcessor.getName())),
                        hasProperty("description", notNullValue())));
        assertEquals(Response.Status.CREATED.getStatusCode(),
                response.getStatusCode());
    }

    @Test
    public void getPaymentProcessorTest() throws ApiException {
        CreateUpdatePaymentProcessor createPaymentProcessor = createPaymentProcessor();

        ReadPaymentProcessor readPaymentProcessor = paymentProcessorApi.createPaymentProcessor(createPaymentProcessor);

        ApiResponse<ReadPaymentProcessor> response = paymentProcessorApi.getPaymentProcessorWithHttpInfo(readPaymentProcessor.getId());

        assertThat(response.getData(),
                allOf(
                        hasProperty("key", equalTo(createPaymentProcessor.getKey())),
                        hasProperty("name", equalTo(createPaymentProcessor.getName())),
                        hasProperty("description", notNullValue())));
        assertThat(response.getStatusCode(), equalTo(Response.Status.OK.getStatusCode()));
    }

    @Test
    public void getAllPaymentProcessorsTest() throws ApiException {
        CreateUpdatePaymentProcessor createPaymentProcessor = createPaymentProcessor();
        CreateUpdatePaymentProcessor createPaymentProcessor2 = createPaymentProcessor();

        paymentProcessorApi.createPaymentProcessor(createPaymentProcessor2);
        paymentProcessorApi.createPaymentProcessor(createPaymentProcessor);

        ApiResponse<List<ReadPaymentProcessor>> response = paymentProcessorApi.getPaymentProcessorsWithHttpInfo(1L, 10L);

            assertThat(response.getData().size(), greaterThan(1));
            assertThat(response.getStatusCode(), equalTo(Response.Status.OK.getStatusCode()));
    }

    @Test
    public void deletePaymentProcessorTest() throws ApiException {
        CreateUpdatePaymentProcessor createPaymentProcessor = createPaymentProcessor();

        ReadPaymentProcessor readPaymentProcessor = paymentProcessorApi.createPaymentProcessor(createPaymentProcessor);
        ApiResponse<Void> response = paymentProcessorApi.deletePaymentProcessorWithHttpInfo(readPaymentProcessor.getId());

        assertThat(response.getStatusCode(), equalTo(Response.Status.NO_CONTENT.getStatusCode()));
    }

    @Test
    public void updateTest() throws ApiException {
        CreateUpdatePaymentProcessor createPaymentProcessor = createPaymentProcessor();

        ReadPaymentProcessor readPaymentProcessor = paymentProcessorApi.createPaymentProcessor(createPaymentProcessor);

        CreateUpdatePaymentProcessor updatePaymentProcessor = new CreateUpdatePaymentProcessor()
                .key(RandomStringUtils.randomAlphabetic(10).concat("key"))
                .name("new name")
                .description("new description");

        ApiResponse<ReadPaymentProcessor> response = paymentProcessorApi.updatePaymentProcessorWithHttpInfo(readPaymentProcessor.getId(), updatePaymentProcessor);

        assertThat(response.getData(),
                allOf(
                        hasProperty("key", equalTo(updatePaymentProcessor.getKey())),
                        hasProperty("name", equalTo(updatePaymentProcessor.getName())),
                        hasProperty("description", notNullValue())));
        assertThat(response.getStatusCode(), equalTo(Response.Status.OK.getStatusCode()));
    }


    @Test
    public void deleteNonExisting() {
        String nonExistingId = "123e4567-e89b-12d3-a456-426614174000";

        var ex = assertThrows(ApiException.class, () -> paymentProcessorApi.deletePaymentProcessorWithHttpInfo(nonExistingId));

        assertThat(ex.getCode(), equalTo(404));
        assertThat(ex.getMessage(), equalTo(String.format("Payment processor with id %s not found", nonExistingId)));
    }

    @Test
    public void updateNonExisting() {
        String nonExistingId = "123e4567-e89b-12d3-a456-426614174000";

        CreateUpdatePaymentProcessor createPaymentProcessor = createPaymentProcessor();

        var ex = assertThrows(ApiException.class, () -> paymentProcessorApi.updatePaymentProcessorWithHttpInfo(nonExistingId, createPaymentProcessor));


        assertThat(ex.getCode(), equalTo(404));
        assertThat(ex.getMessage(), equalTo(String.format("Payment processor with id %s not found", nonExistingId)));
    }

    @Test
    public void updateExistingKey() throws ApiException {
        CreateUpdatePaymentProcessor createPaymentProcessor = createPaymentProcessor();
        ReadPaymentProcessor readPaymentProcessor = paymentProcessorApi.createPaymentProcessor(createPaymentProcessor);

        var ex = assertThrows(ApiException.class, () -> paymentProcessorApi.updatePaymentProcessor(readPaymentProcessor.getId(), createPaymentProcessor));

        assertThat(ex.getCode(), equalTo(409));
        assertThat(ex.getMessage(), equalTo(String.format("Key: %s already exists", readPaymentProcessor.getKey())));
    }

    @Test
    public void createWithExistingKey() throws ApiException {
        CreateUpdatePaymentProcessor createPaymentProcessor = createPaymentProcessor();
        paymentProcessorApi.createPaymentProcessor(createPaymentProcessor);

        var ex = assertThrows(ApiException.class, () -> paymentProcessorApi.createPaymentProcessorWithHttpInfo(createPaymentProcessor));

        assertThat(ex.getCode(), equalTo(409));
        assertThat(ex.getMessage(), equalTo(String.format("Key: %s already exists", createPaymentProcessor.getKey())));
    }

    @Test
    public void getNonExistingPerson() {
        String nonExistingId = "123e4567-e89b-12d3-a456-426614174000";

        var ex = assertThrows(ApiException.class, () -> paymentProcessorApi.getPaymentProcessorWithHttpInfo(nonExistingId));

        assertThat(ex.getCode(), equalTo(404));
        assertThat(ex.getMessage(), equalTo(String.format("Payment processor with id %s not found", nonExistingId)));
    }

    CreateUpdatePaymentProcessor createPaymentProcessor() {
        return new CreateUpdatePaymentProcessor()
                .key(RandomStringUtils.randomAlphabetic(10).concat("key"))
                .name("name: ".concat(RandomStringUtils.randomAlphabetic(10)))
                .description("description: ".concat(RandomStringUtils.randomAlphabetic(10)));
    }

}
