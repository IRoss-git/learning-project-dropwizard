package dropwizard.resource;

import com.ilya.service.service.PaymentFailureReasonService;
import com.learn.dropwizard.model.CreateUpdatePaymentFailureReasonDTO;
import com.learn.dropwizard.model.ReadPaymentFailureReasonDTO;
import com.learn.dropwizard.api.PaymentProcessorApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import java.util.List;

@Component
public class PaymentFailureReasonResource implements PaymentProcessorApi {

    @Autowired
    private PaymentFailureReasonService paymentFailureReasonService;


    @Override
    public Response createPaymentFailureReason(String paymentProcessorId, CreateUpdatePaymentFailureReasonDTO createUpdatePaymentFailureReasonDTO) {
        ReadPaymentFailureReasonDTO readPaymentFailureReasonDTO = paymentFailureReasonService.createPaymentFailureReason(paymentProcessorId, createUpdatePaymentFailureReasonDTO);

        return Response.status(Response.Status.CREATED).entity(readPaymentFailureReasonDTO).build();
    }

    @Override
    public Response deletePaymentFailureReason(String paymentProcessorId, String id) {
        paymentFailureReasonService.deletePaymentFailureReason(paymentProcessorId, id);

        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @Override
    public Response getAllPaymentFailureReasonByPaymentProcessor(String paymentProcessorId, Long pageNumber, Long pageSize) {
        List <ReadPaymentFailureReasonDTO> readPaymentFailureReasonDTOS = paymentFailureReasonService.getAllPaymentFailureReasonsByProcessor(paymentProcessorId, pageNumber, pageSize);

        return Response.status(Response.Status.OK).entity(readPaymentFailureReasonDTOS).build();
    }

    @Override
    public Response getPaymentFailureReason(String paymentProcessorId, String id) {
        ReadPaymentFailureReasonDTO readPaymentFailureReasonDTO = paymentFailureReasonService.getPaymentFailureReason(paymentProcessorId, id);

        return Response.status(Response.Status.OK).entity(readPaymentFailureReasonDTO).build();
    }

    @Override
    public Response updatePaymentFailureReason(String paymentProcessorId, String id, CreateUpdatePaymentFailureReasonDTO createUpdatePaymentFailureReasonDTO) {
        ReadPaymentFailureReasonDTO readPaymentFailureReasonDTO = paymentFailureReasonService.updatePaymentFailureReason(paymentProcessorId,id,createUpdatePaymentFailureReasonDTO);

        return Response.status(Response.Status.OK).entity(readPaymentFailureReasonDTO).build();
    }
}
