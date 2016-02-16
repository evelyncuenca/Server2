package py.com.konecta.redcobros.ejb.impl;

import com.trg.search.ExampleOptions;
import com.trg.search.Search;
import com.trg.search.hibernate.HibernateMetadataUtil;
import com.trg.search.jpa.JPASearchProcessor;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.ejb.HibernateEntityManager;
import py.com.konecta.redcobros.ejb.GenericDao;

public  class GenericDaoImpl<T, ID extends Serializable> implements GenericDao<T, ID>{

    private Class<T> entityBeanType;

    @PersistenceContext
    private EntityManager em;

    public GenericDaoImpl() {
        this.entityBeanType = (Class<T>) ((ParameterizedType) getClass()
                                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public EntityManager getEm() {
        if (em == null)
            throw new IllegalStateException("EntityManager no esta seteado");
        return em;
    }

    @Override
    public Session getSession() {
        return ((HibernateEntityManager) getEm().getDelegate()).getSession();
    }

    public Class<T> getEntityBeanType() {
        return entityBeanType;
    }

    @Override
    public T get(ID id) {
        return (T) getEm().find(getEntityBeanType(), id);
    }

    @Override
    public T getLocked(ID id) {
        //return (T) getSession().get(getEntityBeanType(), id, LockMode.UPGRADE);
        T t = (T) getEm().getReference(entityBeanType, id);
        getEm().lock(t, LockModeType.WRITE);
        return t;
    }

    @Override
    public T get(T ejemplo) { 
        List<T> list = this.list(ejemplo, 0, 2);

        if (list.isEmpty()) {
            return null;
        }
        else if (list.size() == 1) {
            return list.get(0);
        }

        throw new NonUniqueResultException("Se encontro mas de un "+this.getEntityBeanType().getCanonicalName()+
                " para el pedido dado");
    }

    @Override
    public Map<String, Object> get(T ejemplo, String [] atributos) {
        List<Map<String, Object>> lista = this.listAtributos(ejemplo, atributos);

        if (lista.isEmpty()) {
            return null;
        }

        if (lista.size() == 1) {
            return lista.get(0);
        }

        throw new NonUniqueResultException("Se encontro mas de un "+this.getEntityBeanType().getCanonicalName()+
                " para el pedido dado");
    }

    @Override
    public void save(T entity) {
        getEm().persist(entity);
        //getEm().flush();
    }

    @Override
    public T merge(T entity) {
        return (T) getEm().merge(entity);
    }

    @Override
    public void update(T entity) {
        HibernateEntityManager hem = (HibernateEntityManager)getEm().getDelegate();
        hem.getSession().saveOrUpdate(entity);
    }

    @Override
    public void delete(ID id) {
        T entity = this.get(id);
        this.delete(entity);
    }

    @Override
    public void delete(T entity) {
        this.getEm().remove(entity);
        this.getEm().flush();
    }

    @Override
    public Integer total() {
        return this.total(null, false);
    }

    @Override
    public Integer total(T ejemplo) {
        return this.total(ejemplo, false);
    }

    @Override
    public Integer total(T ejemplo, boolean like) {
        HibernateEntityManager hem = (HibernateEntityManager)this.getEm().getDelegate();
        JPASearchProcessor jpaSP = new JPASearchProcessor(HibernateMetadataUtil.getInstanceForSessionFactory(hem.getSession().getSessionFactory()));

        Search s = new Search(this.getEntityBeanType());

        if (ejemplo != null) {
            ExampleOptions exampleOptions = new ExampleOptions();
            exampleOptions.setExcludeNulls(true);
            exampleOptions.setIgnoreCase(true);

            if (like) {
                exampleOptions.setLikeMode(ExampleOptions.ANYWHERE);
            }

            s.addFilter(jpaSP.getFilterFromExample(ejemplo, exampleOptions));
        }

        return jpaSP.count(em, s);
    }

    
    @Override
    public List<T> list() {
        return this.list(null, true, -1, -1, null, null, false, false);
    }

    
    @Override
    public List<T> list(Integer primerResultado, Integer cantResultado) {
        return this.list(null, false, primerResultado, cantResultado, null, null, false, false);
    }

    @Override
    public List<T> list (T ejemplo) {
        return this.list(ejemplo, true, -1, -1, null, null, false, false);
    }

    @Override
    public List<T> list (T ejemplo, boolean like) {
        return this.list(ejemplo, true, -1, -1, null, null, like, false);
    }

    @Override
    public List<T> list (T ejemplo, boolean like, boolean ignorarZeros) {
        return this.list(ejemplo, true, -1, -1, null, null, like, ignorarZeros);
    }
    
    @Override
    public List<T> list (T ejemplo, String orderBy , String dir) {
        return this.list(ejemplo, true, -1, -1,new String[] {orderBy},new String[] {dir}, false, false);
    }

    @Override
    public List<T> list (T ejemplo, String orderBy , String dir, boolean like) {
        return this.list(ejemplo, true, -1, -1,new String[] {orderBy},new String[] {dir}, like, false);
    }

    @Override
    public List<T> list (T ejemplo, String[] orderBy , String [] dir) {
        return this.list(ejemplo, true, -1, -1, orderBy, dir, false, false);
    }

    @Override
    public List<T> list (T ejemplo, String[] orderBy , String [] dir, boolean like) {
        return this.list(ejemplo, true, -1, -1, orderBy, dir, like, false);
    }

    @Override
    public List<T> list(T ejemplo, Integer primerResultado, Integer cantResultados) {
       return this.list(ejemplo, false, primerResultado, cantResultados, null, null, false, false);
    }

    @Override
    public List<T> list(T ejemplo, Integer primerResultado, Integer cantResultados, boolean like) {
       return this.list(ejemplo, false, primerResultado, cantResultados, null, null, like, false);
    }

    @Override
    public List<T> list(T ejemplo, Integer primerResultado, Integer cantResultados, String orderBy, String dir) {
       return this.list(ejemplo, false, primerResultado, cantResultados, new String[] {orderBy}, new String[] {dir}, false, false);
    }

    @Override
    public List<T> list(T ejemplo, Integer primerResultado, Integer cantResultados, String orderBy, String dir, boolean like) {
       return this.list(ejemplo, false, primerResultado, cantResultados, new String[] {orderBy}, new String[] {dir}, like, false);
    }

    @Override
    public List<T> list(T ejemplo, Integer primerResultado, Integer cantResultados, String [] orderBy, String [] dir) {
       return this.list(ejemplo, false, primerResultado, cantResultados, orderBy, dir, false, false);
    }

    @Override
    public List<T> list(T ejemplo, Integer primerResultado, Integer cantResultados, String [] orderBy, String [] dir, boolean like) {
       return this.list(ejemplo, false, primerResultado, cantResultados, orderBy, dir, like, false);
    }

    @Override
    public List<T> list (T ejemplo, boolean all, Integer primerResultado, 
        Integer cantResultados, String [] orderBy , String [] dir, 
        boolean like, boolean ignorarZeros) {
        HibernateEntityManager hem = (HibernateEntityManager)this.getEm().getDelegate();
        JPASearchProcessor jpaSP = new JPASearchProcessor(HibernateMetadataUtil.getInstanceForSessionFactory(hem.getSession().getSessionFactory()));

        Search s = new Search(this.getEntityBeanType());
        
        if (ejemplo != null) {
            ExampleOptions exampleOptions = new ExampleOptions();
            exampleOptions.setExcludeNulls(true);
            exampleOptions.setIgnoreCase(true);
            exampleOptions.setExcludeZeros(ignorarZeros);

            if (like) {
                exampleOptions.setLikeMode(ExampleOptions.ANYWHERE);
            }

            s.addFilter(jpaSP.getFilterFromExample(ejemplo, exampleOptions));
        }

        if (!all) {
            s.setFirstResult(primerResultado);
            s.setMaxResults(cantResultados);
        }

        if(orderBy != null && dir != null && orderBy.length==dir.length) {
            for (int i=0;i<orderBy.length;i++) {
                if (dir[i].equalsIgnoreCase("desc")) {
                    s.addSortDesc(orderBy[i]);
                } else {
                    s.addSortAsc(orderBy[i]);
                }
            }
        }

        return jpaSP.search(em, s);
    }

    @Override
    public List<Map<String, Object>> listAtributos (T ejemplo, String [] atributos) {
        return this.listAtributos(ejemplo, true, null, null, null, null, false, atributos);
    }

    @Override
    public List<Map<String, Object>> listAtributos (T ejemplo, boolean like, String [] atributos){
        return this.listAtributos(ejemplo, true, null, null, null, null, like, atributos);
    }

    @Override
    public List<Map<String, Object>> listAtributos (T ejemplo, Integer primerResultado, Integer cantResultados, String [] atributos) {
        return this.listAtributos(ejemplo, false, primerResultado, cantResultados, null, null, false, atributos);
    }

    @Override
    public List<Map<String, Object>> listAtributos (T ejemplo, boolean all, 
            Integer primerResultado, Integer cantResultados, 
            String [] orderBy , String [] dir, boolean like, String [] atributos)
    {

        if(atributos.length == 0) {
            throw  new RuntimeException("La lista de propiedades no puede ser nula");
        }

        HibernateEntityManager hem = (HibernateEntityManager)this.getEm().getDelegate();
        JPASearchProcessor jpaSP = new JPASearchProcessor(HibernateMetadataUtil.getInstanceForSessionFactory(hem.getSession().getSessionFactory()));

        Search s = new Search(this.getEntityBeanType());

        if (ejemplo != null) {
            ExampleOptions exampleOptions = new ExampleOptions();
            exampleOptions.setExcludeNulls(true);
            exampleOptions.setIgnoreCase(true);

            if (like) {
                exampleOptions.setLikeMode(ExampleOptions.ANYWHERE);
            }

            s.addFilter(jpaSP.getFilterFromExample(ejemplo, exampleOptions));
        }

        if (!all) {
            s.setFirstResult(primerResultado);
            s.setMaxResults(cantResultados);
        }
        
        if(orderBy != null && dir != null && orderBy.length==dir.length) {
            for (int i=0;i<orderBy.length;i++) {
                if (dir[i].equalsIgnoreCase("desc")) {
                    s.addSortDesc(orderBy[i]);
                } else {
                    s.addSortAsc(orderBy[i]);
                }
            }
        }

        for (String a : atributos) {
            s.addField(a);
        }

        s.setResultMode(Search.RESULT_MAP);

        return jpaSP.search(em, s);
    }
}