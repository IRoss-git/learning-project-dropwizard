package payment_failure_reason;

import com.jw.ApiException;
import com.jw.ApiResponse;
import com.jw.api.PaymentFailureReasonApi;
import com.jw.api.PaymentProcessorApi;
import com.jw.model.CreateUpdatePaymentFailureReason;
import com.jw.model.CreateUpdatePaymentProcessor;
import com.jw.model.ReadGenericPaymentFailureReason;
import com.jw.model.ReadPaymentFailureReason;
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

public class PaymentFailureReasonIT {
    private final PaymentFailureReasonApi paymentFailureReasonApi = new PaymentFailureReasonApi();

    private final PaymentProcessorApi paymentProcessorApi = new PaymentProcessorApi();

    @Test
    public void createPaymentFailureReasonTest() throws ApiException {
        CreateUpdatePaymentFailureReason createUpdatePaymentFailureReason = createPaymentFailureReason();
        ReadPaymentProcessor paymentProcessor = paymentProcessorApi.createPaymentProcessor(createPaymentProcessor());

        ApiResponse<ReadPaymentFailureReason> response = paymentFailureReasonApi.createPaymentFailureReasonWithHttpInfo(paymentProcessor.getId(),createUpdatePaymentFailureReason);

        assertThat(response.getData(),
                allOf(
                        hasProperty("code", equalTo(createUpdatePaymentFailureReason.getCode())),
                        hasProperty("name", equalTo(createUpdatePaymentFailureReason.getName())),
                        hasProperty("description", notNullValue())));
        assertEquals(Response.Status.CREATED.getStatusCode(),
                response.getStatusCode());
    }

    @Test
    public void getPaymentFailureReasonTest() throws ApiException {
        CreateUpdatePaymentFailureReason createUpdatePaymentFailureReason = createPaymentFailureReason();
        ReadPaymentProcessor paymentProcessor = paymentProcessorApi.createPaymentProcessor(createPaymentProcessor());
        ReadPaymentFailureReason readPaymentFailureReason = paymentFailureReasonApi.createPaymentFailureReason(paymentProcessor.getId(),createUpdatePaymentFailureReason);

        ApiResponse<ReadPaymentFailureReason> response = paymentFailureReasonApi.getPaymentFailureReasonWithHttpInfo(paymentProcessor.getId(),readPaymentFailureReason.getId());

        assertThat(response.getData(),
                allOf(
                        hasProperty("code", equalTo(createUpdatePaymentFailureReason.getCode())),
                        hasProperty("name", equalTo(createUpdatePaymentFailureReason.getName())),
                        hasProperty("description", notNullValue())));
        assertThat(response.getStatusCode(), equalTo(Response.Status.OK.getStatusCode()));
    }

    @Test
    public void getAllPaymentFailureReasonsTest() throws ApiException {
        CreateUpdatePaymentFailureReason createUpdatePaymentFailureReason = createPaymentFailureReason();
        CreateUpdatePaymentFailureReason createUpdatePaymentFailureReason2 = createPaymentFailureReason();
        ReadPaymentProcessor paymentProcessor = paymentProcessorApi.createPaymentProcessor(createPaymentProcessor());
        paymentFailureReasonApi.createPaymentFailureReason(paymentProcessor.getId(),createUpdatePaymentFailureReason);
        paymentFailureReasonApi.createPaymentFailureReason(paymentProcessor.getId(),createUpdatePaymentFailureReason2);


        ApiResponse<List<ReadPaymentFailureReason>> response = paymentFailureReasonApi.getAllPaymentFailureReasonByPaymentProcessorWithHttpInfo(paymentProcessor.getId(),1L, 10L);

        assertThat(response.getData().size(), greaterThan(1));
        assertThat(response.getStatusCode(), equalTo(Response.Status.OK.getStatusCode()));
    }

    @Test
    public void deletePaymentFailureReasonTest() throws ApiException {
        CreateUpdatePaymentFailureReason createUpdatePaymentFailureReason = createPaymentFailureReason();
        ReadPaymentProcessor paymentProcessor = paymentProcessorApi.createPaymentProcessor(createPaymentProcessor());
        ReadPaymentFailureReason readPaymentFailureReason = paymentFailureReasonApi.createPaymentFailureReason(paymentProcessor.getId(),createUpdatePaymentFailureReason);


        ApiResponse<Void> response = paymentFailureReasonApi.deletePaymentFailureReasonWithHttpInfo(paymentProcessor.getId(),readPaymentFailureReason.getId());

        assertThat(response.getStatusCode(), equalTo(Response.Status.NO_CONTENT.getStatusCode()));
    }

    @Test
    public void updatePaymentFailureReasonTest() throws ApiException {
        CreateUpdatePaymentFailureReason createUpdatePaymentFailureReason = createPaymentFailureReason();
        ReadPaymentProcessor paymentProcessor = paymentProcessorApi.createPaymentProcessor(createPaymentProcessor());
        ReadPaymentFailureReason readPaymentFailureReason = paymentFailureReasonApi.createPaymentFailureReason(paymentProcessor.getId(),createUpdatePaymentFailureReason);


        CreateUpdatePaymentFailureReason updatePaymentFailureReason = new CreateUpdatePaymentFailureReason()
                .code(RandomStringUtils.randomAlphabetic(10).concat("code"))
                .name("new name")
                .description("new description");

        ApiResponse<ReadPaymentFailureReason> response = paymentFailureReasonApi.updatePaymentFailureReasonWithHttpInfo(paymentProcessor.getId(),readPaymentFailureReason.getId(), updatePaymentFailureReason);

        assertThat(response.getData(),
                allOf(
                        hasProperty("code", equalTo(updatePaymentFailureReason.getCode())),
                        hasProperty("name", equalTo(updatePaymentFailureReason.getName())),
                        hasProperty("description", notNullValue())));
        assertThat(response.getStatusCode(), equalTo(Response.Status.OK.getStatusCode()));
    }


    @Test
    public void deleteNonExistingReason() throws ApiException {
        ReadPaymentProcessor paymentProcessor = paymentProcessorApi.createPaymentProcessor(createPaymentProcessor());

        String nonExistingId = "123e4567-e89b-12d3-a456-426614174000";

        var ex = assertThrows(ApiException.class, () -> paymentFailureReasonApi.deletePaymentFailureReasonWithHttpInfo(paymentProcessor.getId(),nonExistingId));

        assertThat(ex.getCode(), equalTo(404));
        assertThat(ex.getMessage(), equalTo(String.format("Payment failure reason with id %s not found", nonExistingId)));
    }

    @Test
    public void updateNonExisting() throws ApiException {
        ReadPaymentProcessor paymentProcessor = paymentProcessorApi.createPaymentProcessor(createPaymentProcessor());

        String nonExistingId = "123e4567-e89b-12d3-a456-426614174000";

        CreateUpdatePaymentFailureReason createUpdatePaymentFailureReason = createPaymentFailureReason();

        var ex = assertThrows(ApiException.class, () -> paymentFailureReasonApi.updatePaymentFailureReasonWithHttpInfo(paymentProcessor.getId(),nonExistingId, createUpdatePaymentFailureReason));


        assertThat(ex.getCode(), equalTo(404));
        assertThat(ex.getMessage(), equalTo(String.format("Payment failure reason with id %s not found", nonExistingId)));
    }

    @Test
    public void updateExistingCode() throws ApiException {
        CreateUpdatePaymentFailureReason createUpdatePaymentFailureReason = createPaymentFailureReason();
        ReadPaymentProcessor paymentProcessor = paymentProcessorApi.createPaymentProcessor(createPaymentProcessor());
        ReadPaymentFailureReason readPaymentFailureReason = paymentFailureReasonApi.createPaymentFailureReason(paymentProcessor.getId(),createUpdatePaymentFailureReason);


        var ex = assertThrows(ApiException.class, () -> paymentFailureReasonApi.updatePaymentFailureReasonWithHttpInfo(paymentProcessor.getId(),readPaymentFailureReason.getId(), createUpdatePaymentFailureReason));

        assertThat(ex.getCode(), equalTo(409));
        assertThat(ex.getMessage(), equalTo(String.format("Code: %s already exists", readPaymentFailureReason.getCode())));
    }

    @Test
    public void createWithExistingCode() throws ApiException {
        CreateUpdatePaymentFailureReason createUpdatePaymentFailureReason = createPaymentFailureReason();
        ReadPaymentProcessor paymentProcessor = paymentProcessorApi.createPaymentProcessor(createPaymentProcessor());
        paymentFailureReasonApi.createPaymentFailureReason(paymentProcessor.getId(),createUpdatePaymentFailureReason);


        var ex = assertThrows(ApiException.class, () -> paymentFailureReasonApi.createPaymentFailureReasonWithHttpInfo(paymentProcessor.getId(),createUpdatePaymentFailureReason));

        assertThat(ex.getCode(), equalTo(409));
        assertThat(ex.getMessage(), equalTo(String.format("Code: %s already exists", createUpdatePaymentFailureReason.getCode())));
    }

    @Test
    public void getNonExistingPaymentFailureReason() throws ApiException {
        ReadPaymentProcessor paymentProcessor = paymentProcessorApi.createPaymentProcessor(createPaymentProcessor());

        String nonExistingId = "123e4567-e89b-12d3-a456-426614175000";

        var ex = assertThrows(ApiException.class, () -> paymentFailureReasonApi.getPaymentFailureReason(paymentProcessor.getId(),nonExistingId));

        assertThat(ex.getCode(), equalTo(404));
        assertThat(ex.getMessage(), equalTo(String.format("Payment failure reason with id %s not found", nonExistingId)));
    }

    CreateUpdatePaymentFailureReason createPaymentFailureReason() {
        return new CreateUpdatePaymentFailureReason()
                .code(RandomStringUtils.randomAlphabetic(10).concat("code"))
                .name("name: ".concat(RandomStringUtils.randomAlphabetic(10)))
                .description("description: ".concat(RandomStringUtils.randomAlphabetic(10)));
    }

    CreateUpdatePaymentProcessor createPaymentProcessor() {
        return new CreateUpdatePaymentProcessor()
                .key(RandomStringUtils.randomAlphabetic(10).concat("key"))
                .name("name: ".concat(RandomStringUtils.randomAlphabetic(10)))
                .description("description: ".concat(RandomStringUtils.randomAlphabetic(10)));
    }

}
