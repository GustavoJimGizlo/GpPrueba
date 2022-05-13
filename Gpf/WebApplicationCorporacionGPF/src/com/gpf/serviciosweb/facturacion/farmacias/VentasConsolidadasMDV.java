/**
 * @date 17/12/2013
 * @user mftellor
 * @project_name WebApplicationCorporacionGPF
 * @company Corporacion GPF
 */
package com.gpf.serviciosweb.facturacion.farmacias;


import java.util.ArrayList;
import java.util.List;

import com.gpf.postg.pedidos.util.ConexionVarianteSid;
import com.gpf.postg.pedidos.util.ObtenerNuevaConexion;
import com.gpf.postg.pedidos.util.ResultSetMapper;
import com.serviciosweb.gpf.facturacion.farmacias.bean.GridCeldasRespuestaBean;
import com.serviciosweb.gpf.facturacion.farmacias.bean.GridRespuestaBean;
import com.serviciosweb.gpf.facturacion.farmacias.bean.VentasConsolidadasMDVBean;

import flexjson.JSONSerializer;

/**
 * @author mftellor
 *
 */
public class VentasConsolidadasMDV extends ObtenerNuevaConexion{

	public VentasConsolidadasMDV() {
		super(VentasConsolidadasMDV.class);
	}

	public String getVentasConsolidadas(){
		Double totalVitalCard=new Double(0);
		Double totalPerfumeria=new Double(0);
		Double totalEspecialidad=new Double(0);
		Double totalAyudaComunitaria=new Double(0);
		Double totalServicios=new Double(0);
		Double totalGlobal=new Double(0);
		String resultadoHtml="<title>MDV 12/2013</title><table border='1'><th>FARMACIA</th><th>F. ULTIMO MOV.</th><th>V. VITALCARD</th>" +
				"<th>V. PERFUMERIA</th><th>V. ESPECIALIDAD</th><th>AYUDA COMUNITARIA</th><th>V. SERVICIOS</th><th>TOTAL</th>";
		String queryBusqueda="select b.nombre, a.* from fa_ventas_consolidadas a, ad_farmacias b where A.FARMACIA=b.codigo  and  fecha=trunc(sysdate) order by b.ciudad,b.nombre";
		ResultSetMapper<VentasConsolidadasMDVBean> resultSetMapper=new ResultSetMapper<VentasConsolidadasMDVBean>();		
		ConexionVarianteSid conexion = obtenerNuevaConexionVarianteSidMaster("1");		
		List<VentasConsolidadasMDVBean> listDatosCentroCostos=resultSetMapper.mapRersultSetToObject(conexion.consulta(queryBusqueda), VentasConsolidadasMDVBean.class);
		for(VentasConsolidadasMDVBean mdv:listDatosCentroCostos){
			resultadoHtml+="<tr><td>"+mdv.getFarmacia()+"</td><td>"+mdv.getFechaUltimoMvimiento()+"</td><td  align='right'>"+mdv.getValorVitalCard()+"</td><td align='right'>"+mdv.getValorPerfumeria()
					+"</td><td align='right'>"+mdv.getValorEspecialidad()+"</td><td align='right'>"+mdv.getValorAyudaComunitaria()+"</td><td align='right'>"+mdv.getValorServicios()+"</td>" +
							"<td align='right'>"+mdv.getTotal()+"</td></tr>";
			totalVitalCard+=mdv.getValorVitalCard();
			totalPerfumeria+=mdv.getValorPerfumeria();
			totalEspecialidad+=mdv.getValorEspecialidad();
			totalAyudaComunitaria+=mdv.getValorAyudaComunitaria();
			totalServicios+=mdv.getValorServicios();			
		}
		totalGlobal+=(totalPerfumeria+totalEspecialidad+totalServicios);
		conexion.cerrarConexion();
		resultadoHtml+="<tr><td>Total:</td><td>-</td><td align='right'>"+toDouble(totalVitalCard)+"</td><td align='right'>"+toDouble(totalPerfumeria)+"</td><td align='right'>"+toDouble(totalEspecialidad)+"</td><td align='right'>"+toDouble(totalAyudaComunitaria)+"</td><td align='right'>"+toDouble(totalServicios)+"</td><td align='right'>"+toDouble(totalGlobal)+"</td></tr>";
		resultadoHtml+="</table>";
		return resultadoHtml;
	}
	public String getVentasConsolidadas(String fechaDesde, String fechaHasta){
		List<GridCeldasRespuestaBean> listaVentasConsolidadasMDVBean=new ArrayList<GridCeldasRespuestaBean>();
		Double totalVitalCard=new Double(0);
		Double totalPerfumeria=new Double(0);
		Double totalEspecialidad=new Double(0);
		Double totalAyudaComunitaria=new Double(0);
		Double totalServicios=new Double(0);
		Double totalGlobal=new Double(0);
		//Integer totaltrx=new Integer(0) ;
		String resultadoHtml="";
		String queryBusqueda="select b.nombre, a.* from fa_ventas_consolidadas a, ad_farmacias b where A.FARMACIA=b.codigo  " +
				" and ( fecha>=to_date('"+fechaDesde+"','YYYY-MM-DD') and  fecha<=to_date('"+fechaHasta+"','YYYY-MM-DD')) order by b.ciudad,b.nombre";
		ResultSetMapper<VentasConsolidadasMDVBean> resultSetMapper=new ResultSetMapper<VentasConsolidadasMDVBean>();		
		ConexionVarianteSid conexion = obtenerNuevaConexionVarianteSidMaster("1");		
		List<VentasConsolidadasMDVBean> listDatosCentroCostos=resultSetMapper.mapRersultSetToObject(conexion.consulta(queryBusqueda), VentasConsolidadasMDVBean.class);
		Long id=0L;
		for(VentasConsolidadasMDVBean mdv:listDatosCentroCostos){
			List<String> fila=new ArrayList<String>();
			fila.add(mdv.getFarmacia());
			fila.add(mdv.getFechaUltimoMvimiento());
			fila.add(mdv.getValorVitalCard().toString());
			fila.add(mdv.getValorPerfumeria().toString());
			fila.add(mdv.getValorEspecialidad().toString());
			fila.add(mdv.getValorAyudaComunitaria().toString());
			fila.add(mdv.getValorServicios().toString());
			//fila.add(mdv.getTotalTransacciones().toString());
			fila.add(mdv.getTotal().toString());
			listaVentasConsolidadasMDVBean.add(new GridCeldasRespuestaBean(id, fila));

			totalVitalCard+=mdv.getValorVitalCard();
			totalPerfumeria+=mdv.getValorPerfumeria();
			totalEspecialidad+=mdv.getValorEspecialidad();
			totalAyudaComunitaria+=mdv.getValorAyudaComunitaria();
			totalServicios+=mdv.getValorServicios();		
			//totaltrx+=mdv.getTotalTransacciones();
			
			id++;
		}
		totalGlobal+=(totalPerfumeria+totalEspecialidad+totalServicios);
		
		List<String> fila=new ArrayList<String>();
		fila.add("TOTAL:");
		fila.add("");
		fila.add(toDouble(totalVitalCard).toString());
		fila.add(toDouble(totalPerfumeria).toString());
		fila.add(toDouble(totalEspecialidad).toString());
		fila.add(toDouble(totalAyudaComunitaria).toString());
		fila.add(toDouble(totalServicios).toString());
		fila.add(toDouble(totalGlobal).toString());
		//fila.add(totaltrx.toString());
		fila.add(toDouble(totalGlobal).toString());
		listaVentasConsolidadasMDVBean.add(new GridCeldasRespuestaBean(1000L, fila));
		
		
		conexion.cerrarConexion();
		
		GridRespuestaBean gridRespuesta = new GridRespuestaBean(1L, 1L, id, listaVentasConsolidadasMDVBean);
		
		JSONSerializer serializador = new JSONSerializer();	
		resultadoHtml = serializador.exclude("*.class").include("rows.cell").	serialize(gridRespuesta);
		return resultadoHtml;
	}
	
	private Double toDouble(Double valor){
		return Math.floor(valor * 100) / 100;
	}
}
