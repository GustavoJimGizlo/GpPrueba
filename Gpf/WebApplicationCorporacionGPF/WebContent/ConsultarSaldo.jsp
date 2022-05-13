<%@ page contentType="text/xml" %>

<%
String nro = request.getParameter("nro");
out.println("<informacion><dato><saldo>"+nro+"</saldo></dato></informacion>");
%>
