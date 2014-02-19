<%-- 
    Document   : prubaJSP
    Created on : 18/02/2014, 11:18:01 PM
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
        <h1>Hello World!</h1>
        
         <form action="agregar.jsp" method="get">
             IdArea<input type="text" name="idArea"><br>
             Nombre<input type="text" name="Nombre"><br>
             Descripcion<input type="text" name="descripcion"><br>
             <p><input type="submit" value="Enviar"></p>
         </form>
         
         
    </body>
</html>
