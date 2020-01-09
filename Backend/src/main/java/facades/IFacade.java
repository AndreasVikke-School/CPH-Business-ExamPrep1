package facades;

import java.util.List;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author Andreas Vikke
 */
public interface IFacade <T> {
    public List<T> getAll();
    public T getSingle(long id) throws WebApplicationException;
    public T add(T obj) throws WebApplicationException;
    public T edit(long id, T obj) throws WebApplicationException;
    public T delete(long id) throws WebApplicationException;
}
