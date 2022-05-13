package modelo;

public class persona {
	
	String id;
	String nombre;
	String cedula;
	String correo;
	
	public persona() {
	}
	
	
	public persona(String id, String nombre, String cedula, String correo) {
		this.id = id;
		this.nombre = nombre;
		this.cedula = cedula;
		this.correo = correo;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getCedula() {
		return cedula;
	}


	public void setCedula(String cedula) {
		this.cedula = cedula;
	}


	public String getCorreo() {
		return correo;
	}


	public void setCorreo(String correo) {
		this.correo = correo;
	}
	
	
	
	
	
	
	

}
