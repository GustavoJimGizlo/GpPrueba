package com.corporaciongpf.adm.utilitario;


import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;


import org.apache.log4j.Logger;

//log4j.//logger;
import com.corporaciongpf.adm.excepcion.FileException;

public class DocumentUtil {
	public static final Logger log = Logger.getLogger(DocumentUtil.class);


	public static void crearArchivoDirectorio(String ruta, String url) throws IOException {
		
		log.error("Ingresa a funcion");

	    try (BufferedInputStream inputStream = new BufferedInputStream(new URL(url).openStream());
	    		  FileOutputStream fileOS = new FileOutputStream(ruta)) {
	    		    byte data[] = new byte[1024];
	    		    int byteContent;
	    		    while ((byteContent = inputStream.read(data, 0, 1024)) != -1) {
	    		        fileOS.write(data, 0, byteContent);
	    		    }
	    			log.error("Se creo el archivo correctamente");
	    		} catch (IOException e) {
	    			log.error(e);
	    		}	    

		}
	
	/**
	 * @param path
	 * @return
	 * @throws FileNotFoundException
	 */
	public static void createDirectory(String path) throws FileNotFoundException {
		StringBuffer pathname = new StringBuffer(path);

		if (!path.trim().endsWith("/")) {
			pathname.append("/");
		}
		// log.debug("Directorio URL: " + pathname.toString());
		File directory = new File(pathname.toString());
		// //log.debug(pathname.toString());
		if (!directory.exists()) {
			if (!directory.mkdirs()) {
				throw new FileNotFoundException();
			}
		}
	}

}
