package com.gpf.easypagos.bean;

import java.io.Serializable;


public class EmployeeRequest implements Serializable {
	
	private static final long serialVersionUID = 1L;

			private String  FirstName;
			private String  LastName;
			private String  SecondLastName;
			private String  User;
			private boolean   isActive;

			public String getFirstName() {
				return FirstName;
			}
			public void setFirstName(String firstName) {
				FirstName = firstName;
			}
			public String getLastName() {
				return LastName;
			}
			public void setLastName(String lastName) {
				LastName = lastName;
			}
			public String getSecondLastName() {
				return SecondLastName;
			}
			public void setSecondLastName(String secondLastName) {
				SecondLastName = secondLastName;
			}
			public boolean isActive() {
				return isActive;
			}
			public void setActive(boolean isActive) {
				this.isActive = isActive;
			}
			public String getUser() {
				return User;
			}
			public void setUser(String user) {
				User = user;
			}
			
			 
			
			 

}


