package person_department;

import com.jw.ApiException;
import com.jw.ApiResponse;
import com.jw.api.DepartmentApi;
import com.jw.api.PersonApi;
import com.jw.api.PersonDepartmentApi;
import com.jw.model.AddPersonToDepartment;
import com.jw.model.CreateUpdateDepartment;
import com.jw.model.CreateUpdatePerson;
import com.jw.model.ReadDepartment;
import com.jw.model.ReadPerson;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PersonDepartmentApiIT {
    private final PersonDepartmentApi personDepartmentApi = new PersonDepartmentApi();

    private final DepartmentApi departmentApi = new DepartmentApi();

    private final PersonApi personApi = new PersonApi();

    @Test
    public void setPersonForDepartmentTest() throws ApiException {
        CreateUpdateDepartment createUpdateDepartment = createDepartment();
        CreateUpdatePerson createUpdatePerson = createPerson();
        ReadDepartment readDepartment = departmentApi.createDepartment(createUpdateDepartment);
        ReadPerson readPerson = personApi.createPerson(createUpdatePerson);

        ApiResponse<ReadPerson> response = personDepartmentApi.setPersonForDepartmentWithHttpInfo(readDepartment.getId(), createPersonInDepartment(readPerson.getId()));

        assertThat(response.getStatusCode(), equalTo(Response.Status.CREATED.getStatusCode()));
        assertThat(response.getData(),
                allOf(
                        hasProperty("email", equalTo(readPerson.getEmail())),
                        hasProperty("name", equalTo(readPerson.getName())),
                        hasProperty("surname", notNullValue())));
    }

    @Test
    public void getPersonByDepartmentTest() throws ApiException {
        CreateUpdateDepartment createUpdateDepartment = createDepartment();
        ReadDepartment readDepartment = departmentApi.createDepartment(createUpdateDepartment);

        CreateUpdatePerson createUpdatePerson = createPerson();
        ReadPerson readPerson = personApi.createPerson(createUpdatePerson);

        personDepartmentApi.setPersonForDepartment(readDepartment.getId(), createPersonInDepartment(readPerson.getId()));

        ApiResponse<ReadPerson> response = personDepartmentApi.getPersonByDepartmentWithHttpInfo(readDepartment.getId(), readPerson.getId());


        assertThat(response.getData(),
                allOf(
                        hasProperty("email", is(readPerson.getEmail())),
                        hasProperty("name", is(readPerson.getName())),
                        hasProperty("surname", notNullValue())));
        assertThat(response.getStatusCode(), equalTo(Response.Status.OK.getStatusCode()));
    }

    @Test
    public void deleteTest() throws ApiException{
        CreateUpdateDepartment createUpdateDepartment = createDepartment();
        ReadDepartment readDepartment = departmentApi.createDepartment(createUpdateDepartment);

        CreateUpdatePerson createUpdatePerson = createPerson();
        ReadPerson readPerson = personApi.createPerson(createUpdatePerson);

        personDepartmentApi.setPersonForDepartment(readDepartment.getId(), createPersonInDepartment(readPerson.getId()));

        ApiResponse<Void> response = personDepartmentApi.deletePersonFromDepartmentWithHttpInfo(readDepartment.getId(), readPerson.getId());

        assertThat(response.getStatusCode(), equalTo(Response.Status.NO_CONTENT.getStatusCode()));
    }

    @Test
    public void getAllPersonsInDepartmentTest() throws ApiException {
        CreateUpdateDepartment createUpdateDepartment = createDepartment();
        ReadDepartment readDepartment = departmentApi.createDepartment(createUpdateDepartment);

        CreateUpdatePerson createUpdatePerson = createPerson();
        CreateUpdatePerson createUpdatePerson2 = createPerson();
        ReadPerson readPerson = personApi.createPerson(createUpdatePerson);
        ReadPerson readPerson1 = personApi.createPerson(createUpdatePerson2);
        personDepartmentApi.setPersonForDepartment(readDepartment.getId(), createPersonInDepartment(readPerson.getId()));
        personDepartmentApi.setPersonForDepartment(readDepartment.getId(), createPersonInDepartment(readPerson1.getId()));

        ApiResponse<List<ReadPerson>> response = personDepartmentApi.getAllPersonsByDepartmentWithHttpInfo(readDepartment.getId(), 1L, 10L);

        assertThat(response.getData().size(), greaterThan(1));
        assertThat(response.getStatusCode(), equalTo(Response.Status.OK.getStatusCode()));
    }

    @Test
    public void getPersonByNonExistingDepartment() throws ApiException {
        CreateUpdatePerson createUpdatePerson = createPerson();
        ReadPerson readPerson = personApi.createPerson(createUpdatePerson);

        Long nonExistingDepartmentId = 100000L;

        var ex = assertThrows(ApiException.class, () -> personDepartmentApi.getPersonByDepartmentWithHttpInfo(nonExistingDepartmentId,readPerson.getId()));


        assertThat(ex.getCode(), equalTo(404));
        assertThat(ex.getMessage(), equalTo(String.format("Department with id %d not found", nonExistingDepartmentId)));
    }

    @Test
    public void getNonExistingPersonByDepartment() throws ApiException {
        CreateUpdateDepartment createUpdateDepartment = createDepartment();
        ReadDepartment readDepartment = departmentApi.createDepartment(createUpdateDepartment);

        Long nonExistingPersonId = 100000L;

        var ex = assertThrows(ApiException.class, () -> personDepartmentApi.getPersonByDepartmentWithHttpInfo(readDepartment.getId(),nonExistingPersonId));


        assertThat(ex.getCode(), equalTo(404));
        assertThat(ex.getMessage(), equalTo(String.format("Person with id %d not found", nonExistingPersonId)));
    }

    @Test
    public void deleteNonExistingPersonFromDepartment() throws ApiException {
        CreateUpdateDepartment createUpdateDepartment = createDepartment();
        ReadDepartment readDepartment = departmentApi.createDepartment(createUpdateDepartment);

        Long nonExistingPersonId = 100000L;

        var ex = assertThrows(ApiException.class, () -> personDepartmentApi.deletePersonFromDepartmentWithHttpInfo(readDepartment.getId(),nonExistingPersonId));


        assertThat(ex.getCode(), equalTo(404));
        assertThat(ex.getMessage(), equalTo(String.format("Person with id %d not found", nonExistingPersonId)));
    }

    @Test
    public void addPersonToNonExistingDepartment() throws ApiException {
        CreateUpdatePerson createUpdatePerson = createPerson();
        ReadPerson readPerson = personApi.createPerson(createUpdatePerson);

        Long nonExistingDepartmentId = 100000L;

        var ex = assertThrows(ApiException.class, () -> personDepartmentApi.setPersonForDepartment(nonExistingDepartmentId,createPersonInDepartment(readPerson.getId())));


        assertThat(ex.getCode(), equalTo(404));
        assertThat(ex.getMessage(), equalTo(String.format("Department with id %d not found", nonExistingDepartmentId)));
    }

    @Test
    public void addNonExistingPersonToDepartment() throws ApiException{
        CreateUpdateDepartment createUpdateDepartment = createDepartment();
        ReadDepartment readDepartment = departmentApi.createDepartment(createUpdateDepartment);

        Long nonExistingPersonId = 100000L;

        var ex = assertThrows(ApiException.class, () -> personDepartmentApi.setPersonForDepartment(readDepartment.getId(),createPersonInDepartment(nonExistingPersonId)));


        assertThat(ex.getCode(), equalTo(404));
        assertThat(ex.getMessage(), equalTo(String.format("Person with id %d not found", nonExistingPersonId)));
    }

    private static AddPersonToDepartment  createPersonInDepartment(Long id) throws ApiException {

        AddPersonToDepartment addPersonToDepartment = new AddPersonToDepartment();
        addPersonToDepartment.setPersonId(id);

        return addPersonToDepartment;
    }

    private static CreateUpdateDepartment createDepartment() {
        return new CreateUpdateDepartment()
                .name(RandomStringUtils.randomAlphabetic(10))
                .location(RandomStringUtils.randomAlphabetic(12));
    }

    private static CreateUpdatePerson createPerson() {
        return new CreateUpdatePerson()
                .email(RandomStringUtils.randomAlphabetic(10).concat("@mail.com"))
                .name("asda")
                .surname("dasdasd")
                .password("321323213211");
    }
}
