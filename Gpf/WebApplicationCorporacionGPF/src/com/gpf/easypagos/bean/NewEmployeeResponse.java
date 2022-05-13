package com.gpf.easypagos.bean;


public class NewEmployeeResponse {


	private Boolean success;
	private Integer messagecode=0;
	private String message ="";

	public NewEmployeeResponse() {
	}

	public NewEmployeeResponse(Boolean success, Integer messagecode,String message) {
		super();
		this.success = success;
		this.messagecode = messagecode;
		this.message = message;
	}
	
    @Override
    public String toString() {
        return "NewEmployeeResponse [Success=" + success + ", MessageCode=" + messagecode + ", Message=" + message+ "]";
    }


	public Integer getMessagecode() {
		return messagecode;
	}

	public void setMessagecode(Integer messagecode) {
		this.messagecode = messagecode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}
	
	
	
	
	 
    
}
