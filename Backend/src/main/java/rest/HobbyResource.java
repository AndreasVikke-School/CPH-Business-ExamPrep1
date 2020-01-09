package rest;

import entities.dto.HobbyDTO;
import facades.HobbyFacade;
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
@Path("hobby")
public class HobbyResource {
    
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
                "pu",
                "jdbc:mysql://localhost:3307/examprep1",
                "dev",
                "ax2",
                EMF_Creator.Strategy.CREATE);
    private static final HobbyFacade facade =  HobbyFacade.getFacade(EMF);

    public HobbyResource() {
    }
    
    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<HobbyDTO> getAll() {
        return facade.getAll();
    }
    
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public HobbyDTO getSingle(@PathParam("id") long id) throws Exception {
        return facade.getSingle(id);
    }
    
    @POST
    @Path("add")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("admin")
    public HobbyDTO add(HobbyDTO hobby) throws Exception {
        return facade.add(hobby);
    }
    
    @POST
    @Path("edit/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("admin")
    public HobbyDTO edit(@PathParam("id") long id, HobbyDTO hobby) throws Exception {
        return facade.edit(id, hobby);
    }
    
    @DELETE
    @Path("delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("admin")
    public HobbyDTO delete(@PathParam("id") long id) throws Exception {
        return facade.delete(id);
    }
}
