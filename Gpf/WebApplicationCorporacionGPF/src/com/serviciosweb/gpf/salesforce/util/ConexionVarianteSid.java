package com.serviciosweb.gpf.salesforce.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import oracle.jdbc.driver.OracleDriver;

public class ConexionVarianteSid
{
  static
  {
    new Utilities();
  }
  
  public static Logger log = Logger.getLogger("SalesForceServices." + Utilities.class.getName());
  private Connection conn;
  
  public ConexionVarianteSid(String sidDataBase, String quienIngresa, String comoIngresa)
    throws NamingException, Exception
  {
    try
    {
      System.setProperty("oracle.jdbc.thinLogonCapability", "o3");
      System.setProperty("oracle.net.tns_admin", "/data/oracle");
      DriverManager.registerDriver(new OracleDriver());
      this.conn = DriverManager.getConnection("jdbc:oracle:thin:@" + sidDataBase, quienIngresa, comoIngresa);
    }
    catch (SQLException e)
    {
      log.log(Level.SEVERE, e.toString() + " FALLA COMEXION A SID: " + sidDataBase);
    }
  }
  
  public void cerrarConexion(ResultSet rs)
  {
    try
    {
      if (rs != null) {
        rs.close();
      }
    }
    catch (SQLException e1)
    {
      e1.printStackTrace();
    }
    if (this.conn != null) {
      try
      {
        if (!this.conn.isClosed()) {
          this.conn.close();
        }
      }
      catch (Exception e)
      {
        log.log(Level.SEVERE, e.toString());
      }
    }
    this.conn = null;
  }
  
  public void cerrarConexion()
  {
    if (this.conn != null) {
      try
      {
        if (!this.conn.isClosed()) {
          this.conn.close();
        }
      }
      catch (Exception e)
      {
        log.log(Level.SEVERE, e.toString());
      }
    }
    this.conn = null;
  }
  
  public void cerrarResultSet(ResultSet rs)
  {
    try
    {
      rs.close();
    }
    catch (SQLException e)
    {
      log.log(Level.SEVERE, e.toString());
    }
  }
  
  public Connection getConn()
  {
    return this.conn;
  }
  
  public Boolean isValid()
  {
    try
    {
      if (this.conn == null) {
        return Boolean.valueOf(false);
      }
      if (this.conn.isClosed()) {
        return Boolean.valueOf(false);
      }
      return Boolean.valueOf(true);
    }
    catch (SQLException e)
    {
      log.log(Level.SEVERE, e.toString());
    }
    return Boolean.valueOf(false);
  }
}
