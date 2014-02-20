/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JPA.Entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author DELL
 */
@Entity
@Table(name = "it_item")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ItItem.findAll", query = "SELECT i FROM ItItem i"),
    @NamedQuery(name = "ItItem.findByItSerie", query = "SELECT i FROM ItItem i WHERE i.itItemPK.itSerie = :itSerie"),
    @NamedQuery(name = "ItItem.findByItMarca", query = "SELECT i FROM ItItem i WHERE i.itItemPK.itMarca = :itMarca"),
    @NamedQuery(name = "ItItem.findByModelo", query = "SELECT i FROM ItItem i WHERE i.modelo = :modelo")})
public class ItItem implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ItItemPK itItemPK;
    @Size(max = 45)
    @Column(name = "modelo")
    private String modelo;
    @ManyToMany(mappedBy = "itItemList")
    private List<Empleado> empleadoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itItem")
    private List<Laptops> laptopsList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itItem")
    private List<Workstation> workstationList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itItem")
    private List<Telecommunications> telecommunicationsList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itItem")
    private List<Software> softwareList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itItem")
    private List<Servidor> servidorList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itItem")
    private List<Perifericos> perifericosList;

    public ItItem() {
    }

    public ItItem(ItItemPK itItemPK) {
        this.itItemPK = itItemPK;
    }

    public ItItem(String itSerie, String itMarca) {
        this.itItemPK = new ItItemPK(itSerie, itMarca);
    }

    public ItItemPK getItItemPK() {
        return itItemPK;
    }

    public void setItItemPK(ItItemPK itItemPK) {
        this.itItemPK = itItemPK;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    @XmlTransient
    public List<Empleado> getEmpleadoList() {
        return empleadoList;
    }

    public void setEmpleadoList(List<Empleado> empleadoList) {
        this.empleadoList = empleadoList;
    }

    @XmlTransient
    public List<Laptops> getLaptopsList() {
        return laptopsList;
    }

    public void setLaptopsList(List<Laptops> laptopsList) {
        this.laptopsList = laptopsList;
    }

    @XmlTransient
    public List<Workstation> getWorkstationList() {
        return workstationList;
    }

    public void setWorkstationList(List<Workstation> workstationList) {
        this.workstationList = workstationList;
    }

    @XmlTransient
    public List<Telecommunications> getTelecommunicationsList() {
        return telecommunicationsList;
    }

    public void setTelecommunicationsList(List<Telecommunications> telecommunicationsList) {
        this.telecommunicationsList = telecommunicationsList;
    }

    @XmlTransient
    public List<Software> getSoftwareList() {
        return softwareList;
    }

    public void setSoftwareList(List<Software> softwareList) {
        this.softwareList = softwareList;
    }

    @XmlTransient
    public List<Servidor> getServidorList() {
        return servidorList;
    }

    public void setServidorList(List<Servidor> servidorList) {
        this.servidorList = servidorList;
    }

    @XmlTransient
    public List<Perifericos> getPerifericosList() {
        return perifericosList;
    }

    public void setPerifericosList(List<Perifericos> perifericosList) {
        this.perifericosList = perifericosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (itItemPK != null ? itItemPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ItItem)) {
            return false;
        }
        ItItem other = (ItItem) object;
        if ((this.itItemPK == null && other.itItemPK != null) || (this.itItemPK != null && !this.itItemPK.equals(other.itItemPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "JPA.Entidades.ItItem[ itItemPK=" + itItemPK + " ]";
    }
    
}
