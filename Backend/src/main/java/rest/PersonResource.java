package rest;

import entities.dto.PersonDTO;
import facades.PersonFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import utils.EMF_Creator;

/**
 * REST Web Service
 *
 * @author Andreas Vikke
 */
@Path("person")
public class PersonResource {
    
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
                "pu",
                "jdbc:mysql://localhost:3307/examprep1",
                "dev",
                "ax2",
                EMF_Creator.Strategy.CREATE);
    private static final PersonFacade facade =  PersonFacade.getFacade(EMF);

    public PersonResource() {
    }
    
    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PersonDTO> getAll() {
        return facade.getAll();
    }
    
    @GET
    @Path("id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public PersonDTO getSingle(@PathParam("id") long id) throws Exception {
        return facade.getSingle(id);
    }
    
    @GET
    @Path("email/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public PersonDTO getSingleByEmail(@PathParam("email") String email) throws Exception {
        return facade.getSingleByEmail(email);
    }
    
    @GET
    @Path("phone/{phone}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PersonDTO> getSingleByPhone(@PathParam("phone") String phone) throws Exception {
        return facade.getAllByPhone(phone);
    }
}
