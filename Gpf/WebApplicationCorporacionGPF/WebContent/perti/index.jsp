<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<center>
<div>
<h3> PERSONAS </h3>
<form action = "controladorper" method ="POST">
<input type ="submit" name="accion" value="ListarPersonas">
<input type ="submit" name="accion" value="NuevaPersona">
</form>
</div>

<div>

<table border =1>
<thead>
<tr>
<th>Id</th>
<th>Nombre</th>
<th>Cedula</th>
<th>Correo</th>
<th>Acciones</th>
</tr>
<thead>
<tbody>
<c:forEach var ="dato" items="${datos}">
<tr>
<td>${dato.getId()}</td>
<td>${dato.getNombre()}</td>
<td>${dato.getCedula()}</td>
<td>${dato.getCorreo()}</td>

<td>


<form action = "controladorper" method ="POST">

<input type ="hidden" name="p_id" value="${dato.getId()}">
<input type ="submit" name="accion" value="Editar">
<input type ="submit" value="Delete">
</form>
</td>


</tr>
</c:forEach>
</tbody>
</table>
</div>
</center>
</body>
</html>