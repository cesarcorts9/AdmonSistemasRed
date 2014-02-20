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
import JPA.Entidades.ItItem;
import JPA.Entidades.ItItemPK;
import java.util.ArrayList;
import java.util.List;
import JPA.Entidades.Laptops;
import JPA.Entidades.Workstation;
import JPA.Entidades.Telecommunications;
import JPA.Entidades.Software;
import JPA.Entidades.Servidor;
import JPA.Entidades.Perifericos;
import JPA.Entidades_Controllers.exceptions.IllegalOrphanException;
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
public class ItItemJpaController implements Serializable {

    public ItItemJpaController(){
    }
    private EntityManagerFactory emf = null;

    private EntityManager getEntityManager() {
        emf = Persistence.createEntityManagerFactory("It_ITILPU");
        return emf.createEntityManager();
    }
    public void create(ItItem itItem) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (itItem.getItItemPK() == null) {
            itItem.setItItemPK(new ItItemPK());
        }
        if (itItem.getEmpleadoList() == null) {
            itItem.setEmpleadoList(new ArrayList<Empleado>());
        }
        if (itItem.getLaptopsList() == null) {
            itItem.setLaptopsList(new ArrayList<Laptops>());
        }
        if (itItem.getWorkstationList() == null) {
            itItem.setWorkstationList(new ArrayList<Workstation>());
        }
        if (itItem.getTelecommunicationsList() == null) {
            itItem.setTelecommunicationsList(new ArrayList<Telecommunications>());
        }
        if (itItem.getSoftwareList() == null) {
            itItem.setSoftwareList(new ArrayList<Software>());
        }
        if (itItem.getServidorList() == null) {
            itItem.setServidorList(new ArrayList<Servidor>());
        }
        if (itItem.getPerifericosList() == null) {
            itItem.setPerifericosList(new ArrayList<Perifericos>());
        }
        EntityManager em = null;
        try {
           
            em = getEntityManager();
            List<Empleado> attachedEmpleadoList = new ArrayList<Empleado>();
            for (Empleado empleadoListEmpleadoToAttach : itItem.getEmpleadoList()) {
                empleadoListEmpleadoToAttach = em.getReference(empleadoListEmpleadoToAttach.getClass(), empleadoListEmpleadoToAttach.getEmpNoEmpleado());
                attachedEmpleadoList.add(empleadoListEmpleadoToAttach);
            }
            itItem.setEmpleadoList(attachedEmpleadoList);
            List<Laptops> attachedLaptopsList = new ArrayList<Laptops>();
            for (Laptops laptopsListLaptopsToAttach : itItem.getLaptopsList()) {
                laptopsListLaptopsToAttach = em.getReference(laptopsListLaptopsToAttach.getClass(), laptopsListLaptopsToAttach.getIdLaptop());
                attachedLaptopsList.add(laptopsListLaptopsToAttach);
            }
            itItem.setLaptopsList(attachedLaptopsList);
            List<Workstation> attachedWorkstationList = new ArrayList<Workstation>();
            for (Workstation workstationListWorkstationToAttach : itItem.getWorkstationList()) {
                workstationListWorkstationToAttach = em.getReference(workstationListWorkstationToAttach.getClass(), workstationListWorkstationToAttach.getIdWorkstation());
                attachedWorkstationList.add(workstationListWorkstationToAttach);
            }
            itItem.setWorkstationList(attachedWorkstationList);
            List<Telecommunications> attachedTelecommunicationsList = new ArrayList<Telecommunications>();
            for (Telecommunications telecommunicationsListTelecommunicationsToAttach : itItem.getTelecommunicationsList()) {
                telecommunicationsListTelecommunicationsToAttach = em.getReference(telecommunicationsListTelecommunicationsToAttach.getClass(), telecommunicationsListTelecommunicationsToAttach.getIdTelecom());
                attachedTelecommunicationsList.add(telecommunicationsListTelecommunicationsToAttach);
            }
            itItem.setTelecommunicationsList(attachedTelecommunicationsList);
            List<Software> attachedSoftwareList = new ArrayList<Software>();
            for (Software softwareListSoftwareToAttach : itItem.getSoftwareList()) {
                softwareListSoftwareToAttach = em.getReference(softwareListSoftwareToAttach.getClass(), softwareListSoftwareToAttach.getIdSoftware());
                attachedSoftwareList.add(softwareListSoftwareToAttach);
            }
            itItem.setSoftwareList(attachedSoftwareList);
            List<Servidor> attachedServidorList = new ArrayList<Servidor>();
            for (Servidor servidorListServidorToAttach : itItem.getServidorList()) {
                servidorListServidorToAttach = em.getReference(servidorListServidorToAttach.getClass(), servidorListServidorToAttach.getIdServidor());
                attachedServidorList.add(servidorListServidorToAttach);
            }
            itItem.setServidorList(attachedServidorList);
            List<Perifericos> attachedPerifericosList = new ArrayList<Perifericos>();
            for (Perifericos perifericosListPerifericosToAttach : itItem.getPerifericosList()) {
                perifericosListPerifericosToAttach = em.getReference(perifericosListPerifericosToAttach.getClass(), perifericosListPerifericosToAttach.getIdPeriferico());
                attachedPerifericosList.add(perifericosListPerifericosToAttach);
            }
            itItem.setPerifericosList(attachedPerifericosList);
            em.persist(itItem);
            for (Empleado empleadoListEmpleado : itItem.getEmpleadoList()) {
                empleadoListEmpleado.getItItemList().add(itItem);
                empleadoListEmpleado = em.merge(empleadoListEmpleado);
            }
            for (Laptops laptopsListLaptops : itItem.getLaptopsList()) {
                ItItem oldItItemOfLaptopsListLaptops = laptopsListLaptops.getItItem();
                laptopsListLaptops.setItItem(itItem);
                laptopsListLaptops = em.merge(laptopsListLaptops);
                if (oldItItemOfLaptopsListLaptops != null) {
                    oldItItemOfLaptopsListLaptops.getLaptopsList().remove(laptopsListLaptops);
                    oldItItemOfLaptopsListLaptops = em.merge(oldItItemOfLaptopsListLaptops);
                }
            }
            for (Workstation workstationListWorkstation : itItem.getWorkstationList()) {
                ItItem oldItItemOfWorkstationListWorkstation = workstationListWorkstation.getItItem();
                workstationListWorkstation.setItItem(itItem);
                workstationListWorkstation = em.merge(workstationListWorkstation);
                if (oldItItemOfWorkstationListWorkstation != null) {
                    oldItItemOfWorkstationListWorkstation.getWorkstationList().remove(workstationListWorkstation);
                    oldItItemOfWorkstationListWorkstation = em.merge(oldItItemOfWorkstationListWorkstation);
                }
            }
            for (Telecommunications telecommunicationsListTelecommunications : itItem.getTelecommunicationsList()) {
                ItItem oldItItemOfTelecommunicationsListTelecommunications = telecommunicationsListTelecommunications.getItItem();
                telecommunicationsListTelecommunications.setItItem(itItem);
                telecommunicationsListTelecommunications = em.merge(telecommunicationsListTelecommunications);
                if (oldItItemOfTelecommunicationsListTelecommunications != null) {
                    oldItItemOfTelecommunicationsListTelecommunications.getTelecommunicationsList().remove(telecommunicationsListTelecommunications);
                    oldItItemOfTelecommunicationsListTelecommunications = em.merge(oldItItemOfTelecommunicationsListTelecommunications);
                }
            }
            for (Software softwareListSoftware : itItem.getSoftwareList()) {
                ItItem oldItItemOfSoftwareListSoftware = softwareListSoftware.getItItem();
                softwareListSoftware.setItItem(itItem);
                softwareListSoftware = em.merge(softwareListSoftware);
                if (oldItItemOfSoftwareListSoftware != null) {
                    oldItItemOfSoftwareListSoftware.getSoftwareList().remove(softwareListSoftware);
                    oldItItemOfSoftwareListSoftware = em.merge(oldItItemOfSoftwareListSoftware);
                }
            }
            for (Servidor servidorListServidor : itItem.getServidorList()) {
                ItItem oldItItemOfServidorListServidor = servidorListServidor.getItItem();
                servidorListServidor.setItItem(itItem);
                servidorListServidor = em.merge(servidorListServidor);
                if (oldItItemOfServidorListServidor != null) {
                    oldItItemOfServidorListServidor.getServidorList().remove(servidorListServidor);
                    oldItItemOfServidorListServidor = em.merge(oldItItemOfServidorListServidor);
                }
            }
            for (Perifericos perifericosListPerifericos : itItem.getPerifericosList()) {
                ItItem oldItItemOfPerifericosListPerifericos = perifericosListPerifericos.getItItem();
                perifericosListPerifericos.setItItem(itItem);
                perifericosListPerifericos = em.merge(perifericosListPerifericos);
                if (oldItItemOfPerifericosListPerifericos != null) {
                    oldItItemOfPerifericosListPerifericos.getPerifericosList().remove(perifericosListPerifericos);
                    oldItItemOfPerifericosListPerifericos = em.merge(oldItItemOfPerifericosListPerifericos);
                }
            }
           
        } catch (Exception ex) {
            try {
                
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findItItem(itItem.getItItemPK()) != null) {
                throw new PreexistingEntityException("ItItem " + itItem + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ItItem itItem) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            
            em = getEntityManager();
            ItItem persistentItItem = em.find(ItItem.class, itItem.getItItemPK());
            List<Empleado> empleadoListOld = persistentItItem.getEmpleadoList();
            List<Empleado> empleadoListNew = itItem.getEmpleadoList();
            List<Laptops> laptopsListOld = persistentItItem.getLaptopsList();
            List<Laptops> laptopsListNew = itItem.getLaptopsList();
            List<Workstation> workstationListOld = persistentItItem.getWorkstationList();
            List<Workstation> workstationListNew = itItem.getWorkstationList();
            List<Telecommunications> telecommunicationsListOld = persistentItItem.getTelecommunicationsList();
            List<Telecommunications> telecommunicationsListNew = itItem.getTelecommunicationsList();
            List<Software> softwareListOld = persistentItItem.getSoftwareList();
            List<Software> softwareListNew = itItem.getSoftwareList();
            List<Servidor> servidorListOld = persistentItItem.getServidorList();
            List<Servidor> servidorListNew = itItem.getServidorList();
            List<Perifericos> perifericosListOld = persistentItItem.getPerifericosList();
            List<Perifericos> perifericosListNew = itItem.getPerifericosList();
            List<String> illegalOrphanMessages = null;
            for (Laptops laptopsListOldLaptops : laptopsListOld) {
                if (!laptopsListNew.contains(laptopsListOldLaptops)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Laptops " + laptopsListOldLaptops + " since its itItem field is not nullable.");
                }
            }
            for (Workstation workstationListOldWorkstation : workstationListOld) {
                if (!workstationListNew.contains(workstationListOldWorkstation)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Workstation " + workstationListOldWorkstation + " since its itItem field is not nullable.");
                }
            }
            for (Telecommunications telecommunicationsListOldTelecommunications : telecommunicationsListOld) {
                if (!telecommunicationsListNew.contains(telecommunicationsListOldTelecommunications)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Telecommunications " + telecommunicationsListOldTelecommunications + " since its itItem field is not nullable.");
                }
            }
            for (Software softwareListOldSoftware : softwareListOld) {
                if (!softwareListNew.contains(softwareListOldSoftware)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Software " + softwareListOldSoftware + " since its itItem field is not nullable.");
                }
            }
            for (Servidor servidorListOldServidor : servidorListOld) {
                if (!servidorListNew.contains(servidorListOldServidor)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Servidor " + servidorListOldServidor + " since its itItem field is not nullable.");
                }
            }
            for (Perifericos perifericosListOldPerifericos : perifericosListOld) {
                if (!perifericosListNew.contains(perifericosListOldPerifericos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Perifericos " + perifericosListOldPerifericos + " since its itItem field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Empleado> attachedEmpleadoListNew = new ArrayList<Empleado>();
            for (Empleado empleadoListNewEmpleadoToAttach : empleadoListNew) {
                empleadoListNewEmpleadoToAttach = em.getReference(empleadoListNewEmpleadoToAttach.getClass(), empleadoListNewEmpleadoToAttach.getEmpNoEmpleado());
                attachedEmpleadoListNew.add(empleadoListNewEmpleadoToAttach);
            }
            empleadoListNew = attachedEmpleadoListNew;
            itItem.setEmpleadoList(empleadoListNew);
            List<Laptops> attachedLaptopsListNew = new ArrayList<Laptops>();
            for (Laptops laptopsListNewLaptopsToAttach : laptopsListNew) {
                laptopsListNewLaptopsToAttach = em.getReference(laptopsListNewLaptopsToAttach.getClass(), laptopsListNewLaptopsToAttach.getIdLaptop());
                attachedLaptopsListNew.add(laptopsListNewLaptopsToAttach);
            }
            laptopsListNew = attachedLaptopsListNew;
            itItem.setLaptopsList(laptopsListNew);
            List<Workstation> attachedWorkstationListNew = new ArrayList<Workstation>();
            for (Workstation workstationListNewWorkstationToAttach : workstationListNew) {
                workstationListNewWorkstationToAttach = em.getReference(workstationListNewWorkstationToAttach.getClass(), workstationListNewWorkstationToAttach.getIdWorkstation());
                attachedWorkstationListNew.add(workstationListNewWorkstationToAttach);
            }
            workstationListNew = attachedWorkstationListNew;
            itItem.setWorkstationList(workstationListNew);
            List<Telecommunications> attachedTelecommunicationsListNew = new ArrayList<Telecommunications>();
            for (Telecommunications telecommunicationsListNewTelecommunicationsToAttach : telecommunicationsListNew) {
                telecommunicationsListNewTelecommunicationsToAttach = em.getReference(telecommunicationsListNewTelecommunicationsToAttach.getClass(), telecommunicationsListNewTelecommunicationsToAttach.getIdTelecom());
                attachedTelecommunicationsListNew.add(telecommunicationsListNewTelecommunicationsToAttach);
            }
            telecommunicationsListNew = attachedTelecommunicationsListNew;
            itItem.setTelecommunicationsList(telecommunicationsListNew);
            List<Software> attachedSoftwareListNew = new ArrayList<Software>();
            for (Software softwareListNewSoftwareToAttach : softwareListNew) {
                softwareListNewSoftwareToAttach = em.getReference(softwareListNewSoftwareToAttach.getClass(), softwareListNewSoftwareToAttach.getIdSoftware());
                attachedSoftwareListNew.add(softwareListNewSoftwareToAttach);
            }
            softwareListNew = attachedSoftwareListNew;
            itItem.setSoftwareList(softwareListNew);
            List<Servidor> attachedServidorListNew = new ArrayList<Servidor>();
            for (Servidor servidorListNewServidorToAttach : servidorListNew) {
                servidorListNewServidorToAttach = em.getReference(servidorListNewServidorToAttach.getClass(), servidorListNewServidorToAttach.getIdServidor());
                attachedServidorListNew.add(servidorListNewServidorToAttach);
            }
            servidorListNew = attachedServidorListNew;
            itItem.setServidorList(servidorListNew);
            List<Perifericos> attachedPerifericosListNew = new ArrayList<Perifericos>();
            for (Perifericos perifericosListNewPerifericosToAttach : perifericosListNew) {
                perifericosListNewPerifericosToAttach = em.getReference(perifericosListNewPerifericosToAttach.getClass(), perifericosListNewPerifericosToAttach.getIdPeriferico());
                attachedPerifericosListNew.add(perifericosListNewPerifericosToAttach);
            }
            perifericosListNew = attachedPerifericosListNew;
            itItem.setPerifericosList(perifericosListNew);
            itItem = em.merge(itItem);
            for (Empleado empleadoListOldEmpleado : empleadoListOld) {
                if (!empleadoListNew.contains(empleadoListOldEmpleado)) {
                    empleadoListOldEmpleado.getItItemList().remove(itItem);
                    empleadoListOldEmpleado = em.merge(empleadoListOldEmpleado);
                }
            }
            for (Empleado empleadoListNewEmpleado : empleadoListNew) {
                if (!empleadoListOld.contains(empleadoListNewEmpleado)) {
                    empleadoListNewEmpleado.getItItemList().add(itItem);
                    empleadoListNewEmpleado = em.merge(empleadoListNewEmpleado);
                }
            }
            for (Laptops laptopsListNewLaptops : laptopsListNew) {
                if (!laptopsListOld.contains(laptopsListNewLaptops)) {
                    ItItem oldItItemOfLaptopsListNewLaptops = laptopsListNewLaptops.getItItem();
                    laptopsListNewLaptops.setItItem(itItem);
                    laptopsListNewLaptops = em.merge(laptopsListNewLaptops);
                    if (oldItItemOfLaptopsListNewLaptops != null && !oldItItemOfLaptopsListNewLaptops.equals(itItem)) {
                        oldItItemOfLaptopsListNewLaptops.getLaptopsList().remove(laptopsListNewLaptops);
                        oldItItemOfLaptopsListNewLaptops = em.merge(oldItItemOfLaptopsListNewLaptops);
                    }
                }
            }
            for (Workstation workstationListNewWorkstation : workstationListNew) {
                if (!workstationListOld.contains(workstationListNewWorkstation)) {
                    ItItem oldItItemOfWorkstationListNewWorkstation = workstationListNewWorkstation.getItItem();
                    workstationListNewWorkstation.setItItem(itItem);
                    workstationListNewWorkstation = em.merge(workstationListNewWorkstation);
                    if (oldItItemOfWorkstationListNewWorkstation != null && !oldItItemOfWorkstationListNewWorkstation.equals(itItem)) {
                        oldItItemOfWorkstationListNewWorkstation.getWorkstationList().remove(workstationListNewWorkstation);
                        oldItItemOfWorkstationListNewWorkstation = em.merge(oldItItemOfWorkstationListNewWorkstation);
                    }
                }
            }
            for (Telecommunications telecommunicationsListNewTelecommunications : telecommunicationsListNew) {
                if (!telecommunicationsListOld.contains(telecommunicationsListNewTelecommunications)) {
                    ItItem oldItItemOfTelecommunicationsListNewTelecommunications = telecommunicationsListNewTelecommunications.getItItem();
                    telecommunicationsListNewTelecommunications.setItItem(itItem);
                    telecommunicationsListNewTelecommunications = em.merge(telecommunicationsListNewTelecommunications);
                    if (oldItItemOfTelecommunicationsListNewTelecommunications != null && !oldItItemOfTelecommunicationsListNewTelecommunications.equals(itItem)) {
                        oldItItemOfTelecommunicationsListNewTelecommunications.getTelecommunicationsList().remove(telecommunicationsListNewTelecommunications);
                        oldItItemOfTelecommunicationsListNewTelecommunications = em.merge(oldItItemOfTelecommunicationsListNewTelecommunications);
                    }
                }
            }
            for (Software softwareListNewSoftware : softwareListNew) {
                if (!softwareListOld.contains(softwareListNewSoftware)) {
                    ItItem oldItItemOfSoftwareListNewSoftware = softwareListNewSoftware.getItItem();
                    softwareListNewSoftware.setItItem(itItem);
                    softwareListNewSoftware = em.merge(softwareListNewSoftware);
                    if (oldItItemOfSoftwareListNewSoftware != null && !oldItItemOfSoftwareListNewSoftware.equals(itItem)) {
                        oldItItemOfSoftwareListNewSoftware.getSoftwareList().remove(softwareListNewSoftware);
                        oldItItemOfSoftwareListNewSoftware = em.merge(oldItItemOfSoftwareListNewSoftware);
                    }
                }
            }
            for (Servidor servidorListNewServidor : servidorListNew) {
                if (!servidorListOld.contains(servidorListNewServidor)) {
                    ItItem oldItItemOfServidorListNewServidor = servidorListNewServidor.getItItem();
                    servidorListNewServidor.setItItem(itItem);
                    servidorListNewServidor = em.merge(servidorListNewServidor);
                    if (oldItItemOfServidorListNewServidor != null && !oldItItemOfServidorListNewServidor.equals(itItem)) {
                        oldItItemOfServidorListNewServidor.getServidorList().remove(servidorListNewServidor);
                        oldItItemOfServidorListNewServidor = em.merge(oldItItemOfServidorListNewServidor);
                    }
                }
            }
            for (Perifericos perifericosListNewPerifericos : perifericosListNew) {
                if (!perifericosListOld.contains(perifericosListNewPerifericos)) {
                    ItItem oldItItemOfPerifericosListNewPerifericos = perifericosListNewPerifericos.getItItem();
                    perifericosListNewPerifericos.setItItem(itItem);
                    perifericosListNewPerifericos = em.merge(perifericosListNewPerifericos);
                    if (oldItItemOfPerifericosListNewPerifericos != null && !oldItItemOfPerifericosListNewPerifericos.equals(itItem)) {
                        oldItItemOfPerifericosListNewPerifericos.getPerifericosList().remove(perifericosListNewPerifericos);
                        oldItItemOfPerifericosListNewPerifericos = em.merge(oldItItemOfPerifericosListNewPerifericos);
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
                ItItemPK id = itItem.getItItemPK();
                if (findItItem(id) == null) {
                    throw new NonexistentEntityException("The itItem with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ItItemPK id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            
            em = getEntityManager();
            ItItem itItem;
            try {
                itItem = em.getReference(ItItem.class, id);
                itItem.getItItemPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The itItem with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Laptops> laptopsListOrphanCheck = itItem.getLaptopsList();
            for (Laptops laptopsListOrphanCheckLaptops : laptopsListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ItItem (" + itItem + ") cannot be destroyed since the Laptops " + laptopsListOrphanCheckLaptops + " in its laptopsList field has a non-nullable itItem field.");
            }
            List<Workstation> workstationListOrphanCheck = itItem.getWorkstationList();
            for (Workstation workstationListOrphanCheckWorkstation : workstationListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ItItem (" + itItem + ") cannot be destroyed since the Workstation " + workstationListOrphanCheckWorkstation + " in its workstationList field has a non-nullable itItem field.");
            }
            List<Telecommunications> telecommunicationsListOrphanCheck = itItem.getTelecommunicationsList();
            for (Telecommunications telecommunicationsListOrphanCheckTelecommunications : telecommunicationsListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ItItem (" + itItem + ") cannot be destroyed since the Telecommunications " + telecommunicationsListOrphanCheckTelecommunications + " in its telecommunicationsList field has a non-nullable itItem field.");
            }
            List<Software> softwareListOrphanCheck = itItem.getSoftwareList();
            for (Software softwareListOrphanCheckSoftware : softwareListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ItItem (" + itItem + ") cannot be destroyed since the Software " + softwareListOrphanCheckSoftware + " in its softwareList field has a non-nullable itItem field.");
            }
            List<Servidor> servidorListOrphanCheck = itItem.getServidorList();
            for (Servidor servidorListOrphanCheckServidor : servidorListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ItItem (" + itItem + ") cannot be destroyed since the Servidor " + servidorListOrphanCheckServidor + " in its servidorList field has a non-nullable itItem field.");
            }
            List<Perifericos> perifericosListOrphanCheck = itItem.getPerifericosList();
            for (Perifericos perifericosListOrphanCheckPerifericos : perifericosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ItItem (" + itItem + ") cannot be destroyed since the Perifericos " + perifericosListOrphanCheckPerifericos + " in its perifericosList field has a non-nullable itItem field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Empleado> empleadoList = itItem.getEmpleadoList();
            for (Empleado empleadoListEmpleado : empleadoList) {
                empleadoListEmpleado.getItItemList().remove(itItem);
                empleadoListEmpleado = em.merge(empleadoListEmpleado);
            }
            em.remove(itItem);
            
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

    public List<ItItem> findItItemEntities() {
        return findItItemEntities(true, -1, -1);
    }

    public List<ItItem> findItItemEntities(int maxResults, int firstResult) {
        return findItItemEntities(false, maxResults, firstResult);
    }

    private List<ItItem> findItItemEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ItItem.class));
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

    public ItItem findItItem(ItItemPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ItItem.class, id);
        } finally {
            em.close();
        }
    }

    public int getItItemCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ItItem> rt = cq.from(ItItem.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
