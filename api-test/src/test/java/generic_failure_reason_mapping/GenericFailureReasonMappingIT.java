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
import util.ApiClientUtil;

import javax.ws.rs.core.Response;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class GenericFailureReasonMappingIT {

    private final PaymentFailureReasonMappingWithGenericApi paymentFailureReasonMappingWithGenericApi = new PaymentFailureReasonMappingWithGenericApi(ApiClientUtil.getApiClient());

    private final GenericReasonApi genericReasonApi = new GenericReasonApi(ApiClientUtil.getApiClient());

    private final PaymentFailureReasonApi paymentFailureReasonApi = new PaymentFailureReasonApi(ApiClientUtil.getApiClient());

    private final PaymentProcessorApi paymentProcessorApi = new PaymentProcessorApi(ApiClientUtil.getApiClient());

    @Test
    public void createReasonMapping() throws ApiException {
        ReadGenericPaymentFailureReason readGenericPaymentFailureReason = genericReasonApi.createPaymentGenericFailureReason(createUpdateGenericPaymentFailureReason());

        ReadPaymentProcessor paymentProcessor = paymentProcessorApi.createPaymentProcessor(createPaymentProcessor());
        ReadPaymentFailureReason readPaymentFailureReason = paymentFailureReasonApi.createPaymentFailureReason(paymentProcessor.getId(),createPaymentFailureReason());

        RefPaymentFailureReason refPaymentFailureReason = new RefPaymentFailureReason();
        refPaymentFailureReason.setReasonId(readPaymentFailureReason.getId());

        ApiResponse<ReadPaymentFailureReason> response = paymentFailureReasonMappingWithGenericApi.setRefPaymentFailureReasonWithHttpInfo(readGenericPaymentFailureReason.getId(), List.of(refPaymentFailureReason));


        assertThat(response.getStatusCode(), equalTo(Response.Status.OK.getStatusCode()));
    }

    @Test
    public void getMappingTest() throws ApiException {
        ReadGenericPaymentFailureReason readGenericPaymentFailureReason = genericReasonApi.createPaymentGenericFailureReason(createUpdateGenericPaymentFailureReason());

        ReadPaymentProcessor paymentProcessor = paymentProcessorApi.createPaymentProcessor(createPaymentProcessor());
        ReadPaymentFailureReason readPaymentFailureReason = paymentFailureReasonApi.createPaymentFailureReason(paymentProcessor.getId(),createPaymentFailureReason());

        RefPaymentFailureReason refPaymentFailureReason = new RefPaymentFailureReason();
        refPaymentFailureReason.setReasonId(readPaymentFailureReason.getId());
        paymentFailureReasonMappingWithGenericApi.setRefPaymentFailureReason(readGenericPaymentFailureReason.getId(),List.of(refPaymentFailureReason));

        ApiResponse<List<ReadPaymentFailureReason>> response = paymentFailureReasonMappingWithGenericApi.getAllPaymentFailureReasonByGenericReasonWithHttpInfo(readGenericPaymentFailureReason.getId(), 1L, 10L);


        assertThat(response.getData().size(), equalTo(1));
        assertThat(response.getStatusCode(), equalTo(Response.Status.OK.getStatusCode()));
    }

    @Test
    public void deleteMappingTest() throws ApiException {
        ReadGenericPaymentFailureReason readGenericPaymentFailureReason = genericReasonApi.createPaymentGenericFailureReason(createUpdateGenericPaymentFailureReason());

        ReadPaymentProcessor paymentProcessor = paymentProcessorApi.createPaymentProcessor(createPaymentProcessor());
        ReadPaymentFailureReason readPaymentFailureReason = paymentFailureReasonApi.createPaymentFailureReason(paymentProcessor.getId(),createPaymentFailureReason());

        RefPaymentFailureReason refPaymentFailureReason = new RefPaymentFailureReason();
        refPaymentFailureReason.setReasonId(readPaymentFailureReason.getId());
        paymentFailureReasonMappingWithGenericApi.setRefPaymentFailureReason(readGenericPaymentFailureReason.getId(),List.of(refPaymentFailureReason));

        ApiResponse<Void> response = paymentFailureReasonMappingWithGenericApi.deleteMappingPaymentGenericFailureReasonWithHttpInfo(readGenericPaymentFailureReason.getId(),refPaymentFailureReason.getReasonId());

        assertThat(response.getStatusCode(), equalTo(Response.Status.NO_CONTENT.getStatusCode()));
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
