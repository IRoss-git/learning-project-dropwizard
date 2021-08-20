package person;

import com.jw.ApiException;
import com.jw.ApiResponse;
import com.jw.api.PersonApi;
import com.jw.model.CreateUpdatePerson;
import com.jw.model.ReadPerson;
import com.jw.model.ReadPersons;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PersonApiIT {

    private PersonApi personApi = new PersonApi();

    @Test
    public void createPersonTest() throws ApiException {
        CreateUpdatePerson createPerson = createPerson();

        ApiResponse<ReadPerson> response = personApi.createPersonWithHttpInfo(createPerson);

        assertEquals(Response.Status.CREATED.getStatusCode(),
                response.getStatusCode());
        assertPerson(createPerson, response.getData());
    }

    @Test
    public void getPersonTest() throws ApiException {
        CreateUpdatePerson createPerson = createPerson();

        ReadPerson readPerson = personApi.createPerson(createPerson);

        ApiResponse<ReadPerson> response = personApi.getPersonWithHttpInfo(readPerson.getId());

        assertThat(response.getData(),
                allOf(
                        hasProperty("email", equalTo(readPerson.getEmail())),
                        hasProperty("name", equalTo(readPerson.getName())),
                        hasProperty("surname", notNullValue())));
        assertThat(response.getStatusCode(), equalTo(Response.Status.OK.getStatusCode()));
    }

    @Test
    public void getAllPersonsTest() throws ApiException {
        CreateUpdatePerson createUpdatePerson = createPerson();
        CreateUpdatePerson createUpdatePerson2 = createPerson();
        personApi.createPerson(createUpdatePerson);
        personApi.createPerson(createUpdatePerson2);

        ApiResponse<List<ReadPersons>> response = personApi.getPersonsWithHttpInfo(1L, 10L);

        assertThat(response.getData().size(), greaterThan(1));
        assertThat(response.getStatusCode(), equalTo(Response.Status.OK.getStatusCode()));
    }

    @Test
    public void deleteTest() throws ApiException {
        CreateUpdatePerson createUpdatePerson = createPerson();

        ReadPerson readPerson = personApi.createPerson(createUpdatePerson);

        ApiResponse<Void> response = personApi.deletePersonWithHttpInfo(readPerson.getId());

        assertThat(response.getStatusCode(), equalTo(Response.Status.NO_CONTENT.getStatusCode()));
    }

    @Test
    public void updateTest() throws ApiException {
        CreateUpdatePerson createUpdatePerson = createPerson();

        ReadPerson readPerson = personApi.createPerson(createUpdatePerson);

        CreateUpdatePerson updatePerson = new CreateUpdatePerson()
                .email(RandomStringUtils.randomAlphabetic(10).concat("@mail.com"))
                .password("newpassword")
                .surname("surnamenew")
                .name("newname");

        ApiResponse<ReadPerson> response = personApi.updatePersonWithHttpInfo(readPerson.getId(), updatePerson);

        assertThat(response.getData(),
                allOf(
                        hasProperty("email", equalTo(updatePerson.getEmail())),
                        hasProperty("name", equalTo(updatePerson.getName())),
                        hasProperty("surname", notNullValue())));
        assertThat(response.getStatusCode(), equalTo(Response.Status.OK.getStatusCode()));
    }


    @Test
    public void deleteNonExisting() {
        Long nonExistingId = 10000000L;

        var ex = assertThrows(ApiException.class, () -> personApi.deletePersonWithHttpInfo(nonExistingId));

        assertThat(ex.getCode(), equalTo(404));
        assertThat(ex.getMessage(), equalTo(String.format("Person with id %d not found", nonExistingId)));
    }

    @Test
    public void updateNonExisting() {
        Long nonExistingId = 10000000L;

        CreateUpdatePerson createUpdatePerson = createPerson();

        var ex = assertThrows(ApiException.class, () -> personApi.updatePersonWithHttpInfo(nonExistingId,createUpdatePerson));


        assertThat(ex.getCode(), equalTo(404));
        assertThat(ex.getMessage(), equalTo(String.format("Person with id %d not found", nonExistingId)));
    }

    @Test
    public void updateExistingEmail() throws ApiException {
        CreateUpdatePerson createUpdatePerson = createPerson();
        ReadPerson readPerson = personApi.createPerson(createUpdatePerson);


        var ex = assertThrows(ApiException.class, () -> personApi.updatePersonWithHttpInfo(readPerson.getId(),createUpdatePerson));


        assertThat(ex.getCode(), equalTo(409));
        assertThat(ex.getMessage(), equalTo(String.format("Email: %s already exists", readPerson.getEmail())));
    }

    @Test
    public void createWithExistingEmail() throws ApiException {
        CreateUpdatePerson createUpdatePerson = createPerson();
        personApi.createPerson(createUpdatePerson);

        var ex = assertThrows(ApiException.class, () -> personApi.createPersonWithHttpInfo(createUpdatePerson));


        assertThat(ex.getCode(), equalTo(409));
        assertThat(ex.getMessage(), equalTo(String.format("Person with email:%s already exists", createUpdatePerson.getEmail())));
    }

    @Test
    public void getNonExistingPerson(){
        Long nonExistingId = 100000000L;

        var ex = assertThrows(ApiException.class, () -> personApi.getPersonWithHttpInfo(nonExistingId));


        assertThat(ex.getCode(), equalTo(404));
        assertThat(ex.getMessage(), equalTo(String.format("Person with id %d not found", nonExistingId)));
    }


    private void assertPerson(CreateUpdatePerson expected, ReadPerson actual) {
        assertNotNull(actual);
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getSurname(), actual.getSurname());
        assertEquals(expected.getEmail(), actual.getEmail());
    }

    CreateUpdatePerson createPerson() {
        return new CreateUpdatePerson()
                .email(RandomStringUtils.randomAlphabetic(10).concat("@mail.com"))
                .name("asda")
                .surname("dasdasd")
                .password("321323213211");
    }
}
