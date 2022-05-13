/**
 * @date 13/01/2014
 * @user mftellor
 * @project_name WebApplicationCorporacionGPF
 * @company Corporacion GPF
 */
package com.gpf.serviciosweb.facturacion.farmacias;

import java.util.List;

import com.gpf.postg.pedidos.util.Constantes;
import com.gpf.postg.pedidos.util.GetDatosGenerica;
import com.serviciosweb.gpf.facturacion.farmacias.bean.InformacionPadronBean;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

/**
 * @author mftellor
 *
 */
public class InformacionPadron extends GetDatosGenerica<InformacionPadronBean>{

	public String resultadoXml="NO EXISTEN DATOS PARA ESTA CONSULTA";
	
	public static InformacionPadron getInformacionPadron(String cedula){
		return new InformacionPadron(cedula);
	}
	public InformacionPadron(String cedula) {
		super(InformacionPadronBean.class);
		XStream xstream = new XStream(new StaxDriver());
		String queryBusqueda="select wcedula,wdigito,wnombres,wnompro,wnomcan,wnompar,wnomzon,wnomrec,wdirrec,wjunta, "+
				" decode(wsexo,'F','MUJERES','M','HOMBRES',null) wsexo,wdesjrv,wjunjrv, "+
				" decode(wsexjrv,'F','MUJERES','M','HOMBRES',null) wsexjrv,wnrejrv,wdrejrv, "+
				" wcedula || wdigito identificacion,WNOMCIR, WCODREC from   fa_padron_electoral where  wcedula = substr('"+cedula+"',1,9)";
		List<InformacionPadronBean> listaInformacionPadronBean=getDatosOracle("1",queryBusqueda);
		
        xstream.alias("DatosPadron", InformacionPadronBean.class);
        if(listaInformacionPadronBean!=null)
        	resultadoXml=xstream.toXML(listaInformacionPadronBean.get(0));
        else
        	resultadoXml=null;	
	}
	
	public String getResultadoXml() {
		if(resultadoXml!=null)
			return  Constantes.respuestaXmlObjeto("OK",resultadoXml) ;
		else
			return  Constantes.respuestaXmlObjeto("ERROR","NO EXISTEN DATOS PARA ESTA CONSULTA") ;
	}	
}
