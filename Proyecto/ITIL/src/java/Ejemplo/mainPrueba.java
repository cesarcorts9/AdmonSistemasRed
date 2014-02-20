package Ejemplo;


import JPA.Entidades.Area;
import JPA.Entidades.Lenguaje;
import JPA.Entidades.Roles;
import JPA.Entidades.Sucursal;
import JPA.Entidades_Controllers.AreaJpaController;
import JPA.Entidades_Controllers.LenguajeJpaController;
import JPA.Entidades_Controllers.RolesJpaController;
import JPA.Entidades_Controllers.SucursalJpaController;
import JPA.Entidades_Controllers.exceptions.RollbackFailureException;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author madman
 */
public class mainPrueba {
    public static void main(String[] args) throws RollbackFailureException, Exception{
        
//        SucursalJpaController control= new SucursalJpaController();//instaciamos la clase controlador
//        Sucursal sucur =new Sucursal();//instanciamos la clase que contiene los getter an setter para llenar nuestro objeto area
//       
//       sucur.setSucursalidSucursal(1);
//       sucur.setSucursalNombre("MEGA");
//       sucur.setSucursalUbicacion("San Mateo");
//       sucur.setSucursalObservacion("bodega");
//       sucur.setSucursalcol("san mateo");
//       sucur.setSucursalObservacion("todo barato");
//       control.create(sucur);//mandamos el objeto de tipo Area al metodo del controlador area
        
        RolesJpaController controlador =new RolesJpaController();
        Roles rol=new Roles();
        rol.setRolidRol("100");
        rol.setRolNombre("Admin");
        rol.setRolDescripcion("super usuario");
        controlador.create(rol);
        
    }
}