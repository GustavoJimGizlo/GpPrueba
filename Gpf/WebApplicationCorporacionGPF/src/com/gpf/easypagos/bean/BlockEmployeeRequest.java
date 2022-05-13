package com.gpf.easypagos.bean;

import java.io.Serializable;


public class BlockEmployeeRequest implements Serializable {
	
			private static final long serialVersionUID = 1L;
			private String PID;
			private String User;
			private String EmployeeUser;
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
			public String getEmployeeUser() {
				return EmployeeUser;
			}
			public void setEmployeeUser(String employeeUser) {
				EmployeeUser = employeeUser;
			}
   	

}


