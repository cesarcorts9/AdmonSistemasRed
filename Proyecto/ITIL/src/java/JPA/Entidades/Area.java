/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JPA.Entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author DELL
 */
@Entity
@Table(name = "area")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Area.findAll", query = "SELECT a FROM Area a"),
    @NamedQuery(name = "Area.findByAreidArea", query = "SELECT a FROM Area a WHERE a.areidArea = :areidArea"),
    @NamedQuery(name = "Area.findByAreNombre", query = "SELECT a FROM Area a WHERE a.areNombre = :areNombre"),
    @NamedQuery(name = "Area.findByAreDespcripcion", query = "SELECT a FROM Area a WHERE a.areDespcripcion = :areDespcripcion")})
public class Area implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "are_idArea")
    private String areidArea;
    @Size(max = 45)
    @Column(name = "are_nombre")
    private String areNombre;
    @Size(max = 100)
    @Column(name = "are_despcripcion")
    private String areDespcripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "areaareidArea")
    private List<Empleado> empleadoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "areaareidArea")
    private List<Depto> deptoList;

    public Area() {
    }

    public Area(String areidArea) {
        this.areidArea = areidArea;
    }

    public String getAreidArea() {
        return areidArea;
    }

    public void setAreidArea(String areidArea) {
        this.areidArea = areidArea;
    }

    public String getAreNombre() {
        return areNombre;
    }

    public void setAreNombre(String areNombre) {
        this.areNombre = areNombre;
    }

    public String getAreDespcripcion() {
        return areDespcripcion;
    }

    public void setAreDespcripcion(String areDespcripcion) {
        this.areDespcripcion = areDespcripcion;
    }

    @XmlTransient
    public List<Empleado> getEmpleadoList() {
        return empleadoList;
    }

    public void setEmpleadoList(List<Empleado> empleadoList) {
        this.empleadoList = empleadoList;
    }

    @XmlTransient
    public List<Depto> getDeptoList() {
        return deptoList;
    }

    public void setDeptoList(List<Depto> deptoList) {
        this.deptoList = deptoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (areidArea != null ? areidArea.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Area)) {
            return false;
        }
        Area other = (Area) object;
        if ((this.areidArea == null && other.areidArea != null) || (this.areidArea != null && !this.areidArea.equals(other.areidArea))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "JPA.Entidades.Area[ areidArea=" + areidArea + " ]";
    }
    
}
