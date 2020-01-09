package entities.dto;

import entities.Person;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Andreas Vikke
 */
public class PersonListDTO {
    private List<PersonDTO> personsDTO;

    public PersonListDTO(List<Person> persons) {
        personsDTO = new ArrayList();
        for(Person person : persons)
            personsDTO.add(new PersonDTO(person));
    }

    public List<PersonDTO> getPersonsDTO() {
        return personsDTO;
    }
}
