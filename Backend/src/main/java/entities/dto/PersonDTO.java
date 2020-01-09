package entities.dto;

import entities.Hobby;
import entities.Person;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Andreas Vikke
 */
public class PersonDTO {
    private Long id;
    private String email;
    private String phone;
    private String firstName;
    private String lastName;
    private List<HobbyDTO> hobbies;
    private AddressDTO address;

    public PersonDTO() {
    }

    public PersonDTO(Long id, String email, String phone, String firstName, String lastName, List<HobbyDTO> hobbies, AddressDTO address) {
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.firstName = firstName;
        this.lastName = lastName;
        this.hobbies = hobbies;
        this.address = address;
    }
    
    public PersonDTO(Person person) {
        this.id = person.getId();
        this.email = person.getEmail();
        this.phone = person.getPhone();
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.hobbies = new ArrayList();
        for(Hobby hobby : person.getHobbies())
            hobbies.add(new HobbyDTO(hobby));
        this.address = new AddressDTO(person.getAddress());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<HobbyDTO> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<HobbyDTO> hobbies) {
        this.hobbies = hobbies;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void addAddress(AddressDTO address) {
        this.address = address;
    }
}
