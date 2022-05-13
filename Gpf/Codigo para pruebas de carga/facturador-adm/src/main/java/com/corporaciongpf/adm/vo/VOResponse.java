package com.corporaciongpf.adm.vo;

public class VOResponse {

	public VOResponse setResponse(VOResponse response,String code, String msg) {
		setCode( code) ;
		setMsg(msg) ;
		return response;
	}
	

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	private String  code;
	private String  msg;		
	
}