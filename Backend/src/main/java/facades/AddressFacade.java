package facades;

import entities.Address;
import entities.dto.AddressDTO;
import entities.dto.PersonListDTO;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author Andreas Vikke
 */
public class AddressFacade implements IFacade<AddressDTO> {
    private static EntityManagerFactory emf;
    private static AddressFacade instance;
    
    private AddressFacade(){}
    public static AddressFacade getFacade (EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new AddressFacade();
        }
        return instance;
    }
    
    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public List<AddressDTO> getAll() {
        return getEntityManager().createQuery("SELECT new entities.dto.AddressDTO(a) FROM Address a", AddressDTO.class).getResultList();
    }

    @Override
    public AddressDTO getSingle(long id) {
        Address address = getEntityManager().find(Address.class, id);
        if(address == null)
            throw new WebApplicationException("Address with id " + id + " was not found");
        
        AddressDTO dto = new AddressDTO(address);
        dto.setPersons(new PersonListDTO(address.getPersons()).getPersonsDTO());
        return dto;
    }

    @Override
    public AddressDTO add(AddressDTO obj) throws WebApplicationException {
        EntityManager em = getEntityManager();
        try {
            List<Address> checkAddress = em.createQuery("SELECT a FROM Address a WHERE a.street = :street AND a.city = :city AND a.zip = :zip", Address.class)
                    .setParameter("street", obj.getStreet())
                    .setParameter("city", obj.getCity())
                    .setParameter("zip", obj.getZip())
                    .getResultList();

            if(checkAddress.size() != 0) {
                return new AddressDTO(checkAddress.get(0));
            } 

            Address address = new Address(obj.getStreet(), obj.getCity(), obj.getZip(), null);
            em.getTransaction().begin();
            em.persist(address);
            em.getTransaction().commit();
            return new AddressDTO(address);
        } finally {
            em.close();
        }
    }

    @Override
    public AddressDTO edit(long id, AddressDTO obj) throws WebApplicationException {
        EntityManager em = getEntityManager();
        try {
            Address address = em.find(Address.class, id);

            if(address == null) {
                throw new WebApplicationException("Address with id " + id + " was not found");
            } 

            address.setStreet(obj.getStreet());
            address.setCity(obj.getCity());
            address.setZip(obj.getZip());
            
            em.getTransaction().begin();
            em.merge(address);
            em.getTransaction().commit();
            return new AddressDTO(address);
        } finally {
            em.close();
        }
    }

    @Override
    public AddressDTO delete(long id) throws WebApplicationException {
        EntityManager em = getEntityManager();
        try {
            Address address = em.find(Address.class, id);

            if(address == null) {
                throw new WebApplicationException("Address with id " + id + " was not found");
            } 
            em.getTransaction().begin();
            em.remove(address);
            em.getTransaction().commit();
            return new AddressDTO(address);
        } finally {
            em.close();
        }
    }
}
