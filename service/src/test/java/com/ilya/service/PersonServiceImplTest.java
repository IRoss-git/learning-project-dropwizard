package com.ilya.service;

import com.ilya.service.modelmapper.CreatePersonMapper;
import com.ilya.service.modelmapper.ReadAllPersonsMapper;
import com.ilya.service.modelmapper.ReadPersonMapper;
import com.ilya.service.service.DepartmentService;
import com.ilya.service.service.PersonServiceImpl;
import com.learn.dropwizard.model.AddPersonToDepartmentDTO;
import com.learn.dropwizard.model.CreateUpdatePersonDTO;
import com.learn.dropwizard.model.ReadPersonDTO;
import com.learn.dropwizard.model.ReadPersonsDTO;
import com.ilya.db.dao.PersonDAO;
import com.ilya.db.domain.Department;
import com.ilya.db.domain.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonServiceImplTest {
    @Mock
    private PersonDAO personDAO;

    @InjectMocks
    private PersonServiceImpl personServiceImpl;

    @Mock
    private CreatePersonMapper createPersonMapper;

    @Mock
    private ReadPersonMapper readPersonMapper;

    @Mock
    private ReadAllPersonsMapper readAllPersonsMapper;

    @Mock
    private DepartmentService departmentService;

    @Test
    public void getTest() {
        Person person1 = mock(Person.class);
        ReadPersonDTO mockDto = mock(ReadPersonDTO.class);
        List<Department> departmentList = new ArrayList<>();


        when(personDAO.getPersonById(1L)).thenReturn(person1);
        when(readPersonMapper.convertToDto(person1)).thenReturn(mockDto);
        when(departmentService.getAllDepartmentsByPerson(1L)).thenReturn(departmentList);

        ReadPersonDTO exp = personServiceImpl.getPerson(1L);

        assertThat(exp).isSameAs(mockDto);
    }

    @Test
    public void createTest() {
        CreateUpdatePersonDTO createUpdatePersonDTO = mock(CreateUpdatePersonDTO.class);
        ReadPersonDTO readPersonDTO = mock(ReadPersonDTO.class);
        Person person = mock(Person.class);

        when(readPersonMapper.convertToDto(person)).thenReturn(readPersonDTO);
        when(createPersonMapper.convertToEntity(createUpdatePersonDTO)).thenReturn(person);
        when(personDAO.isPersonExistsByEmail(null)).thenReturn(true);

        ReadPersonDTO expected = personServiceImpl.createPerson(createUpdatePersonDTO);

        assertThat(expected).isNotNull();
        assertThat(expected).isSameAs(readPersonDTO);
    }

    @Test
    public void getAllTest() {
        Person person = mock(Person.class);
        List<Person> personList = new ArrayList<>();
        personList.add(person);

        ReadPersonsDTO readPersonsDTO = mock(ReadPersonsDTO.class);
        List<ReadPersonsDTO> list = new ArrayList<>();
        list.add(readPersonsDTO);


        when(personDAO.getAllPersons(1L, 3L)).thenReturn(personList);
        when(readAllPersonsMapper.convertListToDto(personList)).thenReturn(list);

        List<ReadPersonsDTO> expected = personServiceImpl.getAllPersons(1L, 3L);
        assertThat(expected).isNotNull();
        assertThat(expected).isSameAs(list);
    }

    @Test
    public void deletePersonTest() {

        personServiceImpl.deletePerson(1L);

        verify(personDAO, times(1)).deletePersonById(1L);
    }

    @Test
    public void updatePersonTest() {
        CreateUpdatePersonDTO createUpdatePersonDTO = mock(CreateUpdatePersonDTO.class);
        Person person = mock(Person.class);
        ReadPersonDTO readPersonDTO = mock(ReadPersonDTO.class);


        when(createPersonMapper.convertToEntity(createUpdatePersonDTO)).thenReturn(person);
        when(personDAO.isPersonExistsByEmail(null)).thenReturn(true);
        when(readPersonMapper.convertToDto(person)).thenReturn(readPersonDTO);

        ReadPersonDTO expected = personServiceImpl.updatePerson(1L, createUpdatePersonDTO);

        assertThat(expected).isNotNull();
        verify(personDAO, times(1)).updatePerson(1L, person);
    }

    @Test
    public void addPersonToDepartmentTest() {
        AddPersonToDepartmentDTO addPersonToDepartmentDTO = mock(AddPersonToDepartmentDTO.class);

        when(departmentService.isDepartmentExists(1L)).thenReturn(false);
        personServiceImpl.addPersonToDepartment(1L, addPersonToDepartmentDTO);

        verify(personDAO, times(1)).createPersonInDepartment(isA(Long.class), isA(Long.class));
    }

    @Test
    public void getAllPersonsInDepartmentTest() {
        Person person = mock(Person.class);
        List<Person> personList = new ArrayList<>();
        personList.add(person);

        ReadPersonDTO readPersonDTO = mock(ReadPersonDTO.class);
        List<ReadPersonDTO> list = new ArrayList<>();
        list.add(readPersonDTO);


        when(personDAO.getAllPersonsByDepartment(1L, 1L, 3L)).thenReturn(personList);
        when(readPersonMapper.convertListToDto(personList)).thenReturn(list);

        List<ReadPersonDTO> expected = personServiceImpl.getAllPersonsByDepartment(1L, 1L, 3L);
        assertEquals(1, expected.size());
        assertThat(expected).isSameAs(list);
    }

    @Test
    public void getPersonByDepartmentAndIdTest() {
        Person person1 = mock(Person.class);
        ReadPersonDTO mockDto = mock(ReadPersonDTO.class);

        when(personDAO.getPersonByIdAndDepartmentId(1L, 1L)).thenReturn(person1);
        when(readPersonMapper.convertToDto(person1)).thenReturn(mockDto);

        ReadPersonDTO exp = personServiceImpl.getPersonByDepartmentIdAndId(1L, 1L);

        assertThat(exp).isSameAs(mockDto);
    }

    @Test
    public void deletePersonFromDepartment() {

        personServiceImpl.deletePersonFromDepartment(1L, 1L);

        verify(personDAO, times(1)).deletePersonFromDepartmentById(1L, 1L);
    }
}
