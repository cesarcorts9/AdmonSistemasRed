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
import JPA.Entidades.ItItem;
import JPA.Entidades.Computadora;
import JPA.Entidades.Workstation;
import JPA.Entidades_Controllers.exceptions.IllegalOrphanException;
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
public class WorkstationJpaController implements Serializable {

    public WorkstationJpaController(){
    }
     private EntityManagerFactory emf = null;

    private EntityManager getEntityManager() {
        emf = Persistence.createEntityManagerFactory("It_ITILPU");
        return emf.createEntityManager();
    }

    public void create(Workstation workstation) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (workstation.getComputadoraList() == null) {
            workstation.setComputadoraList(new ArrayList<Computadora>());
        }
        EntityManager em = null;
        try {
             
            em = getEntityManager();
            ItItem itItem = workstation.getItItem();
            if (itItem != null) {
                itItem = em.getReference(itItem.getClass(), itItem.getItItemPK());
                workstation.setItItem(itItem);
            }
            List<Computadora> attachedComputadoraList = new ArrayList<Computadora>();
            for (Computadora computadoraListComputadoraToAttach : workstation.getComputadoraList()) {
                computadoraListComputadoraToAttach = em.getReference(computadoraListComputadoraToAttach.getClass(), computadoraListComputadoraToAttach.getIdComputadora());
                attachedComputadoraList.add(computadoraListComputadoraToAttach);
            }
            workstation.setComputadoraList(attachedComputadoraList);
            em.persist(workstation);
            if (itItem != null) {
                itItem.getWorkstationList().add(workstation);
                itItem = em.merge(itItem);
            }
            for (Computadora computadoraListComputadora : workstation.getComputadoraList()) {
                Workstation oldWorkstationidWorkstationOfComputadoraListComputadora = computadoraListComputadora.getWorkstationidWorkstation();
                computadoraListComputadora.setWorkstationidWorkstation(workstation);
                computadoraListComputadora = em.merge(computadoraListComputadora);
                if (oldWorkstationidWorkstationOfComputadoraListComputadora != null) {
                    oldWorkstationidWorkstationOfComputadoraListComputadora.getComputadoraList().remove(computadoraListComputadora);
                    oldWorkstationidWorkstationOfComputadoraListComputadora = em.merge(oldWorkstationidWorkstationOfComputadoraListComputadora);
                }
            }
            
        } catch (Exception ex) {
            try {
                 
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findWorkstation(workstation.getIdWorkstation()) != null) {
                throw new PreexistingEntityException("Workstation " + workstation + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Workstation workstation) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
             
            em = getEntityManager();
            Workstation persistentWorkstation = em.find(Workstation.class, workstation.getIdWorkstation());
            ItItem itItemOld = persistentWorkstation.getItItem();
            ItItem itItemNew = workstation.getItItem();
            List<Computadora> computadoraListOld = persistentWorkstation.getComputadoraList();
            List<Computadora> computadoraListNew = workstation.getComputadoraList();
            List<String> illegalOrphanMessages = null;
            for (Computadora computadoraListOldComputadora : computadoraListOld) {
                if (!computadoraListNew.contains(computadoraListOldComputadora)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Computadora " + computadoraListOldComputadora + " since its workstationidWorkstation field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (itItemNew != null) {
                itItemNew = em.getReference(itItemNew.getClass(), itItemNew.getItItemPK());
                workstation.setItItem(itItemNew);
            }
            List<Computadora> attachedComputadoraListNew = new ArrayList<Computadora>();
            for (Computadora computadoraListNewComputadoraToAttach : computadoraListNew) {
                computadoraListNewComputadoraToAttach = em.getReference(computadoraListNewComputadoraToAttach.getClass(), computadoraListNewComputadoraToAttach.getIdComputadora());
                attachedComputadoraListNew.add(computadoraListNewComputadoraToAttach);
            }
            computadoraListNew = attachedComputadoraListNew;
            workstation.setComputadoraList(computadoraListNew);
            workstation = em.merge(workstation);
            if (itItemOld != null && !itItemOld.equals(itItemNew)) {
                itItemOld.getWorkstationList().remove(workstation);
                itItemOld = em.merge(itItemOld);
            }
            if (itItemNew != null && !itItemNew.equals(itItemOld)) {
                itItemNew.getWorkstationList().add(workstation);
                itItemNew = em.merge(itItemNew);
            }
            for (Computadora computadoraListNewComputadora : computadoraListNew) {
                if (!computadoraListOld.contains(computadoraListNewComputadora)) {
                    Workstation oldWorkstationidWorkstationOfComputadoraListNewComputadora = computadoraListNewComputadora.getWorkstationidWorkstation();
                    computadoraListNewComputadora.setWorkstationidWorkstation(workstation);
                    computadoraListNewComputadora = em.merge(computadoraListNewComputadora);
                    if (oldWorkstationidWorkstationOfComputadoraListNewComputadora != null && !oldWorkstationidWorkstationOfComputadoraListNewComputadora.equals(workstation)) {
                        oldWorkstationidWorkstationOfComputadoraListNewComputadora.getComputadoraList().remove(computadoraListNewComputadora);
                        oldWorkstationidWorkstationOfComputadoraListNewComputadora = em.merge(oldWorkstationidWorkstationOfComputadoraListNewComputadora);
                    }
                }
            }
             
        } catch (Exception ex) {
            try {
                
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = workstation.getIdWorkstation();
                if (findWorkstation(id) == null) {
                    throw new NonexistentEntityException("The workstation with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
             
            em = getEntityManager();
            Workstation workstation;
            try {
                workstation = em.getReference(Workstation.class, id);
                workstation.getIdWorkstation();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The workstation with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Computadora> computadoraListOrphanCheck = workstation.getComputadoraList();
            for (Computadora computadoraListOrphanCheckComputadora : computadoraListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Workstation (" + workstation + ") cannot be destroyed since the Computadora " + computadoraListOrphanCheckComputadora + " in its computadoraList field has a non-nullable workstationidWorkstation field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            ItItem itItem = workstation.getItItem();
            if (itItem != null) {
                itItem.getWorkstationList().remove(workstation);
                itItem = em.merge(itItem);
            }
            em.remove(workstation);
             
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

    public List<Workstation> findWorkstationEntities() {
        return findWorkstationEntities(true, -1, -1);
    }

    public List<Workstation> findWorkstationEntities(int maxResults, int firstResult) {
        return findWorkstationEntities(false, maxResults, firstResult);
    }

    private List<Workstation> findWorkstationEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Workstation.class));
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

    public Workstation findWorkstation(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Workstation.class, id);
        } finally {
            em.close();
        }
    }

    public int getWorkstationCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Workstation> rt = cq.from(Workstation.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
