package com.gpf.easypagos.bean;

import java.io.Serializable;


public class NewEmployeeRequest implements Serializable {
	
	private static final long serialVersionUID = 1L;
	

		  

			private String PID;
			private String User;
			private EmployeeRequest employee;

			public String getPID() {
				return PID;
			}
			public void setPID(String pID) {
				PID = pID;
			}
			public String getUser() {
				return User;
			}
			public void setUser(String user) {
				User = user;
			}
			public EmployeeRequest getEmployee() {
				return employee;
			}
			public void setEmployee(EmployeeRequest employee) {
				this.employee = employee;
			}
			
			

}


