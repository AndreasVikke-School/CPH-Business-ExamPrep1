package facades;

import entities.Hobby;
import entities.dto.HobbyDTO;
import entities.dto.PersonListDTO;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author Andreas Vikke
 */
public class HobbyFacade implements IFacade<HobbyDTO> {
    private static EntityManagerFactory emf;
    private static HobbyFacade instance;
    
    private HobbyFacade(){}
    public static HobbyFacade getFacade (EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new HobbyFacade();
        }
        return instance;
    }
    
    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public List<HobbyDTO> getAll() {
        return getEntityManager().createQuery("SELECT new entities.dto.HobbyDTO(h) FROM Hobby h", HobbyDTO.class).getResultList();
    }

    @Override
    public HobbyDTO getSingle(long id) {
        Hobby hobby = getEntityManager().find(Hobby.class, id);
        if(hobby == null)
            throw new WebApplicationException("Hobby with id " + id + " was not found");
        
        HobbyDTO dto = new HobbyDTO(hobby);
        dto.setPersons(new PersonListDTO(hobby.getPersons()).getPersonsDTO());
        return dto;
    }
    
    @Override
    public HobbyDTO add(HobbyDTO obj) throws WebApplicationException {
        EntityManager em = getEntityManager();
        try {
            List<Hobby> checkHobby = em.createQuery("SELECT h FROM Hobby h WHERE h.name = :name")
                    .setParameter("name", obj.getName())
                    .getResultList();

            if(checkHobby.size() != 0) {
                return new HobbyDTO(checkHobby.get(0));
            } 

            Hobby hobby = new Hobby(obj.getName(), obj.getDescription(), null);
            em.getTransaction().begin();
            em.persist(hobby);
            em.getTransaction().commit();
            return new HobbyDTO(hobby);
        } finally {
            em.close();
        }
    }

    @Override
    public HobbyDTO edit(long id, HobbyDTO obj) throws WebApplicationException {
        EntityManager em = getEntityManager();
        try {
            Hobby hobby = em.find(Hobby.class, id);

            if(hobby == null) {
                throw new WebApplicationException("Hobby with id " + id + " was not found");
            } 

            hobby.setName(obj.getName());
            hobby.setDescription(obj.getDescription());
            
            em.getTransaction().begin();
            em.merge(hobby);
            em.getTransaction().commit();
            return new HobbyDTO(hobby);
        } finally {
            em.close();
        }
    }

    @Override
    public HobbyDTO delete(long id) throws WebApplicationException {
        EntityManager em = getEntityManager();
        try {
            Hobby hobby = em.find(Hobby.class, id);

            if(hobby == null) {
                throw new WebApplicationException("Hobby with id " + id + " was not found");
            } 
            em.getTransaction().begin();
            em.remove(hobby);
            em.getTransaction().commit();
            return new HobbyDTO(hobby);
        } finally {
            em.close();
        }
    }
}
