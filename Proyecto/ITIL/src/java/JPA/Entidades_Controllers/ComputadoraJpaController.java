/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JPA.Entidades_Controllers;

import JPA.Entidades.Computadora;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import JPA.Entidades.Servidor;
import JPA.Entidades.Laptops;
import JPA.Entidades.Workstation;
import JPA.Entidades.Software;
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
public class ComputadoraJpaController implements Serializable {

    public ComputadoraJpaController(){
        
    }
     private EntityManagerFactory emf = null;

    private EntityManager getEntityManager() {
        emf = Persistence.createEntityManagerFactory("It_ITILPU");
        return emf.createEntityManager();
    }
    
    
    public void create(Computadora computadora) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (computadora.getSoftwareList() == null) {
            computadora.setSoftwareList(new ArrayList<Software>());
        }
        EntityManager em = null;
        try {
            
            em = getEntityManager();
            Servidor servidoridServidor = computadora.getServidoridServidor();
            if (servidoridServidor != null) {
                servidoridServidor = em.getReference(servidoridServidor.getClass(), servidoridServidor.getIdServidor());
                computadora.setServidoridServidor(servidoridServidor);
            }
            Laptops laptopsidLaptop = computadora.getLaptopsidLaptop();
            if (laptopsidLaptop != null) {
                laptopsidLaptop = em.getReference(laptopsidLaptop.getClass(), laptopsidLaptop.getIdLaptop());
                computadora.setLaptopsidLaptop(laptopsidLaptop);
            }
            Workstation workstationidWorkstation = computadora.getWorkstationidWorkstation();
            if (workstationidWorkstation != null) {
                workstationidWorkstation = em.getReference(workstationidWorkstation.getClass(), workstationidWorkstation.getIdWorkstation());
                computadora.setWorkstationidWorkstation(workstationidWorkstation);
            }
            List<Software> attachedSoftwareList = new ArrayList<Software>();
            for (Software softwareListSoftwareToAttach : computadora.getSoftwareList()) {
                softwareListSoftwareToAttach = em.getReference(softwareListSoftwareToAttach.getClass(), softwareListSoftwareToAttach.getIdSoftware());
                attachedSoftwareList.add(softwareListSoftwareToAttach);
            }
            computadora.setSoftwareList(attachedSoftwareList);
            em.persist(computadora);
            if (servidoridServidor != null) {
                servidoridServidor.getComputadoraList().add(computadora);
                servidoridServidor = em.merge(servidoridServidor);
            }
            if (laptopsidLaptop != null) {
                laptopsidLaptop.getComputadoraList().add(computadora);
                laptopsidLaptop = em.merge(laptopsidLaptop);
            }
            if (workstationidWorkstation != null) {
                workstationidWorkstation.getComputadoraList().add(computadora);
                workstationidWorkstation = em.merge(workstationidWorkstation);
            }
            for (Software softwareListSoftware : computadora.getSoftwareList()) {
                softwareListSoftware.getComputadoraList().add(computadora);
                softwareListSoftware = em.merge(softwareListSoftware);
            }
           
        } catch (Exception ex) {
            try {
                
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findComputadora(computadora.getIdComputadora()) != null) {
                throw new PreexistingEntityException("Computadora " + computadora + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Computadora computadora) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            
            em = getEntityManager();
            Computadora persistentComputadora = em.find(Computadora.class, computadora.getIdComputadora());
            Servidor servidoridServidorOld = persistentComputadora.getServidoridServidor();
            Servidor servidoridServidorNew = computadora.getServidoridServidor();
            Laptops laptopsidLaptopOld = persistentComputadora.getLaptopsidLaptop();
            Laptops laptopsidLaptopNew = computadora.getLaptopsidLaptop();
            Workstation workstationidWorkstationOld = persistentComputadora.getWorkstationidWorkstation();
            Workstation workstationidWorkstationNew = computadora.getWorkstationidWorkstation();
            List<Software> softwareListOld = persistentComputadora.getSoftwareList();
            List<Software> softwareListNew = computadora.getSoftwareList();
            if (servidoridServidorNew != null) {
                servidoridServidorNew = em.getReference(servidoridServidorNew.getClass(), servidoridServidorNew.getIdServidor());
                computadora.setServidoridServidor(servidoridServidorNew);
            }
            if (laptopsidLaptopNew != null) {
                laptopsidLaptopNew = em.getReference(laptopsidLaptopNew.getClass(), laptopsidLaptopNew.getIdLaptop());
                computadora.setLaptopsidLaptop(laptopsidLaptopNew);
            }
            if (workstationidWorkstationNew != null) {
                workstationidWorkstationNew = em.getReference(workstationidWorkstationNew.getClass(), workstationidWorkstationNew.getIdWorkstation());
                computadora.setWorkstationidWorkstation(workstationidWorkstationNew);
            }
            List<Software> attachedSoftwareListNew = new ArrayList<Software>();
            for (Software softwareListNewSoftwareToAttach : softwareListNew) {
                softwareListNewSoftwareToAttach = em.getReference(softwareListNewSoftwareToAttach.getClass(), softwareListNewSoftwareToAttach.getIdSoftware());
                attachedSoftwareListNew.add(softwareListNewSoftwareToAttach);
            }
            softwareListNew = attachedSoftwareListNew;
            computadora.setSoftwareList(softwareListNew);
            computadora = em.merge(computadora);
            if (servidoridServidorOld != null && !servidoridServidorOld.equals(servidoridServidorNew)) {
                servidoridServidorOld.getComputadoraList().remove(computadora);
                servidoridServidorOld = em.merge(servidoridServidorOld);
            }
            if (servidoridServidorNew != null && !servidoridServidorNew.equals(servidoridServidorOld)) {
                servidoridServidorNew.getComputadoraList().add(computadora);
                servidoridServidorNew = em.merge(servidoridServidorNew);
            }
            if (laptopsidLaptopOld != null && !laptopsidLaptopOld.equals(laptopsidLaptopNew)) {
                laptopsidLaptopOld.getComputadoraList().remove(computadora);
                laptopsidLaptopOld = em.merge(laptopsidLaptopOld);
            }
            if (laptopsidLaptopNew != null && !laptopsidLaptopNew.equals(laptopsidLaptopOld)) {
                laptopsidLaptopNew.getComputadoraList().add(computadora);
                laptopsidLaptopNew = em.merge(laptopsidLaptopNew);
            }
            if (workstationidWorkstationOld != null && !workstationidWorkstationOld.equals(workstationidWorkstationNew)) {
                workstationidWorkstationOld.getComputadoraList().remove(computadora);
                workstationidWorkstationOld = em.merge(workstationidWorkstationOld);
            }
            if (workstationidWorkstationNew != null && !workstationidWorkstationNew.equals(workstationidWorkstationOld)) {
                workstationidWorkstationNew.getComputadoraList().add(computadora);
                workstationidWorkstationNew = em.merge(workstationidWorkstationNew);
            }
            for (Software softwareListOldSoftware : softwareListOld) {
                if (!softwareListNew.contains(softwareListOldSoftware)) {
                    softwareListOldSoftware.getComputadoraList().remove(computadora);
                    softwareListOldSoftware = em.merge(softwareListOldSoftware);
                }
            }
            for (Software softwareListNewSoftware : softwareListNew) {
                if (!softwareListOld.contains(softwareListNewSoftware)) {
                    softwareListNewSoftware.getComputadoraList().add(computadora);
                    softwareListNewSoftware = em.merge(softwareListNewSoftware);
                }
            }
           
        } catch (Exception ex) {
            try {
                
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = computadora.getIdComputadora();
                if (findComputadora(id) == null) {
                    throw new NonexistentEntityException("The computadora with id " + id + " no longer exists.");
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
            Computadora computadora;
            try {
                computadora = em.getReference(Computadora.class, id);
                computadora.getIdComputadora();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The computadora with id " + id + " no longer exists.", enfe);
            }
            Servidor servidoridServidor = computadora.getServidoridServidor();
            if (servidoridServidor != null) {
                servidoridServidor.getComputadoraList().remove(computadora);
                servidoridServidor = em.merge(servidoridServidor);
            }
            Laptops laptopsidLaptop = computadora.getLaptopsidLaptop();
            if (laptopsidLaptop != null) {
                laptopsidLaptop.getComputadoraList().remove(computadora);
                laptopsidLaptop = em.merge(laptopsidLaptop);
            }
            Workstation workstationidWorkstation = computadora.getWorkstationidWorkstation();
            if (workstationidWorkstation != null) {
                workstationidWorkstation.getComputadoraList().remove(computadora);
                workstationidWorkstation = em.merge(workstationidWorkstation);
            }
            List<Software> softwareList = computadora.getSoftwareList();
            for (Software softwareListSoftware : softwareList) {
                softwareListSoftware.getComputadoraList().remove(computadora);
                softwareListSoftware = em.merge(softwareListSoftware);
            }
            em.remove(computadora);
            
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

    public List<Computadora> findComputadoraEntities() {
        return findComputadoraEntities(true, -1, -1);
    }

    public List<Computadora> findComputadoraEntities(int maxResults, int firstResult) {
        return findComputadoraEntities(false, maxResults, firstResult);
    }

    private List<Computadora> findComputadoraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Computadora.class));
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

    public Computadora findComputadora(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Computadora.class, id);
        } finally {
            em.close();
        }
    }

    public int getComputadoraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Computadora> rt = cq.from(Computadora.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
