package com.serviciosweb.gpf.facturacion.servicios;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.httpclient.methods.RequestEntity;
public class FileRequestEntity implements RequestEntity {
  
      final File file;
      final String contentType;
      
      public FileRequestEntity(final File file, final String contentType) {
          super();
          if (file == null) {
              throw new IllegalArgumentException("File may not be null");
          }
          this.file = file;
          this.contentType = contentType;
      }
      public long getContentLength() {
          return this.file.length();
      }
 
     public String getContentType() {
          return this.contentType;
      }
  
      public boolean isRepeatable() {
          return true;
      }
  
      public void writeRequest(final OutputStream out) throws IOException {
          byte[] tmp = new byte[4096];
          int i = 0;
          InputStream instream = new FileInputStream(this.file);
          try {
              while ((i = instream.read(tmp)) >= 0) {
                  out.write(tmp, 0, i);
              }        
          } finally {
              instream.close();
          }
      }    
      
  }