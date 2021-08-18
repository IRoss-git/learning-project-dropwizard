package com.ilya.dropwizard.service;

import com.ilya.dropwizard.dao.DepartmentDAO;
import com.ilya.dropwizard.dao.PersonDAO;
import com.ilya.dropwizard.domain.Department;
import com.ilya.dropwizard.domain.Person;
import com.ilya.dropwizard.mapper.CreatePersonMapper;
import com.ilya.dropwizard.mapper.ReadAllPersonsMapper;
import com.ilya.dropwizard.mapper.ReadPersonMapper;
import com.learn.dropwizard.model.ReadDepartmentDTO;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import com.learn.dropwizard.model.ReadPersonDTO;
import com.learn.dropwizard.model.ReadPersonsDTO;
import com.learn.dropwizard.model.AddPersonToDepartmentDTO;
import com.learn.dropwizard.model.CreateUpdatePersonDTO;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
    public void getPersonTest() {
        Person person = new Person();
        person.setId(1L);
        person.setEmail("dasdas@eqweq");
        ReadPersonDTO readPersonDTO = new ReadPersonDTO();
        readPersonDTO.setId(person.getId());
        readPersonDTO.setEmail(person.getEmail());

        given(personDAO.getPersonById(person.getId())).willReturn(person);
        given(readPersonMapper.convertToDto(person)).willReturn(readPersonDTO);

        ReadPersonDTO expected = personServiceImpl.getPerson(1L);

        assertEquals(expected.getId(), person.getId());
        assertNotNull(expected);
    }

    @Test
    public void alternativeGetTest() {
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

        when(createPersonMapper.convertToEntity(createUpdatePersonDTO)).thenReturn(person);
        when(personDAO.isPersonExistsByEmail(null)).thenReturn(true);

        personServiceImpl.updatePerson(1L, createUpdatePersonDTO);

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