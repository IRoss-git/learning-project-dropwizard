package dropwizard.resource;

import com.ilya.service.service.GenericPaymentFailureReasonService;
import com.ilya.service.service.PaymentFailureReasonService;
import com.learn.dropwizard.model.CreateUpdateGenericPaymentFailureReasonDTO;
import com.learn.dropwizard.api.PaymentGenericFailureReasonApi;
import com.learn.dropwizard.model.ReadGenericPaymentFailureReasonDTO;
import com.learn.dropwizard.model.ReadPaymentFailureReasonDTO;
import com.learn.dropwizard.model.RefPaymentFailureReasonDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import java.util.List;

@Component
public class GenericPaymentFailureReasonResource implements PaymentGenericFailureReasonApi {

    @Autowired
    private GenericPaymentFailureReasonService genericPaymentFailureReasonService;

    @Autowired
    private PaymentFailureReasonService paymentFailureReasonService;

    @Override
    public Response createPaymentGenericFailureReason(CreateUpdateGenericPaymentFailureReasonDTO createUpdateGenericPaymentFailureReasonDTO) {
        ReadGenericPaymentFailureReasonDTO readGenericPaymentFailureReasonDTO = genericPaymentFailureReasonService.createGenericPaymentFailureReason(createUpdateGenericPaymentFailureReasonDTO);

        return Response.status(Response.Status.CREATED).entity(readGenericPaymentFailureReasonDTO).build();
    }

    @Override
    public Response deleteMappingPaymentGenericFailureReason(String paymentGenericFailureReasonId, String id) {
        paymentFailureReasonService.deleteMapping(paymentGenericFailureReasonId, id);

        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @Override
    public Response deletePaymentGenericFailureReason(String id) {
        genericPaymentFailureReasonService.deleteGenericPaymentFailureReason(id);

        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @Override
    public Response getAllPaymentFailureReasonByGenericReason(String paymentGenericFailureReasonId, Long pageNumber, Long pageSize) {
        List<ReadPaymentFailureReasonDTO> readPaymentFailureReasonDTOS = paymentFailureReasonService.getAllPaymentFailureReasonsByGenericReason(paymentGenericFailureReasonId, pageNumber, pageSize);

        return Response.status(Response.Status.OK).entity(readPaymentFailureReasonDTOS).build();
    }

    @Override
    public Response getPaymentGenericFailureReason(String id) {
        ReadGenericPaymentFailureReasonDTO readGenericPaymentFailureReasonDTO = genericPaymentFailureReasonService.getGenericPaymentFailureReason(id);

        return Response.status(Response.Status.OK).entity(readGenericPaymentFailureReasonDTO).build();
    }

    @Override
    public Response getPaymentGenericFailureReasons(Long pageNumber, Long pageSize) {
        List<ReadGenericPaymentFailureReasonDTO> readGenericPaymentFailureReasonDTOList = genericPaymentFailureReasonService.getAllGenericPaymentFailureReasons(pageNumber, pageSize);

        return Response.status(Response.Status.OK).entity(readGenericPaymentFailureReasonDTOList).build();
    }

    @Override
    public Response setRefPaymentFailureReason(String paymentGenericFailureReasonId, List<RefPaymentFailureReasonDTO> refPaymentFailureReasonDTO) {

        paymentFailureReasonService.addFailureReasonToGenericGroup(paymentGenericFailureReasonId, refPaymentFailureReasonDTO);

        return Response.status(Response.Status.OK).build();
    }

    @Override
    public Response updatePaymentGenericFailureReason(String id, CreateUpdateGenericPaymentFailureReasonDTO createUpdateGenericPaymentFailureReasonDTO) {
        ReadGenericPaymentFailureReasonDTO readGenericPaymentFailureReasonDTO = genericPaymentFailureReasonService.updateGenericPaymentFailureReason(id, createUpdateGenericPaymentFailureReasonDTO);
        return Response.status(Response.Status.OK).entity(readGenericPaymentFailureReasonDTO).build();
    }
}
