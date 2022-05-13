/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.serviciosweb.gpf.facturacion.servicios;



/*
* ImageUtils.java
*
* Created on 20 de julio de 2005, 11:47
* Resizes jpeg image files on your file system.
* Uses the com.sun.image.codec.jpeg package shipped
* by Sun with Java 2 Standard Edition.
*
* @author Randy Belknap
* @revision Alejandro Sánchez Marcos
* se aprovecha la nueva clase ImageIO de 1.4
* y se añaden algunos métodos nuevos
*/


import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
//import org.apache.log4j.Logger;

/**

*/
public class ImageUtils {
//static Logger logger = Logger.getLogger(ImageUtils.class);
/*
* devuelve la lista de formatos disponibles a leer por ImageIO
* @return un array de strings con los mismos.
*/
public static String[] getAvailableFormats(){
return ImageIO.getReaderFormatNames();
}

/*
* devuelve una imagen (buffer) en función de la ruta de un archivo
* mejoras
* @param la ruta del archivo con su nombre
* @return BufferedImage la imagen en el buffer
*/
public static BufferedImage loadBufferedImage(String fileName) {
BufferedImage image = null;
try {
/*FileInputStream fis = new FileInputStream(fileName);JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(fis);bi = decoder.decodeAsBufferedImage();fis.close();*/
// MAS RAPIDO a partir de 1.4
image = ImageIO.read( new File( fileName ) );
}
catch (Exception e) {
  System.out.println("error al intentar leer la imágen");
return null;
}
return image;

}
/**
* comprueba que la imágen tiene un mínimo en pixels
* width y height tienen que tener un tamaño igual o mayor que el pasado
* como argumento
*
* @param w ancho mínimo
* @param h alto mínimo
* @return true o false
*
*/
public static boolean isGreaterThanMinSize(int w, int h, BufferedImage imgSrc){
	int nHeight = imgSrc.getHeight();
	int nWidth = imgSrc.getWidth();
	if (nHeight<h && nWidth<w) {
		return false;
	} else {
	   return true;
	}
}

/*
* calcula el factor de escala mínimo y en base a eso escala la imagen
* según el dicho factor.
* @param nMaxWidth minimo tamaño para el ancho
* @param nMaxHeight minimo tamaño para el alto
* @param imgSrc la imágen
*/
public static BufferedImage scaleToSize(int nMaxWidth, int nMaxHeight, BufferedImage imgSrc) {
    try{
        int nHeight = imgSrc.getHeight();
        int nWidth = imgSrc.getWidth();
        double scaleX = (double)nMaxWidth / (double)nWidth;
        double scaleY = (double)nMaxHeight / (double)nHeight;
        double fScale = Math.min(scaleX, scaleY);
        return scale(fScale, imgSrc);
    }catch(Exception e){
        e.printStackTrace();
    }
    return null;
}

/*
* escala una imagen en porcentaje.
* @param scale ejemplo: scale=0.6 (escala la imágen al 60%)
* @param srcImg una imagen BufferedImage
* @return un BufferedImage escalado
*/
public static BufferedImage scale(double scale, BufferedImage srcImg) {
if (scale == 1 ) {
return srcImg;
}
AffineTransformOp op = new AffineTransformOp
(AffineTransform.getScaleInstance(scale, scale), null);

return op.filter(srcImg, null);

}

public static void saveImageToDisk(BufferedImage bi, String str, String format) {
if (bi != null && str != null) {

// más rápido con ImageIO
try {
ImageIO.write( bi, format /* formato */, new File( str ) /* destino */ );
} catch (Exception e){}
}
}
/*
public static void main(String args[]) {
	//1714226188
    String imagen ="/data/imagenes/empleados/0913396578.jpg";
    //if(args.length != 3){usage();}
    //System.out.println(args[0]);
    BufferedImage bImg = loadBufferedImage(imagen);//args[0]);
    System.out.println(bImg);
    BufferedImage bImgEscaladaG = scaleToSize(131, 151, bImg);
    BufferedImage bImgEscaladaP = scaleToSize(131, 151, bImg);

    saveImageToDisk(bImgEscaladaG, "/data/imagenes/empleados/convertida1.jpg","JPEG");
    saveImageToDisk(bImgEscaladaP, "/data/imagenes/empleados/convertida2.jpg","JPEG");
    System.exit(0);
}
*/
public ImageUtils(){

}
public void procesar(String rutaImagen){
    BufferedImage bImg = loadBufferedImage(rutaImagen);//args[0]);
    BufferedImage bImgEscaladaG = scaleToSize(131, 151, bImg);
    if(bImgEscaladaG==null){
        return;
    }else{
        new File(rutaImagen).delete();
        saveImageToDisk(bImgEscaladaG, rutaImagen,"JPEG");
    }
}

public static void usage(){
System.out.println("usage: java ImageUtils archivo_original imagen_grande imagen_pequeña ");
System.exit(1);
}

}