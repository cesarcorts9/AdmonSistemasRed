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
import JPA.Entidades.Area;
import JPA.Entidades.Empleado;
import JPA.Entidades.Lenguaje;
import java.util.ArrayList;
import java.util.List;
import JPA.Entidades.Roles;
import JPA.Entidades.ItItem;
import JPA.Entidades_Controllers.exceptions.NonexistentEntityException;
import JPA.Entidades_Controllers.exceptions.PreexistingEntityException;
import JPA.Entidades_Controllers.exceptions.RollbackFailureException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.UserTransaction;

/**
 *
 * @author DELL
 */
public class EmpleadoJpaController implements Serializable {

    public EmpleadoJpaController() {
        
    }
     private EntityManagerFactory emf = null;

    private EntityManager getEntityManager() {
        emf = Persistence.createEntityManagerFactory("It_ITILPU");
        return emf.createEntityManager();
    }

    public void create(Empleado empleado) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (empleado.getLenguajeList() == null) {
            empleado.setLenguajeList(new ArrayList<Lenguaje>());
        }
        if (empleado.getRolesList() == null) {
            empleado.setRolesList(new ArrayList<Roles>());
        }
        if (empleado.getItItemList() == null) {
            empleado.setItItemList(new ArrayList<ItItem>());
        }
        EntityManager em = null;
        try {
            
            em = getEntityManager();
            Area areaareidArea = empleado.getAreaareidArea();
            if (areaareidArea != null) {
                areaareidArea = em.getReference(areaareidArea.getClass(), areaareidArea.getAreidArea());
                empleado.setAreaareidArea(areaareidArea);
            }
            List<Lenguaje> attachedLenguajeList = new ArrayList<Lenguaje>();
            for (Lenguaje lenguajeListLenguajeToAttach : empleado.getLenguajeList()) {
                lenguajeListLenguajeToAttach = em.getReference(lenguajeListLenguajeToAttach.getClass(), lenguajeListLenguajeToAttach.getLenLenguaje());
                attachedLenguajeList.add(lenguajeListLenguajeToAttach);
            }
            empleado.setLenguajeList(attachedLenguajeList);
            List<Roles> attachedRolesList = new ArrayList<Roles>();
            for (Roles rolesListRolesToAttach : empleado.getRolesList()) {
                rolesListRolesToAttach = em.getReference(rolesListRolesToAttach.getClass(), rolesListRolesToAttach.getRolidRol());
                attachedRolesList.add(rolesListRolesToAttach);
            }
            empleado.setRolesList(attachedRolesList);
            List<ItItem> attachedItItemList = new ArrayList<ItItem>();
            for (ItItem itItemListItItemToAttach : empleado.getItItemList()) {
                itItemListItItemToAttach = em.getReference(itItemListItItemToAttach.getClass(), itItemListItItemToAttach.getItItemPK());
                attachedItItemList.add(itItemListItItemToAttach);
            }
            empleado.setItItemList(attachedItItemList);
            em.persist(empleado);
            if (areaareidArea != null) {
                areaareidArea.getEmpleadoList().add(empleado);
                areaareidArea = em.merge(areaareidArea);
            }
            for (Lenguaje lenguajeListLenguaje : empleado.getLenguajeList()) {
                lenguajeListLenguaje.getEmpleadoList().add(empleado);
                lenguajeListLenguaje = em.merge(lenguajeListLenguaje);
            }
            for (Roles rolesListRoles : empleado.getRolesList()) {
                rolesListRoles.getEmpleadoList().add(empleado);
                rolesListRoles = em.merge(rolesListRoles);
            }
            for (ItItem itItemListItItem : empleado.getItItemList()) {
                itItemListItItem.getEmpleadoList().add(empleado);
                itItemListItItem = em.merge(itItemListItItem);
            }
           
        } catch (Exception ex) {
            try {
                
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findEmpleado(empleado.getEmpNoEmpleado()) != null) {
                throw new PreexistingEntityException("Empleado " + empleado + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Empleado empleado) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
           
            em = getEntityManager();
            Empleado persistentEmpleado = em.find(Empleado.class, empleado.getEmpNoEmpleado());
            Area areaareidAreaOld = persistentEmpleado.getAreaareidArea();
            Area areaareidAreaNew = empleado.getAreaareidArea();
            List<Lenguaje> lenguajeListOld = persistentEmpleado.getLenguajeList();
            List<Lenguaje> lenguajeListNew = empleado.getLenguajeList();
            List<Roles> rolesListOld = persistentEmpleado.getRolesList();
            List<Roles> rolesListNew = empleado.getRolesList();
            List<ItItem> itItemListOld = persistentEmpleado.getItItemList();
            List<ItItem> itItemListNew = empleado.getItItemList();
            if (areaareidAreaNew != null) {
                areaareidAreaNew = em.getReference(areaareidAreaNew.getClass(), areaareidAreaNew.getAreidArea());
                empleado.setAreaareidArea(areaareidAreaNew);
            }
            List<Lenguaje> attachedLenguajeListNew = new ArrayList<Lenguaje>();
            for (Lenguaje lenguajeListNewLenguajeToAttach : lenguajeListNew) {
                lenguajeListNewLenguajeToAttach = em.getReference(lenguajeListNewLenguajeToAttach.getClass(), lenguajeListNewLenguajeToAttach.getLenLenguaje());
                attachedLenguajeListNew.add(lenguajeListNewLenguajeToAttach);
            }
            lenguajeListNew = attachedLenguajeListNew;
            empleado.setLenguajeList(lenguajeListNew);
            List<Roles> attachedRolesListNew = new ArrayList<Roles>();
            for (Roles rolesListNewRolesToAttach : rolesListNew) {
                rolesListNewRolesToAttach = em.getReference(rolesListNewRolesToAttach.getClass(), rolesListNewRolesToAttach.getRolidRol());
                attachedRolesListNew.add(rolesListNewRolesToAttach);
            }
            rolesListNew = attachedRolesListNew;
            empleado.setRolesList(rolesListNew);
            List<ItItem> attachedItItemListNew = new ArrayList<ItItem>();
            for (ItItem itItemListNewItItemToAttach : itItemListNew) {
                itItemListNewItItemToAttach = em.getReference(itItemListNewItItemToAttach.getClass(), itItemListNewItItemToAttach.getItItemPK());
                attachedItItemListNew.add(itItemListNewItItemToAttach);
            }
            itItemListNew = attachedItItemListNew;
            empleado.setItItemList(itItemListNew);
            empleado = em.merge(empleado);
            if (areaareidAreaOld != null && !areaareidAreaOld.equals(areaareidAreaNew)) {
                areaareidAreaOld.getEmpleadoList().remove(empleado);
                areaareidAreaOld = em.merge(areaareidAreaOld);
            }
            if (areaareidAreaNew != null && !areaareidAreaNew.equals(areaareidAreaOld)) {
                areaareidAreaNew.getEmpleadoList().add(empleado);
                areaareidAreaNew = em.merge(areaareidAreaNew);
            }
            for (Lenguaje lenguajeListOldLenguaje : lenguajeListOld) {
                if (!lenguajeListNew.contains(lenguajeListOldLenguaje)) {
                    lenguajeListOldLenguaje.getEmpleadoList().remove(empleado);
                    lenguajeListOldLenguaje = em.merge(lenguajeListOldLenguaje);
                }
            }
            for (Lenguaje lenguajeListNewLenguaje : lenguajeListNew) {
                if (!lenguajeListOld.contains(lenguajeListNewLenguaje)) {
                    lenguajeListNewLenguaje.getEmpleadoList().add(empleado);
                    lenguajeListNewLenguaje = em.merge(lenguajeListNewLenguaje);
                }
            }
            for (Roles rolesListOldRoles : rolesListOld) {
                if (!rolesListNew.contains(rolesListOldRoles)) {
                    rolesListOldRoles.getEmpleadoList().remove(empleado);
                    rolesListOldRoles = em.merge(rolesListOldRoles);
                }
            }
            for (Roles rolesListNewRoles : rolesListNew) {
                if (!rolesListOld.contains(rolesListNewRoles)) {
                    rolesListNewRoles.getEmpleadoList().add(empleado);
                    rolesListNewRoles = em.merge(rolesListNewRoles);
                }
            }
            for (ItItem itItemListOldItItem : itItemListOld) {
                if (!itItemListNew.contains(itItemListOldItItem)) {
                    itItemListOldItItem.getEmpleadoList().remove(empleado);
                    itItemListOldItItem = em.merge(itItemListOldItItem);
                }
            }
            for (ItItem itItemListNewItItem : itItemListNew) {
                if (!itItemListOld.contains(itItemListNewItItem)) {
                    itItemListNewItItem.getEmpleadoList().add(empleado);
                    itItemListNewItItem = em.merge(itItemListNewItItem);
                }
            }
           
        } catch (Exception ex) {
            try {
                
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = empleado.getEmpNoEmpleado();
                if (findEmpleado(id) == null) {
                    throw new NonexistentEntityException("The empleado with id " + id + " no longer exists.");
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
            Empleado empleado;
            try {
                empleado = em.getReference(Empleado.class, id);
                empleado.getEmpNoEmpleado();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empleado with id " + id + " no longer exists.", enfe);
            }
            Area areaareidArea = empleado.getAreaareidArea();
            if (areaareidArea != null) {
                areaareidArea.getEmpleadoList().remove(empleado);
                areaareidArea = em.merge(areaareidArea);
            }
            List<Lenguaje> lenguajeList = empleado.getLenguajeList();
            for (Lenguaje lenguajeListLenguaje : lenguajeList) {
                lenguajeListLenguaje.getEmpleadoList().remove(empleado);
                lenguajeListLenguaje = em.merge(lenguajeListLenguaje);
            }
            List<Roles> rolesList = empleado.getRolesList();
            for (Roles rolesListRoles : rolesList) {
                rolesListRoles.getEmpleadoList().remove(empleado);
                rolesListRoles = em.merge(rolesListRoles);
            }
            List<ItItem> itItemList = empleado.getItItemList();
            for (ItItem itItemListItItem : itItemList) {
                itItemListItItem.getEmpleadoList().remove(empleado);
                itItemListItItem = em.merge(itItemListItItem);
            }
            em.remove(empleado);
           
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

    public List<Empleado> findEmpleadoEntities() {
        return findEmpleadoEntities(true, -1, -1);
    }

    public List<Empleado> findEmpleadoEntities(int maxResults, int firstResult) {
        return findEmpleadoEntities(false, maxResults, firstResult);
    }

    private List<Empleado> findEmpleadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Empleado.class));
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

    public Empleado findEmpleado(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Empleado.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpleadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Empleado> rt = cq.from(Empleado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
