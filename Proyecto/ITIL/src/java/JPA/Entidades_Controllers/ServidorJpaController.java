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
import JPA.Entidades.Servidor;
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
public class ServidorJpaController implements Serializable {

    public ServidorJpaController(){
    }
     private EntityManagerFactory emf = null;

    private EntityManager getEntityManager() {
        emf = Persistence.createEntityManagerFactory("It_ITILPU");
        return emf.createEntityManager();
    }

    public void create(Servidor servidor) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (servidor.getComputadoraList() == null) {
            servidor.setComputadoraList(new ArrayList<Computadora>());
        }
        EntityManager em = null;
        try {
             
            em = getEntityManager();
            ItItem itItem = servidor.getItItem();
            if (itItem != null) {
                itItem = em.getReference(itItem.getClass(), itItem.getItItemPK());
                servidor.setItItem(itItem);
            }
            List<Computadora> attachedComputadoraList = new ArrayList<Computadora>();
            for (Computadora computadoraListComputadoraToAttach : servidor.getComputadoraList()) {
                computadoraListComputadoraToAttach = em.getReference(computadoraListComputadoraToAttach.getClass(), computadoraListComputadoraToAttach.getIdComputadora());
                attachedComputadoraList.add(computadoraListComputadoraToAttach);
            }
            servidor.setComputadoraList(attachedComputadoraList);
            em.persist(servidor);
            if (itItem != null) {
                itItem.getServidorList().add(servidor);
                itItem = em.merge(itItem);
            }
            for (Computadora computadoraListComputadora : servidor.getComputadoraList()) {
                Servidor oldServidoridServidorOfComputadoraListComputadora = computadoraListComputadora.getServidoridServidor();
                computadoraListComputadora.setServidoridServidor(servidor);
                computadoraListComputadora = em.merge(computadoraListComputadora);
                if (oldServidoridServidorOfComputadoraListComputadora != null) {
                    oldServidoridServidorOfComputadoraListComputadora.getComputadoraList().remove(computadoraListComputadora);
                    oldServidoridServidorOfComputadoraListComputadora = em.merge(oldServidoridServidorOfComputadoraListComputadora);
                }
            }
            
        } catch (Exception ex) {
            try {
                
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findServidor(servidor.getIdServidor()) != null) {
                throw new PreexistingEntityException("Servidor " + servidor + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Servidor servidor) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
             
            em = getEntityManager();
            Servidor persistentServidor = em.find(Servidor.class, servidor.getIdServidor());
            ItItem itItemOld = persistentServidor.getItItem();
            ItItem itItemNew = servidor.getItItem();
            List<Computadora> computadoraListOld = persistentServidor.getComputadoraList();
            List<Computadora> computadoraListNew = servidor.getComputadoraList();
            List<String> illegalOrphanMessages = null;
            for (Computadora computadoraListOldComputadora : computadoraListOld) {
                if (!computadoraListNew.contains(computadoraListOldComputadora)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Computadora " + computadoraListOldComputadora + " since its servidoridServidor field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (itItemNew != null) {
                itItemNew = em.getReference(itItemNew.getClass(), itItemNew.getItItemPK());
                servidor.setItItem(itItemNew);
            }
            List<Computadora> attachedComputadoraListNew = new ArrayList<Computadora>();
            for (Computadora computadoraListNewComputadoraToAttach : computadoraListNew) {
                computadoraListNewComputadoraToAttach = em.getReference(computadoraListNewComputadoraToAttach.getClass(), computadoraListNewComputadoraToAttach.getIdComputadora());
                attachedComputadoraListNew.add(computadoraListNewComputadoraToAttach);
            }
            computadoraListNew = attachedComputadoraListNew;
            servidor.setComputadoraList(computadoraListNew);
            servidor = em.merge(servidor);
            if (itItemOld != null && !itItemOld.equals(itItemNew)) {
                itItemOld.getServidorList().remove(servidor);
                itItemOld = em.merge(itItemOld);
            }
            if (itItemNew != null && !itItemNew.equals(itItemOld)) {
                itItemNew.getServidorList().add(servidor);
                itItemNew = em.merge(itItemNew);
            }
            for (Computadora computadoraListNewComputadora : computadoraListNew) {
                if (!computadoraListOld.contains(computadoraListNewComputadora)) {
                    Servidor oldServidoridServidorOfComputadoraListNewComputadora = computadoraListNewComputadora.getServidoridServidor();
                    computadoraListNewComputadora.setServidoridServidor(servidor);
                    computadoraListNewComputadora = em.merge(computadoraListNewComputadora);
                    if (oldServidoridServidorOfComputadoraListNewComputadora != null && !oldServidoridServidorOfComputadoraListNewComputadora.equals(servidor)) {
                        oldServidoridServidorOfComputadoraListNewComputadora.getComputadoraList().remove(computadoraListNewComputadora);
                        oldServidoridServidorOfComputadoraListNewComputadora = em.merge(oldServidoridServidorOfComputadoraListNewComputadora);
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
                String id = servidor.getIdServidor();
                if (findServidor(id) == null) {
                    throw new NonexistentEntityException("The servidor with id " + id + " no longer exists.");
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
            Servidor servidor;
            try {
                servidor = em.getReference(Servidor.class, id);
                servidor.getIdServidor();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The servidor with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Computadora> computadoraListOrphanCheck = servidor.getComputadoraList();
            for (Computadora computadoraListOrphanCheckComputadora : computadoraListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Servidor (" + servidor + ") cannot be destroyed since the Computadora " + computadoraListOrphanCheckComputadora + " in its computadoraList field has a non-nullable servidoridServidor field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            ItItem itItem = servidor.getItItem();
            if (itItem != null) {
                itItem.getServidorList().remove(servidor);
                itItem = em.merge(itItem);
            }
            em.remove(servidor);
             
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

    public List<Servidor> findServidorEntities() {
        return findServidorEntities(true, -1, -1);
    }

    public List<Servidor> findServidorEntities(int maxResults, int firstResult) {
        return findServidorEntities(false, maxResults, firstResult);
    }

    private List<Servidor> findServidorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Servidor.class));
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

    public Servidor findServidor(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Servidor.class, id);
        } finally {
            em.close();
        }
    }

    public int getServidorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Servidor> rt = cq.from(Servidor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
