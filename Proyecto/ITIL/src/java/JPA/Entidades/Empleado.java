/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JPA.Entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "empleado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Empleado.findAll", query = "SELECT e FROM Empleado e"),
    @NamedQuery(name = "Empleado.findByEmpNoEmpleado", query = "SELECT e FROM Empleado e WHERE e.empNoEmpleado = :empNoEmpleado"),
    @NamedQuery(name = "Empleado.findByEmpNombre", query = "SELECT e FROM Empleado e WHERE e.empNombre = :empNombre"),
    @NamedQuery(name = "Empleado.findByEmpPaterno", query = "SELECT e FROM Empleado e WHERE e.empPaterno = :empPaterno"),
    @NamedQuery(name = "Empleado.findByEmpMaterno", query = "SELECT e FROM Empleado e WHERE e.empMaterno = :empMaterno"),
    @NamedQuery(name = "Empleado.findByEmpTelefono", query = "SELECT e FROM Empleado e WHERE e.empTelefono = :empTelefono"),
    @NamedQuery(name = "Empleado.findByEmpCelular", query = "SELECT e FROM Empleado e WHERE e.empCelular = :empCelular"),
    @NamedQuery(name = "Empleado.findByEmpEmail", query = "SELECT e FROM Empleado e WHERE e.empEmail = :empEmail"),
    @NamedQuery(name = "Empleado.findByEmpusrMensajeria", query = "SELECT e FROM Empleado e WHERE e.empusrMensajeria = :empusrMensajeria"),
    @NamedQuery(name = "Empleado.findByEmpUbicacion", query = "SELECT e FROM Empleado e WHERE e.empUbicacion = :empUbicacion"),
    @NamedQuery(name = "Empleado.findByEmpLenguaje", query = "SELECT e FROM Empleado e WHERE e.empLenguaje = :empLenguaje"),
    @NamedQuery(name = "Empleado.findByEmpUtc", query = "SELECT e FROM Empleado e WHERE e.empUtc = :empUtc"),
    @NamedQuery(name = "Empleado.findByEmpDisponibilidad", query = "SELECT e FROM Empleado e WHERE e.empDisponibilidad = :empDisponibilidad"),
    @NamedQuery(name = "Empleado.findByEmpServicios", query = "SELECT e FROM Empleado e WHERE e.empServicios = :empServicios"),
    @NamedQuery(name = "Empleado.findByEmpIntereses", query = "SELECT e FROM Empleado e WHERE e.empIntereses = :empIntereses"),
    @NamedQuery(name = "Empleado.findByEmpCertificaciones", query = "SELECT e FROM Empleado e WHERE e.empCertificaciones = :empCertificaciones"),
    @NamedQuery(name = "Empleado.findByEmpHabilidades", query = "SELECT e FROM Empleado e WHERE e.empHabilidades = :empHabilidades"),
    @NamedQuery(name = "Empleado.findByEmpResponsabilidades", query = "SELECT e FROM Empleado e WHERE e.empResponsabilidades = :empResponsabilidades")})
public class Empleado implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "emp_NoEmpleado")
    private String empNoEmpleado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "emp_nombre")
    private String empNombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "emp_paterno")
    private String empPaterno;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "emp_materno")
    private String empMaterno;
    @Size(max = 20)
    @Column(name = "emp_telefono")
    private String empTelefono;
    @Size(max = 20)
    @Column(name = "emp_celular")
    private String empCelular;
    @Size(max = 45)
    @Column(name = "emp_email")
    private String empEmail;
    @Size(max = 45)
    @Column(name = "emp_usrMensajeria")
    private String empusrMensajeria;
    @Size(max = 45)
    @Column(name = "emp_ubicacion")
    private String empUbicacion;
    @Size(max = 45)
    @Column(name = "emp_lenguaje")
    private String empLenguaje;
    @Size(max = 45)
    @Column(name = "emp_utc")
    private String empUtc;
    @Size(max = 45)
    @Column(name = "emp_disponibilidad")
    private String empDisponibilidad;
    @Size(max = 45)
    @Column(name = "emp_servicios")
    private String empServicios;
    @Size(max = 45)
    @Column(name = "emp_intereses")
    private String empIntereses;
    @Size(max = 45)
    @Column(name = "emp_certificaciones")
    private String empCertificaciones;
    @Size(max = 45)
    @Column(name = "emp_habilidades")
    private String empHabilidades;
    @Size(max = 45)
    @Column(name = "emp_responsabilidades")
    private String empResponsabilidades;
    @JoinTable(name = "empleado_has_lenguaje", joinColumns = {
        @JoinColumn(name = "empleado_emp_NoEmpleado", referencedColumnName = "emp_NoEmpleado")}, inverseJoinColumns = {
        @JoinColumn(name = "lenguaje_len_lenguaje", referencedColumnName = "len_lenguaje")})
    @ManyToMany
    private List<Lenguaje> lenguajeList;
    @JoinTable(name = "empleado_has_roles", joinColumns = {
        @JoinColumn(name = "empleado_emp_NoEmpleado", referencedColumnName = "emp_NoEmpleado")}, inverseJoinColumns = {
        @JoinColumn(name = "roles_rol_idRol", referencedColumnName = "rol_idRol")})
    @ManyToMany
    private List<Roles> rolesList;
    @JoinTable(name = "it_item_has_empleado", joinColumns = {
        @JoinColumn(name = "empleado_emp_NoEmpleado", referencedColumnName = "emp_NoEmpleado")}, inverseJoinColumns = {
        @JoinColumn(name = "IT_item_it_serie", referencedColumnName = "it_serie"),
        @JoinColumn(name = "IT_item_it_marca", referencedColumnName = "it_marca")})
    @ManyToMany
    private List<ItItem> itItemList;
    @JoinColumn(name = "area_are_idArea", referencedColumnName = "are_idArea")
    @ManyToOne(optional = false)
    private Area areaareidArea;

    public Empleado() {
    }

    public Empleado(String empNoEmpleado) {
        this.empNoEmpleado = empNoEmpleado;
    }

    public Empleado(String empNoEmpleado, String empNombre, String empPaterno, String empMaterno) {
        this.empNoEmpleado = empNoEmpleado;
        this.empNombre = empNombre;
        this.empPaterno = empPaterno;
        this.empMaterno = empMaterno;
    }

    public String getEmpNoEmpleado() {
        return empNoEmpleado;
    }

    public void setEmpNoEmpleado(String empNoEmpleado) {
        this.empNoEmpleado = empNoEmpleado;
    }

    public String getEmpNombre() {
        return empNombre;
    }

    public void setEmpNombre(String empNombre) {
        this.empNombre = empNombre;
    }

    public String getEmpPaterno() {
        return empPaterno;
    }

    public void setEmpPaterno(String empPaterno) {
        this.empPaterno = empPaterno;
    }

    public String getEmpMaterno() {
        return empMaterno;
    }

    public void setEmpMaterno(String empMaterno) {
        this.empMaterno = empMaterno;
    }

    public String getEmpTelefono() {
        return empTelefono;
    }

    public void setEmpTelefono(String empTelefono) {
        this.empTelefono = empTelefono;
    }

    public String getEmpCelular() {
        return empCelular;
    }

    public void setEmpCelular(String empCelular) {
        this.empCelular = empCelular;
    }

    public String getEmpEmail() {
        return empEmail;
    }

    public void setEmpEmail(String empEmail) {
        this.empEmail = empEmail;
    }

    public String getEmpusrMensajeria() {
        return empusrMensajeria;
    }

    public void setEmpusrMensajeria(String empusrMensajeria) {
        this.empusrMensajeria = empusrMensajeria;
    }

    public String getEmpUbicacion() {
        return empUbicacion;
    }

    public void setEmpUbicacion(String empUbicacion) {
        this.empUbicacion = empUbicacion;
    }

    public String getEmpLenguaje() {
        return empLenguaje;
    }

    public void setEmpLenguaje(String empLenguaje) {
        this.empLenguaje = empLenguaje;
    }

    public String getEmpUtc() {
        return empUtc;
    }

    public void setEmpUtc(String empUtc) {
        this.empUtc = empUtc;
    }

    public String getEmpDisponibilidad() {
        return empDisponibilidad;
    }

    public void setEmpDisponibilidad(String empDisponibilidad) {
        this.empDisponibilidad = empDisponibilidad;
    }

    public String getEmpServicios() {
        return empServicios;
    }

    public void setEmpServicios(String empServicios) {
        this.empServicios = empServicios;
    }

    public String getEmpIntereses() {
        return empIntereses;
    }

    public void setEmpIntereses(String empIntereses) {
        this.empIntereses = empIntereses;
    }

    public String getEmpCertificaciones() {
        return empCertificaciones;
    }

    public void setEmpCertificaciones(String empCertificaciones) {
        this.empCertificaciones = empCertificaciones;
    }

    public String getEmpHabilidades() {
        return empHabilidades;
    }

    public void setEmpHabilidades(String empHabilidades) {
        this.empHabilidades = empHabilidades;
    }

    public String getEmpResponsabilidades() {
        return empResponsabilidades;
    }

    public void setEmpResponsabilidades(String empResponsabilidades) {
        this.empResponsabilidades = empResponsabilidades;
    }

    @XmlTransient
    public List<Lenguaje> getLenguajeList() {
        return lenguajeList;
    }

    public void setLenguajeList(List<Lenguaje> lenguajeList) {
        this.lenguajeList = lenguajeList;
    }

    @XmlTransient
    public List<Roles> getRolesList() {
        return rolesList;
    }

    public void setRolesList(List<Roles> rolesList) {
        this.rolesList = rolesList;
    }

    @XmlTransient
    public List<ItItem> getItItemList() {
        return itItemList;
    }

    public void setItItemList(List<ItItem> itItemList) {
        this.itItemList = itItemList;
    }

    public Area getAreaareidArea() {
        return areaareidArea;
    }

    public void setAreaareidArea(Area areaareidArea) {
        this.areaareidArea = areaareidArea;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (empNoEmpleado != null ? empNoEmpleado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Empleado)) {
            return false;
        }
        Empleado other = (Empleado) object;
        if ((this.empNoEmpleado == null && other.empNoEmpleado != null) || (this.empNoEmpleado != null && !this.empNoEmpleado.equals(other.empNoEmpleado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "JPA.Entidades.Empleado[ empNoEmpleado=" + empNoEmpleado + " ]";
    }
    
}
