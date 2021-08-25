package dropwizard.resource;

import com.ilya.service.service.PaymentProcessorService;
import com.learn.dropwizard.model.CreateUpdatePaymentProcessorDTO;
import com.learn.dropwizard.model.ReadPaymentProcessorDTO;

import javax.ws.rs.core.Response;
import  com.learn.dropwizard.api.PaymentProcessorsApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class PaymentProcessorResource implements PaymentProcessorsApi {

    @Autowired
    private PaymentProcessorService paymentProcessorService;

    @Override
    public Response createPaymentProcessor(CreateUpdatePaymentProcessorDTO createUpdatePaymentProcessorDTO) {
        ReadPaymentProcessorDTO entity = paymentProcessorService.createPaymentProcessor(createUpdatePaymentProcessorDTO);

        return Response.status(Response.Status.CREATED).entity(entity).build();
    }

    @Override
    public Response deletePaymentProcessor(String id) {
        paymentProcessorService.deletePaymentProcessor(id);

        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @Override
    public Response getPaymentProcessor(String id) {
        ReadPaymentProcessorDTO paymentProcessorDTO = paymentProcessorService.getPaymentProcessor(id);

        return Response.status(Response.Status.OK).entity(paymentProcessorDTO).build();
    }

    @Override
    public Response getPaymentProcessors(Long pageNumber, Long pageSize) {

        List <ReadPaymentProcessorDTO> paymentProcessorDTOList = paymentProcessorService.getAllPaymentProcessors(pageNumber,pageSize);

        return Response.status(Response.Status.OK).entity(paymentProcessorDTOList).build();
    }

    @Override
    public Response updatePaymentProcessor(String id, CreateUpdatePaymentProcessorDTO createUpdatePaymentProcessorDTO) {
        ReadPaymentProcessorDTO readPaymentProcessorDTO = paymentProcessorService.updatePaymentProcessor(id,createUpdatePaymentProcessorDTO);

        return Response.status(Response.Status.OK).entity(readPaymentProcessorDTO).build();
    }
}
