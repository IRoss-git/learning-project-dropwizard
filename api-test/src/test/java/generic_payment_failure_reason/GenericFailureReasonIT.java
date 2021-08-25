package generic_payment_failure_reason;

import com.jw.ApiException;
import com.jw.ApiResponse;
import com.jw.api.GenericReasonApi;
import com.jw.model.CreateUpdateGenericPaymentFailureReason;
import com.jw.model.CreateUpdatePaymentProcessor;
import com.jw.model.ReadGenericPaymentFailureReason;
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

public class GenericFailureReasonIT {
    private GenericReasonApi genericReasonApi = new GenericReasonApi();

    @Test
    public void createPaymentProcessorTest() throws ApiException {
        CreateUpdateGenericPaymentFailureReason createUpdateGenericPaymentFailureReason = createUpdateGenericPaymentFailureReason();

        ApiResponse<ReadGenericPaymentFailureReason> response = genericReasonApi.createPaymentGenericFailureReasonWithHttpInfo(createUpdateGenericPaymentFailureReason);

        assertThat(response.getData(),
                allOf(
                        hasProperty("code", equalTo(createUpdateGenericPaymentFailureReason.getCode())),
                        hasProperty("name", equalTo(createUpdateGenericPaymentFailureReason.getName())),
                        hasProperty("description", notNullValue())));
        assertEquals(Response.Status.CREATED.getStatusCode(),
                response.getStatusCode());
    }

    @Test
    public void getPaymentProcessorTest() throws ApiException {
        CreateUpdateGenericPaymentFailureReason createUpdateGenericPaymentFailureReason = createUpdateGenericPaymentFailureReason();

        ReadGenericPaymentFailureReason readGenericPaymentFailureReason = genericReasonApi.createPaymentGenericFailureReason(createUpdateGenericPaymentFailureReason);

        ApiResponse<ReadGenericPaymentFailureReason> response = genericReasonApi.getPaymentGenericFailureReasonWithHttpInfo(readGenericPaymentFailureReason.getId());

        assertThat(response.getData(),
                allOf(
                        hasProperty("code", equalTo(createUpdateGenericPaymentFailureReason.getCode())),
                        hasProperty("name", equalTo(createUpdateGenericPaymentFailureReason.getName())),
                        hasProperty("description", notNullValue())));
        assertThat(response.getStatusCode(), equalTo(Response.Status.OK.getStatusCode()));
    }

    @Test
    public void getAllPaymentProcessorsTest() throws ApiException {
        CreateUpdateGenericPaymentFailureReason createUpdateGenericPaymentFailureReason = createUpdateGenericPaymentFailureReason();
        CreateUpdateGenericPaymentFailureReason createUpdateGenericPaymentFailureReason2 = createUpdateGenericPaymentFailureReason();

        genericReasonApi.createPaymentGenericFailureReason(createUpdateGenericPaymentFailureReason2);
        genericReasonApi.createPaymentGenericFailureReason(createUpdateGenericPaymentFailureReason);

        ApiResponse<List<ReadGenericPaymentFailureReason>> response = genericReasonApi.getPaymentGenericFailureReasonsWithHttpInfo(1L, 10L);

        assertThat(response.getData().size(), greaterThan(1));
        assertThat(response.getStatusCode(), equalTo(Response.Status.OK.getStatusCode()));
    }

    @Test
    public void deletePaymentProcessorTest() throws ApiException {
        CreateUpdateGenericPaymentFailureReason createUpdateGenericPaymentFailureReason = createUpdateGenericPaymentFailureReason();

        ReadGenericPaymentFailureReason readGenericPaymentFailureReason = genericReasonApi.createPaymentGenericFailureReason(createUpdateGenericPaymentFailureReason);
        ApiResponse<Void> response = genericReasonApi.deletePaymentGenericFailureReasonWithHttpInfo(readGenericPaymentFailureReason.getId());

        assertThat(response.getStatusCode(), equalTo(Response.Status.NO_CONTENT.getStatusCode()));
    }

    @Test
    public void updateTest() throws ApiException {
        CreateUpdateGenericPaymentFailureReason createUpdateGenericPaymentFailureReason = createUpdateGenericPaymentFailureReason();

        ReadGenericPaymentFailureReason readGenericPaymentFailureReason = genericReasonApi.createPaymentGenericFailureReason(createUpdateGenericPaymentFailureReason);

        CreateUpdateGenericPaymentFailureReason updateGenericPaymentFailureReason = new CreateUpdateGenericPaymentFailureReason()
                .code(RandomStringUtils.randomAlphabetic(10).concat("code"))
                .name("new name")
                .description("new description");

        ApiResponse<ReadGenericPaymentFailureReason> response = genericReasonApi.updatePaymentGenericFailureReasonWithHttpInfo(readGenericPaymentFailureReason.getId(), updateGenericPaymentFailureReason);

        assertThat(response.getData(),
                allOf(
                        hasProperty("code", equalTo(updateGenericPaymentFailureReason.getCode())),
                        hasProperty("name", equalTo(updateGenericPaymentFailureReason.getName())),
                        hasProperty("description", notNullValue())));
        assertThat(response.getStatusCode(), equalTo(Response.Status.OK.getStatusCode()));
    }


    @Test
    public void deleteNonExisting() {
        String nonExistingId = "123e4567-e89b-12d3-a456-426614174001";

        var ex = assertThrows(ApiException.class, () -> genericReasonApi.deletePaymentGenericFailureReasonWithHttpInfo(nonExistingId));

        assertThat(ex.getCode(), equalTo(404));
        assertThat(ex.getMessage(), equalTo(String.format("Generic payment reason with id %s not found", nonExistingId)));
    }

    @Test
    public void updateNonExisting() {
        String nonExistingId = "123e4567-e89b-12d3-a456-426614174001";

        CreateUpdateGenericPaymentFailureReason createUpdateGenericPaymentFailureReason = createUpdateGenericPaymentFailureReason();

        var ex = assertThrows(ApiException.class, () -> genericReasonApi.updatePaymentGenericFailureReasonWithHttpInfo(nonExistingId, createUpdateGenericPaymentFailureReason));


        assertThat(ex.getCode(), equalTo(404));
        assertThat(ex.getMessage(), equalTo(String.format("Generic payment reason with id %s not found", nonExistingId)));
    }

    @Test
    public void getNonExistingPerson() {
        String nonExistingId = "123e4567-e89b-12d3-a456-426614174020";

        var ex = assertThrows(ApiException.class, () -> genericReasonApi.getPaymentGenericFailureReasonWithHttpInfo(nonExistingId));

        assertThat(ex.getCode(), equalTo(404));
        assertThat(ex.getMessage(), equalTo(String.format("Generic payment reason with id %s not found", nonExistingId)));
    }

    CreateUpdateGenericPaymentFailureReason createUpdateGenericPaymentFailureReason() {
        return new CreateUpdateGenericPaymentFailureReason()
                .code(RandomStringUtils.randomAlphabetic(10).concat("code"))
                .name("name: ".concat(RandomStringUtils.randomAlphabetic(10)))
                .description("description: ".concat(RandomStringUtils.randomAlphabetic(10)));
    }

}
