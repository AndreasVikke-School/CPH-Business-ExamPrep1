package entities.dto;

import entities.Address;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Andreas Vikke
 */
public class AddressDTO {
    private Long id;
    private String street;
    private String city;
    private String zip;
    private List<PersonDTO> persons;

    public AddressDTO() {
    }

    public AddressDTO(Long id, String street, String city, String zip, List<PersonDTO> persons) {
        this.id = id;
        this.street = street;
        this.city = city;
        this.zip = zip;
        this.persons = persons;
    }
    
    public AddressDTO(Address address) {
        this.id = address.getId();
        this.street = address.getStreet();
        this.city = address.getCity();
        this.zip = address.getZip();
        this.persons = new ArrayList();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public List<PersonDTO> getPersons() {
        return persons;
    }

    public void setPersons(List<PersonDTO> persons) {
        this.persons = persons;
    }
}
