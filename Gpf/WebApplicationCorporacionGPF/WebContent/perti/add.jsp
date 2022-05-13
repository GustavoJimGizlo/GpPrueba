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
<h3> Agregar Personas</h3>
</div>
<hr>
<div>
<form action="controladorper" method ="POST">

Id<br>:<input type="text" name="txtid"><br>
Nombre<br>:<input type="text" name="txtnombre"><br>
Cédula<br>:<input type="text" name="txtcedula"><br>
Correo<br>:<input type="email" name="txtcorreo"><br>
<br>
<input type ="submit" name="accion" value="Grabar">




</form>
</div>
</center>

</body>
</html>