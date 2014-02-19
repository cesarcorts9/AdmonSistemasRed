<%-- 
    Document   : agregar
    Created on : 18/02/2014, 11:26:03 PM
    Author     : DELL
--%>

<%@page import="JPA.Entidades.Area"%>
<%@page import="JPA.Entidades_Controllers.AreaJpaController"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Se Agrego Correctamente</h1>
        <%
          AreaJpaController control= new AreaJpaController();//instaciamos la clase controlador
       Area area=new Area();//instanciamos  
         area.setAreidArea(request.getParameter("idArea"));//llenando el objeto
       area.setAreNombre(request.getParameter("Nombre"));
       area.setAreDespcripcion(request.getParameter("descripcion"));
       
       control.create(area);//mandamos el objeto de tipo Area al metodo del controlador area
            
         %>
    </body>
</html>
