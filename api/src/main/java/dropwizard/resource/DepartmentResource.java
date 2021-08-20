package dropwizard.resource;

import com.learn.dropwizard.api.DepartmentsApi;
import com.learn.dropwizard.model.CreateUpdateDepartmentDTO;
import com.learn.dropwizard.model.ReadDepartmentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ilya.service.service.DepartmentService;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Component
@Produces(MediaType.APPLICATION_JSON)
public class DepartmentResource implements DepartmentsApi {

    @Autowired
    private DepartmentService departmentService;

    @Override
    public Response createDepartment(CreateUpdateDepartmentDTO createUpdateDepartmentDTO) {
        ReadDepartmentDTO readDepartmentDTO = departmentService.createDepartment(createUpdateDepartmentDTO);

        return Response.status(Response.Status.CREATED).entity(readDepartmentDTO).build();
    }

    @Override
    public Response deleteDepartment(Long id) {
        departmentService.deleteDepartment(id);

        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @Override
    public Response getDepartment(Long id) {
        ReadDepartmentDTO departmentDTO = departmentService.getDepartment(id);

        return Response.status(Response.Status.OK).entity(departmentDTO).build();
    }


    @Override
    public Response getDepartments(Long pageNumber, Long pageSize) {

        List <ReadDepartmentDTO> departmentDTOS=  departmentService.getAllDepartments(pageNumber,pageSize);

        return Response.status(Response.Status.OK).entity(departmentDTOS).build();
    }

    @Override
    public Response updateDepartment(Long id, CreateUpdateDepartmentDTO createUpdateDepartmentDTO) {
        ReadDepartmentDTO readDepartmentDTO = departmentService.updateDepartment(id, createUpdateDepartmentDTO);

        return Response.status(Response.Status.OK).entity(readDepartmentDTO).build();
    }


}
