package com.gpf.easypagos.bean;

import java.io.Serializable;


public class ReportByUserSummaryRequest implements Serializable {
	
			private static final long serialVersionUID = 1L;
			private String PID;
			private String User;
			private String DateFrom;
			private String DateTo;
			
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
			public String getDateFrom() {
				return DateFrom;
			}
			public void setDateFrom(String dateFrom) {
				DateFrom = dateFrom;
			}
			public String getDateTo() {
				return DateTo;
			}
			public void setDateTo(String dateTo) {
				DateTo = dateTo;
			}

			
			
			
}


