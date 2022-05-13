package modelo;
import java.sql.*;
import java.util.*;
import modelo.conexion;
public class personadao {
  PreparedStatement ps;
  ResultSet rs;
  public List listar()
  {
	    List<persona>listax=new ArrayList();
	    String sql ="select * from ab_terminos";
	    conexion con = new conexion();
		con.conectar();
	 try {
		ps= con.getConexion().prepareStatement(sql);
		rs=ps.executeQuery();
		while(rs.next())
  {
	 persona per=new persona();
	 per.setId(rs.getString(1));
	 per.setNombre(rs.getString(2));
	 per.setCedula(rs.getString(3));
	 per.setCorreo(rs.getString(4));
	 listax.add(per); 
  }
  	 }catch (Exception e)
	 {}
	 return listax; 
  }
  public int agregar(persona per) {
	  int r=0;
	  String sql ="insert into ab_terminos (cod_persona,nombre,cedula,correo) values (?,?,?,?)";
	        conexion con = new conexion();
			con.conectar();
	  try {
		  ps=con.getConexion().prepareStatement(sql);
	      ps.setString(1, per.getId());
	      ps.setString(2, per.getNombre());
	      ps.setString(3, per.getCedula());
	      ps.setString(4, per.getCorreo());
		  r=ps.executeUpdate();
		   if(r==1) {
		   r=1;}
		   else {
		   r=0;}
		  
	  }catch (Exception e)
	  {}
	  return r;
	  
  }
    
  public persona listarid(String vid) {
	  String sql ="select * from ab_terminos where cod_persona = " + vid;
	  persona per = new persona();
      conexion con = new conexion();
			con.conectar();
	  try {
	    ps=con.getConexion().prepareStatement(sql);
		rs=ps.executeQuery();  
		  while(rs.next())
		  {
			 per.setId(rs.getString(1));
			 per.setNombre(rs.getString(2));
			 per.setCedula(rs.getString(3));
			 per.setCorreo(rs.getString(4));
		  }
		  
	  }catch (Exception e)
	  {}
	  return per;
	  
  }
  
  
  public int actualizar(persona pp) {
	  String sql ="update persona set nombre=? cedula=? correo? where cod_persona=?";
	  int r=0;
	  persona per = new persona();
      conexion con = new conexion();
			con.conectar();
	  try {
	          ps=con.getConexion().prepareStatement(sql);
		      ps.setString(1, pp.getNombre());
		      ps.setString(2, pp.getCedula());
		      ps.setString(3, pp.getCorreo());
		      ps.setString(4, pp.getId());
			  r=ps.executeUpdate();
			   if(r==1) {
				   r=1;}
				   else {
				   r=0;}
	  }catch (Exception e)
	  {}
	  return r;
  }
}// fin clase

