package Ejemplo;


import JPA.Entidades.Area;
import JPA.Entidades.Lenguaje;
import JPA.Entidades_Controllers.AreaJpaController;
import JPA.Entidades_Controllers.LenguajeJpaController;
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
        
       AreaJpaController control= new AreaJpaController();//instaciamos la clase controlador
       Area area=new Area();//instanciamos la clase que contiene los getter an setter para llenar nuestro objeto area
       
       area.setAreidArea("100");//llenando el objeto
       area.setAreNombre("Electronica");
       area.setAreDespcripcion("Area de Electronica");
       control.create(area);//mandamos el objeto de tipo Area al metodo del controlador area
    }
}