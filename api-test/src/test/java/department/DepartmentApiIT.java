package department;

import com.jw.ApiException;
import com.jw.ApiResponse;
import com.jw.api.DepartmentApi;
import com.jw.model.CreateUpdateDepartment;
import com.jw.model.ReadDepartment;
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
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DepartmentApiIT {
    private final DepartmentApi departmentApi = new DepartmentApi();

    @Test
    public void createTest() throws ApiException {
        CreateUpdateDepartment createUpdateDepartment = createDepartment();

        ApiResponse<ReadDepartment> response = departmentApi.createDepartmentWithHttpInfo(createUpdateDepartment);

        assertThat(response.getData(),
                allOf(
                        hasProperty("location", notNullValue()),
                        hasProperty("name", notNullValue())));

        assertThat(response.getStatusCode(), equalTo(Response.Status.CREATED.getStatusCode()));
    }

    @Test
    public void getTest() throws ApiException {
        CreateUpdateDepartment createUpdateDepartment = createDepartment();

        ReadDepartment readDepartment = departmentApi.createDepartment(createUpdateDepartment);

        ApiResponse<ReadDepartment> response = departmentApi.getDepartmentWithHttpInfo(readDepartment.getId());

        assertThat(response.getData(),
                allOf(
                        hasProperty("location", notNullValue()),
                        hasProperty("name", notNullValue())));

        assertThat(response.getStatusCode(), equalTo(Response.Status.OK.getStatusCode()));
    }

    @Test
    public void getAllTest() throws ApiException {
        CreateUpdateDepartment createUpdateDepartment = createDepartment();
        CreateUpdateDepartment createUpdateDepartment1 = createDepartment();

        departmentApi.createDepartment(createUpdateDepartment1);
        departmentApi.createDepartment(createUpdateDepartment);

        ApiResponse<List<ReadDepartment>> response = departmentApi.getDepartmentsWithHttpInfo(1L, 10L);

        assertThat(response.getData().size(), greaterThan(1));
        assertThat(response.getStatusCode(), equalTo(Response.Status.OK.getStatusCode()));
    }

    @Test
    public void deleteTest() throws ApiException {
        CreateUpdateDepartment createUpdateDepartment = createDepartment();

        ReadDepartment readDepartment = departmentApi.createDepartment(createUpdateDepartment);

        ApiResponse<Void> response = departmentApi.deleteDepartmentWithHttpInfo(readDepartment.getId());

        assertThat(response.getStatusCode(), equalTo(Response.Status.NO_CONTENT.getStatusCode()));
    }

    @Test
    public void updateTest() throws ApiException {
        CreateUpdateDepartment createUpdateDepartment = createDepartment();
        ReadDepartment readDepartment = departmentApi.createDepartment(createUpdateDepartment);

        CreateUpdateDepartment updateDepartment = new CreateUpdateDepartment()
                .name("newnameaaa")
                .location("newlocation");

        ApiResponse<ReadDepartment> response = departmentApi.updateDepartmentWithHttpInfo(readDepartment.getId(), updateDepartment);

        assertThat(response.getData(),
                allOf(
                        hasProperty("location", notNullValue()),
                        hasProperty("name", notNullValue())));

        assertThat(response.getStatusCode(), equalTo(Response.Status.OK.getStatusCode()));
    }

    @Test
    public void deleteNonExistingDepartment(){
        Long nonExistingId = 10000000L;

        var ex = assertThrows(ApiException.class, () -> departmentApi.deleteDepartmentWithHttpInfo(nonExistingId));

        assertThat(ex.getCode(), equalTo(404));
        assertThat(ex.getMessage(), equalTo(String.format("Department with id %d not found", nonExistingId)));
    }

    @Test
    public void updateNonExistingDepartment() {
        Long nonExistingId = 10000000L;

        CreateUpdateDepartment createUpdateDepartment = createDepartment();

        var ex = assertThrows(ApiException.class, () -> departmentApi.updateDepartmentWithHttpInfo(nonExistingId,createUpdateDepartment));

        assertThat(ex.getCode(), equalTo(404));
        assertThat(ex.getMessage(), equalTo(String.format("Department with id %d not found", nonExistingId)));
    }

    @Test
    public void getNonExistingDepartment() {
        Long nonExistingId = 10000000L;


        var ex = assertThrows(ApiException.class, () -> departmentApi.getDepartmentWithHttpInfo(nonExistingId));


        assertThat(ex.getCode(), equalTo(404));
        assertThat(ex.getMessage(), equalTo(String.format("Department with id %d not found", nonExistingId)));
    }

    private static CreateUpdateDepartment createDepartment() {
        return new CreateUpdateDepartment()
                .name(RandomStringUtils.randomAlphabetic(10))
                .location(RandomStringUtils.randomAlphabetic(12));
    }
}
