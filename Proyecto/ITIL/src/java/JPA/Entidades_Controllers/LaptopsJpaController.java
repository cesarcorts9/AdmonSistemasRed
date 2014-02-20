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
import JPA.Entidades.Laptops;
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
public class LaptopsJpaController implements Serializable {

    public LaptopsJpaController(){
    }
    private EntityManagerFactory emf = null;

    private EntityManager getEntityManager() {
        emf = Persistence.createEntityManagerFactory("It_ITILPU");
        return emf.createEntityManager();
    }

    public void create(Laptops laptops) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (laptops.getComputadoraList() == null) {
            laptops.setComputadoraList(new ArrayList<Computadora>());
        }
        EntityManager em = null;
        try {
            
            em = getEntityManager();
            ItItem itItem = laptops.getItItem();
            if (itItem != null) {
                itItem = em.getReference(itItem.getClass(), itItem.getItItemPK());
                laptops.setItItem(itItem);
            }
            List<Computadora> attachedComputadoraList = new ArrayList<Computadora>();
            for (Computadora computadoraListComputadoraToAttach : laptops.getComputadoraList()) {
                computadoraListComputadoraToAttach = em.getReference(computadoraListComputadoraToAttach.getClass(), computadoraListComputadoraToAttach.getIdComputadora());
                attachedComputadoraList.add(computadoraListComputadoraToAttach);
            }
            laptops.setComputadoraList(attachedComputadoraList);
            em.persist(laptops);
            if (itItem != null) {
                itItem.getLaptopsList().add(laptops);
                itItem = em.merge(itItem);
            }
            for (Computadora computadoraListComputadora : laptops.getComputadoraList()) {
                Laptops oldLaptopsidLaptopOfComputadoraListComputadora = computadoraListComputadora.getLaptopsidLaptop();
                computadoraListComputadora.setLaptopsidLaptop(laptops);
                computadoraListComputadora = em.merge(computadoraListComputadora);
                if (oldLaptopsidLaptopOfComputadoraListComputadora != null) {
                    oldLaptopsidLaptopOfComputadoraListComputadora.getComputadoraList().remove(computadoraListComputadora);
                    oldLaptopsidLaptopOfComputadoraListComputadora = em.merge(oldLaptopsidLaptopOfComputadoraListComputadora);
                }
            }
            
        } catch (Exception ex) {
            try {
               
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findLaptops(laptops.getIdLaptop()) != null) {
                throw new PreexistingEntityException("Laptops " + laptops + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Laptops laptops) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            
            em = getEntityManager();
            Laptops persistentLaptops = em.find(Laptops.class, laptops.getIdLaptop());
            ItItem itItemOld = persistentLaptops.getItItem();
            ItItem itItemNew = laptops.getItItem();
            List<Computadora> computadoraListOld = persistentLaptops.getComputadoraList();
            List<Computadora> computadoraListNew = laptops.getComputadoraList();
            List<String> illegalOrphanMessages = null;
            for (Computadora computadoraListOldComputadora : computadoraListOld) {
                if (!computadoraListNew.contains(computadoraListOldComputadora)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Computadora " + computadoraListOldComputadora + " since its laptopsidLaptop field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (itItemNew != null) {
                itItemNew = em.getReference(itItemNew.getClass(), itItemNew.getItItemPK());
                laptops.setItItem(itItemNew);
            }
            List<Computadora> attachedComputadoraListNew = new ArrayList<Computadora>();
            for (Computadora computadoraListNewComputadoraToAttach : computadoraListNew) {
                computadoraListNewComputadoraToAttach = em.getReference(computadoraListNewComputadoraToAttach.getClass(), computadoraListNewComputadoraToAttach.getIdComputadora());
                attachedComputadoraListNew.add(computadoraListNewComputadoraToAttach);
            }
            computadoraListNew = attachedComputadoraListNew;
            laptops.setComputadoraList(computadoraListNew);
            laptops = em.merge(laptops);
            if (itItemOld != null && !itItemOld.equals(itItemNew)) {
                itItemOld.getLaptopsList().remove(laptops);
                itItemOld = em.merge(itItemOld);
            }
            if (itItemNew != null && !itItemNew.equals(itItemOld)) {
                itItemNew.getLaptopsList().add(laptops);
                itItemNew = em.merge(itItemNew);
            }
            for (Computadora computadoraListNewComputadora : computadoraListNew) {
                if (!computadoraListOld.contains(computadoraListNewComputadora)) {
                    Laptops oldLaptopsidLaptopOfComputadoraListNewComputadora = computadoraListNewComputadora.getLaptopsidLaptop();
                    computadoraListNewComputadora.setLaptopsidLaptop(laptops);
                    computadoraListNewComputadora = em.merge(computadoraListNewComputadora);
                    if (oldLaptopsidLaptopOfComputadoraListNewComputadora != null && !oldLaptopsidLaptopOfComputadoraListNewComputadora.equals(laptops)) {
                        oldLaptopsidLaptopOfComputadoraListNewComputadora.getComputadoraList().remove(computadoraListNewComputadora);
                        oldLaptopsidLaptopOfComputadoraListNewComputadora = em.merge(oldLaptopsidLaptopOfComputadoraListNewComputadora);
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
                String id = laptops.getIdLaptop();
                if (findLaptops(id) == null) {
                    throw new NonexistentEntityException("The laptops with id " + id + " no longer exists.");
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
            Laptops laptops;
            try {
                laptops = em.getReference(Laptops.class, id);
                laptops.getIdLaptop();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The laptops with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Computadora> computadoraListOrphanCheck = laptops.getComputadoraList();
            for (Computadora computadoraListOrphanCheckComputadora : computadoraListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Laptops (" + laptops + ") cannot be destroyed since the Computadora " + computadoraListOrphanCheckComputadora + " in its computadoraList field has a non-nullable laptopsidLaptop field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            ItItem itItem = laptops.getItItem();
            if (itItem != null) {
                itItem.getLaptopsList().remove(laptops);
                itItem = em.merge(itItem);
            }
            em.remove(laptops);
            
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

    public List<Laptops> findLaptopsEntities() {
        return findLaptopsEntities(true, -1, -1);
    }

    public List<Laptops> findLaptopsEntities(int maxResults, int firstResult) {
        return findLaptopsEntities(false, maxResults, firstResult);
    }

    private List<Laptops> findLaptopsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Laptops.class));
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

    public Laptops findLaptops(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Laptops.class, id);
        } finally {
            em.close();
        }
    }

    public int getLaptopsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Laptops> rt = cq.from(Laptops.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
