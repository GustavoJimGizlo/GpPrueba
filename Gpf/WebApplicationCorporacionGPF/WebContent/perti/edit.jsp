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
<h3> Editar</h3>
</div>
<hr>
<div>
<form action="controladorper" method ="POST">

Id<br>:<input type="text" name="txtid" value="${persona.getId()}"><br>
Nombre<br>:<input type="text" name="txtnombre" value="${persona.getNombre()}"> <br>
C�dula<br>:<input type="text" name="txtcedula" value="${persona.getCedula()}"> <br>
Correo<br>:<input type="email" name="txtcorreo" value="${persona.getCorreo()}"> <br>
<br>
<input type ="submit" name="accion" value="Actualizar">




</form>
</div>
</center>



</body>
</html>