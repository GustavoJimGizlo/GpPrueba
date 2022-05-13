package com.gpf.easypagos.bean;

import java.util.List;


public class ReportByUserSummaryResponse{
			private String  success;
			private List<Total> Totals;
			public String getSuccess() {
				return success;
			}
			public void setSuccess(String success) {
				this.success = success;
			}
			public List<Total> getTotals() {
				return Totals;
			}
			public void setTotals(List<Total> totals) {
				Totals = totals;
			}

}


