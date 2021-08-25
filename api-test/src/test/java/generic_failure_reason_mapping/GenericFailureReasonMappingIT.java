package generic_failure_reason_mapping;

import com.jw.ApiException;
import com.jw.ApiResponse;
import com.jw.api.GenericReasonApi;
import com.jw.api.PaymentFailureReasonApi;
import com.jw.api.PaymentFailureReasonMappingWithGenericApi;
import com.jw.api.PaymentProcessorApi;
import com.jw.model.CreateUpdateGenericPaymentFailureReason;
import com.jw.model.CreateUpdatePaymentFailureReason;
import com.jw.model.CreateUpdatePaymentProcessor;
import com.jw.model.ReadGenericPaymentFailureReason;
import com.jw.model.ReadPaymentFailureReason;
import com.jw.model.ReadPaymentProcessor;
import com.jw.model.RefPaymentFailureReason;
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

public class GenericFailureReasonMappingIT {

    private final PaymentFailureReasonMappingWithGenericApi paymentFailureReasonMappingWithGenericApi = new PaymentFailureReasonMappingWithGenericApi();

    private final GenericReasonApi genericReasonApi = new GenericReasonApi();

    private final PaymentFailureReasonApi paymentFailureReasonApi = new PaymentFailureReasonApi();

    private final PaymentProcessorApi paymentProcessorApi = new PaymentProcessorApi();

    @Test
    public void createReasonMapping() throws ApiException {
        ReadGenericPaymentFailureReason readGenericPaymentFailureReason = genericReasonApi.createPaymentGenericFailureReason(createUpdateGenericPaymentFailureReason());

        ReadPaymentProcessor paymentProcessor = paymentProcessorApi.createPaymentProcessor(createPaymentProcessor());
        ReadPaymentFailureReason readPaymentFailureReason = paymentFailureReasonApi.createPaymentFailureReason(paymentProcessor.getId(),createPaymentFailureReason());

        RefPaymentFailureReason refPaymentFailureReason = new RefPaymentFailureReason();
        refPaymentFailureReason.setReasonId(readPaymentFailureReason.getId());

        ApiResponse<ReadPaymentFailureReason> response = paymentFailureReasonMappingWithGenericApi.setRefPaymentFailureReasonWithHttpInfo(readGenericPaymentFailureReason.getId(), refPaymentFailureReason);


        assertThat(response.getStatusCode(), equalTo(Response.Status.OK.getStatusCode()));
        assertThat(response.getData(),
                allOf(
                        hasProperty("code", notNullValue()),
                        hasProperty("name",notNullValue()),
                        hasProperty("description", notNullValue())));
    }

    @Test
    public void getFailureReasonByGenericTest() throws ApiException {
        ReadGenericPaymentFailureReason readGenericPaymentFailureReason = genericReasonApi.createPaymentGenericFailureReason(createUpdateGenericPaymentFailureReason());

        ReadPaymentProcessor paymentProcessor = paymentProcessorApi.createPaymentProcessor(createPaymentProcessor());
        ReadPaymentFailureReason readPaymentFailureReason = paymentFailureReasonApi.createPaymentFailureReason(paymentProcessor.getId(),createPaymentFailureReason());

        RefPaymentFailureReason refPaymentFailureReason = new RefPaymentFailureReason();
        refPaymentFailureReason.setReasonId(readPaymentFailureReason.getId());
        paymentFailureReasonMappingWithGenericApi.setRefPaymentFailureReason(readGenericPaymentFailureReason.getId(),refPaymentFailureReason);

        ApiResponse<List<ReadPaymentFailureReason>> response = paymentFailureReasonMappingWithGenericApi.getAllPaymentFailureReasonByGenericReasonWithHttpInfo(readGenericPaymentFailureReason.getId(), 1L, 10L);


        assertThat(response.getData().size(), equalTo(1));
        assertThat(response.getStatusCode(), equalTo(Response.Status.OK.getStatusCode()));
    }

    CreateUpdateGenericPaymentFailureReason createUpdateGenericPaymentFailureReason() {
        return new CreateUpdateGenericPaymentFailureReason()
                .code(RandomStringUtils.randomAlphabetic(10).concat("code"))
                .name("name: ".concat(RandomStringUtils.randomAlphabetic(10)))
                .description("description: ".concat(RandomStringUtils.randomAlphabetic(10)));
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
