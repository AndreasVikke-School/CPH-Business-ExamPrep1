package rest;

import entities.dto.AddressDTO;
import facades.AddressFacade;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import utils.EMF_Creator;

/**
 * REST Web Service
 *
 * @author Andreas Vikke
 */
@Path("address")
public class AddressResource {
    
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
                "pu",
                "jdbc:mysql://localhost:3307/examprep1",
                "dev",
                "ax2",
                EMF_Creator.Strategy.CREATE);
    private static final AddressFacade facade =  AddressFacade.getFacade(EMF);

    public AddressResource() {
    }
    
    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<AddressDTO> getAll() {
        return facade.getAll();
    }
    
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public AddressDTO getSingle(@PathParam("id") long id) throws Exception {
        return facade.getSingle(id);
    }
    
    @POST
    @Path("{id}")
    @RolesAllowed("admin")
    @Produces(MediaType.APPLICATION_JSON)
    public AddressDTO getSingle(AddressDTO address) throws Exception {
        return facade.add(address);
    }
    
    @POST
    @Path("edit/{id}")
    @RolesAllowed("admin")
    @Produces(MediaType.APPLICATION_JSON)
    public AddressDTO edit(@PathParam("id") long id, AddressDTO hobby) throws Exception {
        return facade.edit(id, hobby);
    }
    
    @DELETE
    @Path("delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("admin")
    public AddressDTO delete(@PathParam("id") long id) throws Exception {
        return facade.delete(id);
    }
}
