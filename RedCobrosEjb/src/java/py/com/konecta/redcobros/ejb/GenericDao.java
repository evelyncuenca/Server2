/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.konecta.redcobros.ejb;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import org.hibernate.Session;

/**
 *
 * @author Kiki
 */
public interface GenericDao<T, ID extends Serializable> {

    public T get(ID id);

    public T getLocked(ID id);

    public T get(T ejemplo);

    public Map<String, Object> get(T ejemplo, String [] atributos);

    public void save(T entity);

    public T merge(T entity);
    
    public Session getSession();
    
    public EntityManager getEm();

    public void update(T entity);

    public void delete(ID id);

    public void delete(T entity);

    public Integer total();

    public Integer total(T ejemplo);

    public Integer total(T ejemplo, boolean like);
    
    public List<T> list();

    public List<T> list(Integer primerResultado, Integer cantResultado);

    public List<T> list(T ejemplo);

    public List<T> list(T ejemplo, boolean like);
    
    public List<T> list(T ejemplo, boolean like, boolean ignorarZeros);

    public List<T> list(T ejemplo, String orderBy, String dir);

    public List<T> list(T ejemplo, String orderBy, String dir, boolean like);

    public List<T> list(T ejemplo, String[] orderBy, String[] dir);

    public List<T> list(T ejemplo, String[] orderBy, String[] dir, boolean like);

    public List<T> list(T ejemplo, Integer primerResultado, Integer cantResultados);

    public List<T> list(T ejemplo, Integer primerResultado, Integer cantResultados, boolean like);

    public List<T> list(T ejemplo, Integer primerResultado, Integer cantResultados, String orderBy, String dir);

    public List<T> list(T ejemplo, Integer primerResultado, Integer cantResultados, String orderBy, String dir, boolean like);

    public List<T> list(T ejemplo, Integer primerResultado, Integer cantResultados, String [] orderBy, String [] dir);

    public List<T> list(T ejemplo, Integer primerResultado, Integer cantResultados, String [] orderBy, String [] dir, boolean like);

    public List<T> list (T ejemplo, boolean all, Integer primerResultado, Integer cantResultados, 
                String [] orderBy , String [] dir, boolean like, boolean ignorarZeros);

    public List<Map<String, Object>> listAtributos(T ejemplo, String[] atributos);

    public List<Map<String, Object>> listAtributos(T ejemplo, boolean like, String[] atributos);

    public List<Map<String, Object>> listAtributos (T ejemplo, Integer primerResultado, Integer cantResultados, String [] atributos);

    public List<Map<String, Object>> listAtributos(T ejemplo, boolean all,
            Integer primerResultado, Integer cantResultados,
            String[] orderBy, String[] dir, boolean like, String[] atributos);
}
