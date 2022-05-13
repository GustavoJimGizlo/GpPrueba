package com.serviciosweb.gpf.appmovil;

import java.security.Security;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import com.serviciosweb.gpf.appmovil.Commons;

public class LdapOPENLDAPConnection {

	static String baseName = Commons.getParametroSistema("OPENLDAP_BASENAME");
	static String serverIP = Commons.getParametroSistema("OPENLDAP_HOST");
	static String serverPort = Commons.getParametroSistema("OPENLDAP_PORT");

	// Obtiene un conexión al servidor de Active directory para el usuario/clave 
	public static DirContext getConnection(String username, String password) {
		configurarConexion();
		DirContext ldapContextTemp;
		baseName = Commons.getParametroSistema("OPENLDAP_BASENAME");
		serverIP = Commons.getParametroSistema("OPENLDAP_HOST");
		try {
            Properties ldapEnv = new Properties();
            
            ldapEnv.put( Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
            ldapEnv.put( Context.PROVIDER_URL, "ldap://" + serverIP + ":" + serverPort);
            ldapEnv.put( Context.SECURITY_PRINCIPAL, username);
            ldapEnv.put( Context.SECURITY_CREDENTIALS, password);
            ldapContextTemp = new InitialDirContext(ldapEnv);
            return ldapContextTemp;
		}
		catch (Exception e) {
			System.out.println("Se produjo el siguiente error: " + e);
			e.printStackTrace();
			return null;
		}
	}

	// Configuración  opciones generales 
	public static void configurarConexion() {
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		//System.setProperty("javax.net.ssl.trustStore", certificate);
		System.setProperty("javax.net.debug", "all");
	}	
	
	// Realiza una conexión al servidor para authenticar las credenciales
	public static String authenticateUser(String username, String currentPassword){
		try{
			DirContext ldapContextTemp;
			ldapContextTemp = getConnection("uid="+username+",ou=Usuarios,dc=fybeca,dc=com", currentPassword);	
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
	
	// Identificar si el usuarios está creado en Active Directory o en OPENLDAP
	@SuppressWarnings("unchecked")
	public static String identificarLDAP(String username) throws NamingException {
		DirContext ldapContextTemp;
		try {
			//ldapContextTemp = getConnection("cn=Manager,dc=fybeca,dc=com", "fybaba99");
			ldapContextTemp = getConnection(Commons.getParametroSistema("OPENLDAP_USUARIO"),
											Commons.getParametroSistema("OPENLDAP_PASSWORD"));
			
			String returnedAtts[] ={"employeeType"};
            String searchFilter = "(uid=" +  username +")";
            String resultado = null;
            SearchControls searchCtls = new SearchControls();
            searchCtls.setReturningAttributes(returnedAtts);
            searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);

            NamingEnumeration answer = ldapContextTemp.search(baseName, searchFilter, searchCtls);

            while (answer.hasMoreElements()){
                  SearchResult sr = (SearchResult)answer.next();
                  Attributes attrs = sr.getAttributes();
                  if (attrs != null){
                	  Attribute attr = attrs.get("employeeType");
                	  resultado = attr.get().toString();                	  
                  }
            }            
			
			return resultado;
		}
		catch (Exception e) {
			System.out.println("Ocurrió un error en el cambio de clave: " + e);
			return "99";
			//System.exit(-1);
		}
	}
	
	// Identificar si el usuarios está creado en Active Directory o en OPENLDAP
	@SuppressWarnings("unchecked")
	public static String getMail(String username) throws NamingException {
		DirContext ldapContextTemp;
		try {
			//ldapContextTemp = getConnection("cn=Manager,dc=fybeca,dc=com", "fybaba99");
			ldapContextTemp = getConnection(Commons.getParametroSistema("OPENLDAP_USUARIO"),
											Commons.getParametroSistema("OPENLDAP_PASSWORD"));
			
			String returnedAtts[] ={"mail"};
            String searchFilter = "(uid=" +  username +")";
            String resultado = null;
            SearchControls searchCtls = new SearchControls();
            searchCtls.setReturningAttributes(returnedAtts);
            searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);

            NamingEnumeration answer = ldapContextTemp.search(baseName, searchFilter, searchCtls);

            while (answer.hasMoreElements()){
                  SearchResult sr = (SearchResult)answer.next();
                  Attributes attrs = sr.getAttributes();
                  if (attrs != null){
                	  Attribute attr = attrs.get("mail");
                	  resultado = attr.get().toString();                	  
                  }
            }            
			
			return resultado;
		}
		catch (Exception e) {
			System.out.println("Ocurrió un error en el cambio de clave: " + e);
			return "99";
			//System.exit(-1);
		}
	}	
	
	// Actualiza la contraseña del usuario
	@SuppressWarnings("unchecked")
	public static String updatePassword(String username, String currentPassword, String newPassword) throws NamingException {
		DirContext ldapContextTemp;
		try {
			if (currentPassword == null){
				ldapContextTemp = getConnection(Commons.getParametroSistema("OPENLDAP_USUARIO"),
												Commons.getParametroSistema("OPENLDAP_PASSWORD"));
			}else {
				ldapContextTemp = getConnection("uid="+username+",ou=Usuarios,dc=fybeca,dc=com", currentPassword);	
			}
			
			String returnedAtts[] ={"CN"};
            String searchFilter = "(uid=" +  username +")";
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
 			mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("userPassword", newPassword));
			ldapContextTemp.modifyAttributes(resultado + "," + baseName, mods);
			
			return "0";
		}
		catch (Exception e) {
			System.out.println("Ocurrió un error en el cambio de clave: " + e);
			return "99";
			//System.exit(-1);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static String unlockAccount(String username) throws NamingException {
		DirContext ldapContextTemp;
		try {
			ldapContextTemp = getConnection(Commons.getParametroSistema("OPENLDAP_USUARIO"),
											Commons.getParametroSistema("OPENLDAP_PASSWORD"));
			
			String returnedAtts[] ={"userPassword"};
            String searchFilter = "(uid=" +  username +")";
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
 			/*mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("userPassword", "tempPassword2010"));
			ldapContextTemp.modifyAttributes("uid=" +  username + "," + baseName, mods);*/
			
 			mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("userPassword", resultado));
			ldapContextTemp.modifyAttributes("uid=" +  username + "," + baseName, mods);			
			
			return "0";
		}
		catch (Exception e) {
			System.out.println("Ocurrió un error en el desbloque del usuario: " + e);
			return "99";
			//System.exit(-1);
		}
	}	
	
	@SuppressWarnings("unchecked")
	public static String forceChangePassword(String username) throws NamingException {
		DirContext ldapContextTemp;
		try {
			ldapContextTemp = getConnection(Commons.getParametroSistema("OPENLDAP_USUARIO"),
											Commons.getParametroSistema("OPENLDAP_PASSWORD"));
			
			String returnedAtts[] ={"userPassword"};
            String searchFilter = "(uid=" +  username +")";
            @SuppressWarnings("unused")
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
 			mods[0] = new ModificationItem(DirContext.REMOVE_ATTRIBUTE, new BasicAttribute("pwdReset"));
			ldapContextTemp.modifyAttributes("uid=" +  username + "," + baseName, mods);			
			
			return "0";
		}
		catch (Exception e) {
			System.out.println("Ocurrió un error asignando el atributo de cambio de clave en el proximo logon: " + e);
			return "99";
			//System.exit(-1);
		}
	}	
}
