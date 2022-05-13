package com.serviciosweb.gpf.facturacion.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.gpf.easypagos.bean.BlockEmployeeRequest;
import com.gpf.easypagos.bean.EmployeeRequest;
import com.gpf.easypagos.bean.NewEmployeeRequest;
import com.gpf.easypagos.bean.ReportByUserSummaryRequest;
import com.serviciosweb.gpf.facturacion.servicios.EasyPagosServices;


@Path("easyPagos")
public class EasyPagosResource {

	   @GET
	   @Path("/newEmployee/{firstName}/{lastName}/{secondLastName}/{userEmployee}/{isActive}/{pid}/{user}")
	   @Produces("text/xml")
	   public String newEmployee( @PathParam("firstName")  String firstName
			   						, @PathParam("lastName")  String lastName
			   						, @PathParam("secondLastName")  String secondLastName
			   						, @PathParam("userEmployee")  String userEmployee
			   						, @PathParam("isActive")  String isActive
			   						, @PathParam("pid")  String pid
			   						, @PathParam("user")  String user){
		   
		   NewEmployeeRequest  newEmployeeRequest = new NewEmployeeRequest();
		   EmployeeRequest employeeRequest = new EmployeeRequest();
		   
		   employeeRequest.setFirstName(firstName);
		   employeeRequest.setLastName(lastName);
		   employeeRequest.setActive("true".equals(isActive)?true:false);
		   employeeRequest.setSecondLastName(secondLastName);	 
		   employeeRequest.setUser(userEmployee);
		   newEmployeeRequest.setEmployee(employeeRequest);
		   newEmployeeRequest.setPID(pid);
		   newEmployeeRequest.setUser(user);
		   EasyPagosServices servicio =new EasyPagosServices(newEmployeeRequest);
   		   return servicio.easyPagosResponse;
	   }
	   
	   @GET
	   @Path("/blockEmployee/{userEmployee}/{PID}/{USER}")
	   @Produces("text/xml")
	   public String confirmacionRecarga(@PathParam("userEmployee")  String userEmployee
			   							, @PathParam("PID")  String pid
			   							, @PathParam("USER")  String user){
		   
		   BlockEmployeeRequest  blockEmployeeRequest = new BlockEmployeeRequest();
		   blockEmployeeRequest.setPID(pid);
		   blockEmployeeRequest.setUser(user);
		   blockEmployeeRequest.setEmployeeUser(userEmployee);
		   EasyPagosServices servicio =new  EasyPagosServices(blockEmployeeRequest);
   		   return servicio.easyPagosResponse;
	   }
	   
	   @GET
	   @Path("/reportsByDate/{dateFrom}/{dateTo}/{employeeUser}/{PID}/{USER}")
	   @Produces("text/xml")
	   public String eliminacionRecarga(  @PathParam("dateFrom")  String dateFrom
			   							, @PathParam("dateTo")  String dateTo
			   							, @PathParam("employeeUser")  String employeeUser
			   							, @PathParam("PID")  String pid
			   							, @PathParam("USER")  String user){

		   NewEmployeeRequest  newEmployeeRequest = new NewEmployeeRequest();
		   EmployeeRequest employeeRequest = new EmployeeRequest();
		   
		   employeeRequest.setFirstName("");
		   employeeRequest.setLastName("");
		   employeeRequest.setActive("true".equals("")?true:false);
		   employeeRequest.setSecondLastName("");	 
		   employeeRequest.setUser("");
		   newEmployeeRequest.setEmployee(employeeRequest);
		   newEmployeeRequest.setPID("7000");
		   newEmployeeRequest.setUser("sanasana");
		   EasyPagosServices servicio =new EasyPagosServices(newEmployeeRequest);
   		   return servicio.easyPagosResponse;
	   }
	   
	   

	   
	   @GET
	   @Path("/reportByUserSummary/{dateFrom}/{dateTo}/{PID}/{USER}")
	   @Produces("text/xml")
	   public String grabaRecarga( @PathParam("dateFrom")  String dateFrom
			   					 , @PathParam("dateTo")  String dateTo
			   					 , @PathParam("PID")  String pid
	   							 , @PathParam("USER")  String user){
		   ReportByUserSummaryRequest  reportByUserSummaryRequest = new ReportByUserSummaryRequest();
		   reportByUserSummaryRequest.setDateFrom(dateFrom);
		   reportByUserSummaryRequest.setDateTo(dateTo);
		   reportByUserSummaryRequest.setPID(pid);
		   reportByUserSummaryRequest.setUser(user);
		   EasyPagosServices servicio =new EasyPagosServices(reportByUserSummaryRequest);
   		   return servicio.easyPagosResponse;

	   }
	   
	   

}
