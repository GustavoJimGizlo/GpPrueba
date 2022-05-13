package com.gpf.easypagos.bean;

import java.util.List;


public class ReportByUserSummaryResponseFinal{
			private String  success;
			private TotalFinal Totals;
			
			public String getSuccess() {
				return success;
			}
			public TotalFinal getTotals() {
				return Totals;
			}
			public void setTotals(TotalFinal totals) {
				Totals = totals;
			}
			public void setSuccess(String success) {
				this.success = success;
			}
}


