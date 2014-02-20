/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JPA.Entidades_Controllers;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import JPA.Entidades.Empleado;
import JPA.Entidades.Lenguaje;
import JPA.Entidades_Controllers.exceptions.NonexistentEntityException;
import JPA.Entidades_Controllers.exceptions.PreexistingEntityException;
import JPA.Entidades_Controllers.exceptions.RollbackFailureException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.UserTransaction;

/**
 *
 * @author DELL
 */
public class LenguajeJpaController implements Serializable {

    public LenguajeJpaController(){
    }
     private EntityManagerFactory emf = null;

    private EntityManager getEntityManager() {
        emf = Persistence.createEntityManagerFactory("It_ITILPU");
        return emf.createEntityManager();
    }

    public void create(Lenguaje lenguaje) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (lenguaje.getEmpleadoList() == null) {
            lenguaje.setEmpleadoList(new ArrayList<Empleado>());
        }
        EntityManager em = null;
        try {
           
            em = getEntityManager();
            List<Empleado> attachedEmpleadoList = new ArrayList<Empleado>();
            for (Empleado empleadoListEmpleadoToAttach : lenguaje.getEmpleadoList()) {
                empleadoListEmpleadoToAttach = em.getReference(empleadoListEmpleadoToAttach.getClass(), empleadoListEmpleadoToAttach.getEmpNoEmpleado());
                attachedEmpleadoList.add(empleadoListEmpleadoToAttach);
            }
            lenguaje.setEmpleadoList(attachedEmpleadoList);
            em.persist(lenguaje);
            for (Empleado empleadoListEmpleado : lenguaje.getEmpleadoList()) {
                empleadoListEmpleado.getLenguajeList().add(lenguaje);
                empleadoListEmpleado = em.merge(empleadoListEmpleado);
            }
           
        } catch (Exception ex) {
            try {
               
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findLenguaje(lenguaje.getLenLenguaje()) != null) {
                throw new PreexistingEntityException("Lenguaje " + lenguaje + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Lenguaje lenguaje) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            
            em = getEntityManager();
            Lenguaje persistentLenguaje = em.find(Lenguaje.class, lenguaje.getLenLenguaje());
            List<Empleado> empleadoListOld = persistentLenguaje.getEmpleadoList();
            List<Empleado> empleadoListNew = lenguaje.getEmpleadoList();
            List<Empleado> attachedEmpleadoListNew = new ArrayList<Empleado>();
            for (Empleado empleadoListNewEmpleadoToAttach : empleadoListNew) {
                empleadoListNewEmpleadoToAttach = em.getReference(empleadoListNewEmpleadoToAttach.getClass(), empleadoListNewEmpleadoToAttach.getEmpNoEmpleado());
                attachedEmpleadoListNew.add(empleadoListNewEmpleadoToAttach);
            }
            empleadoListNew = attachedEmpleadoListNew;
            lenguaje.setEmpleadoList(empleadoListNew);
            lenguaje = em.merge(lenguaje);
            for (Empleado empleadoListOldEmpleado : empleadoListOld) {
                if (!empleadoListNew.contains(empleadoListOldEmpleado)) {
                    empleadoListOldEmpleado.getLenguajeList().remove(lenguaje);
                    empleadoListOldEmpleado = em.merge(empleadoListOldEmpleado);
                }
            }
            for (Empleado empleadoListNewEmpleado : empleadoListNew) {
                if (!empleadoListOld.contains(empleadoListNewEmpleado)) {
                    empleadoListNewEmpleado.getLenguajeList().add(lenguaje);
                    empleadoListNewEmpleado = em.merge(empleadoListNewEmpleado);
                }
            }
          
        } catch (Exception ex) {
            try {
                
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = lenguaje.getLenLenguaje();
                if (findLenguaje(id) == null) {
                    throw new NonexistentEntityException("The lenguaje with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            
            em = getEntityManager();
            Lenguaje lenguaje;
            try {
                lenguaje = em.getReference(Lenguaje.class, id);
                lenguaje.getLenLenguaje();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The lenguaje with id " + id + " no longer exists.", enfe);
            }
            List<Empleado> empleadoList = lenguaje.getEmpleadoList();
            for (Empleado empleadoListEmpleado : empleadoList) {
                empleadoListEmpleado.getLenguajeList().remove(lenguaje);
                empleadoListEmpleado = em.merge(empleadoListEmpleado);
            }
            em.remove(lenguaje);
            
        } catch (Exception ex) {
            try {
                
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Lenguaje> findLenguajeEntities() {
        return findLenguajeEntities(true, -1, -1);
    }

    public List<Lenguaje> findLenguajeEntities(int maxResults, int firstResult) {
        return findLenguajeEntities(false, maxResults, firstResult);
    }

    private List<Lenguaje> findLenguajeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Lenguaje.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Lenguaje findLenguaje(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Lenguaje.class, id);
        } finally {
            em.close();
        }
    }

    public int getLenguajeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Lenguaje> rt = cq.from(Lenguaje.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
