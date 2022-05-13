package com.serviciosweb.gpf.appmovil;

import java.io.File;
import java.security.Security;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import com.serviciosweb.gpf.appmovil.Commons; 
import javax.naming.directory.Attributes;
import javax.naming.directory.Attribute;

public class LdapADConnection {

	static String baseName = Commons.getParametroSistema("AD_BASENAME");
	static String certificate = Commons.getParametroSistema("AD_TRUSTSTORE");
	static String serverIP = Commons.getParametroSistema("AD_HOST");
	static String serverPort = Commons.getParametroSistema("AD_PORT_SSL");	

	// Obtiene un conexi√≥n al servidor de Active directory para el usuario/clave 
	@SuppressWarnings("unchecked")
	public static DirContext getConnection(String username, String password) {
		//configurarConexion();
		DirContext ldapContextTemp;
		baseName = Commons.getParametroSistema("AD_BASENAME");
		certificate = Commons.getParametroSistema("AD_TRUSTSTORE");
		serverIP = Commons.getParametroSistema("AD_HOST");
		serverPort = Commons.getParametroSistema("AD_PORT_SSL");
		try {
			serverIP = "172.20.200.23";
			Hashtable ldapEnv = new Hashtable(11);
			ldapEnv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			ldapEnv.put(Context.PROVIDER_URL, "ldap://172.20.200.23:389");//"ldap://" + serverIP + ":" + serverPort + "/");
			//ldapEnv.put(Context.SECURITY_AUTHENTICATION, "simple");
			ldapEnv.put(Context.SECURITY_PRINCIPAL, "cs_pruebasportal@gfybeca.int");//username);
			ldapEnv.put(Context.SECURITY_CREDENTIALS, "fybeca.15");//password);
			//ldapEnv.put(Context.SECURITY_PROTOCOL, "ssl");
			ldapContextTemp = new InitialDirContext(ldapEnv);
			return ldapContextTemp;
		}
		catch (Exception e) {
			System.out.println("Se produjo el siguiente error: " + e);
			e.printStackTrace();
			return null;
		}
	}

	// Configuraci√≥n  opciones generales 
	public static void configurarConexion() {
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		System.setProperty("javax.net.ssl.trustStore", certificate);
		System.setProperty("javax.net.ssl.trustStorePassword", "global2011");
		System.setProperty("javax.net.debug", "all");
	}	
	
	// Realiza una conexi√≥n al servidor para authenticar las credenciales
	public static String authenticateUser(String username, String currentPassword){
		try{
			DirContext ldapContextTemp;
			ldapContextTemp = getConnection(username+"@gfybeca.int", currentPassword);	
			if (ldapContextTemp == null){
				return "2";
			} else {
				return "0";	
			}
		} catch (Exception e) {
			System.out.println("Se produjo el siguiente error: " + e);
			e.printStackTrace();
			return "2";
		}

	}
	
	// Actualiza la contrase√±a del usuario
	@SuppressWarnings("unchecked")
	public static String updatePassword(String username, String currentPassword, String newPassword) throws NamingException {
		DirContext ldapContextTemp;
		try {
			
			//if (currentPassword == null){
				ldapContextTemp = getConnection(Commons.getParametroSistema("AD_USUARIO"), 
												Commons.getParametroSistema("AD_PASSWORD"));				
			/*}else {
				ldapContextTemp = getConnection(username + "@gfybeca.int", 
												currentPassword);
			}*/
			
			String returnedAtts[] ={"CN"};
            String searchFilter = "(sAMAccountName=" +  username +")";
            String resultado = null;
            SearchControls searchCtls = new SearchControls();
            searchCtls.setReturningAttributes(returnedAtts);
            searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);

            NamingEnumeration answer = ldapContextTemp.search(baseName, searchFilter, searchCtls);

            while (answer.hasMoreElements()){
                  SearchResult sr = (SearchResult)answer.next();
                  resultado = sr.getName();
            }            
          
			String quotedPassword = "\"" + newPassword + "\"";
			char unicodePwd[] = quotedPassword.toCharArray();
			byte pwdArray[] = new byte[unicodePwd.length * 2];
			for (int i=0; i<unicodePwd.length; i++) {
				pwdArray[i*2 + 1] = (byte) (unicodePwd[i] >>> 8);
				pwdArray[i*2 + 0] = (byte) (unicodePwd[i] & 0xff);
			}
			
			ModificationItem[] mods = new ModificationItem[1];
			mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("UnicodePwd", pwdArray));
			ldapContextTemp.modifyAttributes(resultado + "," + baseName, mods);
			
			return "0";
		}
		catch (Exception e) {
			System.out.println("Ocurri√≥ un error en el cambio de clave: " + e);
			return "99";
			//System.exit(-1);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static String forceChangePassword(String username) throws NamingException {
		DirContext ldapContextTemp;
		try {
			
			//if (currentPassword == null){
				ldapContextTemp = getConnection(Commons.getParametroSistema("AD_USUARIO"), 
												Commons.getParametroSistema("AD_PASSWORD"));				
			/*}else {
				ldapContextTemp = getConnection(username + "@gfybeca.int", 
												currentPassword);
			}*/
			
			String returnedAtts[] ={"CN"};
            String searchFilter = "(sAMAccountName=" +  username +")";
            String resultado = null;
            SearchControls searchCtls = new SearchControls();
            searchCtls.setReturningAttributes(returnedAtts);
            searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);

            NamingEnumeration answer = ldapContextTemp.search(baseName, searchFilter, searchCtls);

            while (answer.hasMoreElements()){
                  SearchResult sr = (SearchResult)answer.next();
                  resultado = sr.getName();
            }            
			ModificationItem[] mods = new ModificationItem[1];
			mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("pwdLastSet", "0"));
			ldapContextTemp.modifyAttributes(resultado + "," + baseName, mods);
			
			return "0";
		}
		catch (Exception e) {
			System.out.println("Ocurri√≥ un error asignando el atributo de cambio de clave en el proximo logon: " + e);
			return "99";
			//System.exit(-1);
		}
	}	
	
	@SuppressWarnings("unchecked")
	public static String unlockAccount(String username) throws NamingException {
		DirContext ldapContextTemp;
		try {
			
			ldapContextTemp = getConnection(Commons.getParametroSistema("AD_USUARIO"), 
											Commons.getParametroSistema("AD_PASSWORD"));				
			
			String returnedAtts[] ={"CN"};
            String searchFilter = "(sAMAccountName=" +  username +")";
            String resultado = null;
            SearchControls searchCtls = new SearchControls();
            searchCtls.setReturningAttributes(returnedAtts);
            searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);

            NamingEnumeration answer = ldapContextTemp.search(baseName, searchFilter, searchCtls);

            while (answer.hasMoreElements()){
                  SearchResult sr = (SearchResult)answer.next();
                  resultado = sr.getName();
            }            
          			
			ModificationItem[] mods = new ModificationItem[1];
			mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("lockoutTime", "0"));
			ldapContextTemp.modifyAttributes(resultado + "," + baseName, mods);
			
			return "0";
		}
		catch (Exception e) {
			System.out.println("Ocurri√≥ un error en el desbloqueo de cuenta: " + e);
			return "99";
			//System.exit(-1);
		}
	}	
	
	// Actualiza la contrase√±a del usuario
	@SuppressWarnings("unchecked")
	public static boolean searchUser(String username) throws NamingException {
		DirContext ldapContextTemp;
		boolean estado = false;
		try {
			ldapContextTemp = getConnection(Commons.getParametroSistema("AD_USUARIO"), 
											Commons.getParametroSistema("AD_PASSWORD"));
			
			String returnedAtts[] ={ "ipPhone", "mail", "name", "sAMAccountName"};//"CN"};
            String searchFilter = "(sAMAccountName=" +  username +")";
            SearchControls searchCtls = new SearchControls();
            searchCtls.setReturningAttributes(returnedAtts);
            searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			//NamingEnumeration answer = ldapContextTemp.search(baseName, searchFilter, searchCtls);
			NamingEnumeration<SearchResult> answer = ldapContextTemp.search(baseName, searchFilter, searchCtls);
			
            while (answer.hasMoreElements()){
                @SuppressWarnings("unused")
				String resultado;
            	estado = true;
            	SearchResult sr = (SearchResult)answer.next();
                Attributes attrs = sr.getAttributes();
                //Obtener el valor de los nodos
                Map<String, Object> amap = null;
                if (attrs != null) {
                    amap = new HashMap<String,Object>();
                    NamingEnumeration<?> ne = attrs.getAll();
                    while (ne.hasMore()) {
                        Attribute attr = (Attribute) ne.next();
                        if (attr.size() == 1) {
                            amap.put(attr.getID(), attr.get());
                        } else {
                            HashSet<String> s = new HashSet<String>();
                            NamingEnumeration n =  attr.getAll();
                            while (n.hasMoreElements()) {
                                s.add((String)n.nextElement());
                            }
                            amap.put(attr.getID(), s);
                        }
                    }
                    ne.close();
                }
                ldapContextTemp.close();
                
                resultado = sr.getName();
            }
            
			return estado;
		}
		catch (Exception e) {
			return estado;
		}
	}	
	
	public static ArrayList<LinkedHashMap> getNecesaryData(ResultSet rs, ResultSetMetaData rsmd) throws SQLException{
		//Por entregar rapido y por que las definiciones cambiaron muchas veces toco hacerlo asÌ.
	   ArrayList<LinkedHashMap> table= new ArrayList<LinkedHashMap>();
	   LinkedHashMap<String, String> propVal=new LinkedHashMap<String, String>();
	   String userName = null;
	   while(rs.next()){
		   userName = rs.getString("usuario");
		   for (int i = 1; i <= rsmd.getColumnCount(); i++){
			   propVal.put(rsmd.getColumnName(i)/*.replace(" ", "")*/, (rs.getString(i)==null?"N/A":rs.getString(i).trim()));
		   }
	   }
	   //----------------------------------------------------
	   
		DirContext ldapContextTemp;
		try {
			//ConexiÛn con ldap
			Hashtable ldapEnv = new Hashtable(11);
			ldapEnv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			ldapEnv.put(Context.PROVIDER_URL, "ldap://172.20.200.23:389");
			ldapEnv.put(Context.SECURITY_PRINCIPAL, "cs_pruebasportal@gfybeca.int");
			ldapEnv.put(Context.SECURITY_CREDENTIALS, "fybeca.15");
			ldapContextTemp = new InitialDirContext(ldapEnv);
		}
		catch (Exception e) {
			System.out.println("Se produjo el siguiente error: " + e);
			e.printStackTrace();
			return null;
		}
		
		try {
			//Obtener datos	
			String returnedAtts[] ={ "telephoneNumber", "ipPhone", "mail",  };//, "name", "sAMAccountName"};//"CN"};
	        String searchFilter = "(sAMAccountName=" +  userName +")";
	        SearchControls searchCtls = new SearchControls();
	        searchCtls.setReturningAttributes(returnedAtts);
	        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			NamingEnumeration<SearchResult> answer = ldapContextTemp.search(baseName, searchFilter, searchCtls);
			while (answer.hasMoreElements()){
            	SearchResult sr = (SearchResult)answer.next();
                Attributes attrs = sr.getAttributes();
                //Obtener el valor de los nodos
                //Map<String, Object> amap = null;
                if (attrs != null) {
                    NamingEnumeration<?> ne = attrs.getAll();
                    while (ne.hasMore()) {
                        Attribute attr = (Attribute) ne.next();
                        if (attr.size() == 1) {
                        	if("telephoneNumber".equals(attr.getID().toString())){
                        		propVal.put("N˙mero de telÈfono", attr.get().toString());
                        	}else if("ipPhone".equals(attr.getID().toString())){
                        		propVal.put("ExtensiÛn TelÈfono IP", attr.get().toString());
                        	}else if ("mail".equals(attr.getID().toString())){
                        		propVal.put("Correo Corporativo", attr.get().toString());
                        	}
                        }/* else {
                            HashSet<String> s = new HashSet<String>();
                            NamingEnumeration n =  attr.getAll();
                            while (n.hasMoreElements()) {
                                s.add((String)n.nextElement());
                            }
                            amap.put(attr.getID().toString().toUpperCase(), s.toString());
                        }*/
                    }
                    ne.close();
                }
                ldapContextTemp.close();
			}
			table.add(propVal);
			return table;
		} catch (Exception e) {
			return null;
		}
	} 

	public static String getMail(String username){
		DirContext ldapContextTemp;
		boolean estado = false;
		try {
			ldapContextTemp = getConnection(Commons.getParametroSistema("AD_USUARIO"), 
											Commons.getParametroSistema("AD_PASSWORD"));
			
			String returnedAtts[] ={"mail"};
            String searchFilter = "(sAMAccountName=" +  username +")";
            SearchControls searchCtls = new SearchControls();
            searchCtls.setReturningAttributes(returnedAtts);
            searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			NamingEnumeration<SearchResult> answer = ldapContextTemp.search(baseName, searchFilter, searchCtls);
			
            while (answer.hasMoreElements()){
                @SuppressWarnings("unused")
				String resultado;
            	estado = true;
            	SearchResult sr = (SearchResult)answer.next();
                Attributes attrs = sr.getAttributes();
                //Obtener el valor de los nodos
                Map<String, Object> amap = null;
                if (attrs != null) {
                    amap = new HashMap<String,Object>();
                    NamingEnumeration<?> ne = attrs.getAll();
                    while (ne.hasMore()) {
                        Attribute attr = (Attribute) ne.next();
                        if("mail".equals(attr.getID().toString())){
                        	return attr.get().toString();
                        }
                    }
                    ne.close();
                }
                ldapContextTemp.close();
                
                resultado = sr.getName();
            }
            return "";
		}
		catch (Exception e) {
			return "";
		}
	}
}
