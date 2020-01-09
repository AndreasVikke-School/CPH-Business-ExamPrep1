package facades;

import entities.Person;
import entities.dto.PersonDTO;
import errorhandling.NotFoundException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author Andreas Vikke
 */
public class PersonFacade implements IFacade<PersonDTO> {
    
    private static EntityManagerFactory emf;
    private static PersonFacade instance;
    
    private PersonFacade(){}
    public static PersonFacade getFacade (EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }
    
    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public List<PersonDTO> getAll() {
        return getEntityManager().createQuery("SELECT new entities.dto.PersonDTO(p) FROM Person p", PersonDTO.class).getResultList();
    }

    @Override
    public PersonDTO getSingle(long id) throws WebApplicationException {
        Person person = getEntityManager().find(Person.class, id);
        if(person == null)
            throw new WebApplicationException("Person with id " + id + " was not found");
        
        return new PersonDTO(person);
    }
    
    public PersonDTO getSingleByEmail(String email) throws WebApplicationException {
        try {
            PersonDTO person = getEntityManager().createQuery("SELECT new entities.dto.PersonDTO(p) FROM Person p WHERE p.email = :email", PersonDTO.class)
                .setParameter("email", email)
                .getSingleResult();
        
            return person;
        } catch(NoResultException ex) {
            throw new WebApplicationException("Person with email " + email + " was not found");
        }
    }
    
    public List<PersonDTO> getAllByPhone(String phone) throws WebApplicationException {
        try {
            List<PersonDTO> persons = getEntityManager().createQuery("SELECT new entities.dto.PersonDTO(p) FROM Person p WHERE p.phone = :phone", PersonDTO.class)
                .setParameter("phone", phone)
                .getResultList();
        
            return persons;
        } catch(NoResultException ex) {
            throw new WebApplicationException("Person with phone " + phone + " was not found");
        }
    }

    @Override
    public PersonDTO add(PersonDTO obj) throws WebApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PersonDTO edit(long id, PersonDTO obj) throws WebApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PersonDTO delete(long id) throws WebApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
