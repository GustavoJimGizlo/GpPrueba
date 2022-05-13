package com.gpf.terminos;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.gpf.partnersGroupGPF.servicios.PartnersGroupGpfz;
import com.gpf.partnersGroupGPF.bean.AbTerminos;

@WebService()
public class terminos {

	@WebMethod()
	
	
	public String insertarab(AbTerminos abbp) throws Exception{ 
			// clase para inserter abterminos
		AbTerminos abb = new AbTerminos();
		PartnersGroupGpfz abi = new PartnersGroupGpfz();
		//abb.setCodigo(10000555);
		abb.setCodigo(abbp.getCodigo());
		abb.setCodigoEmpresa(abbp.getCodigoEmpresa());
		abb.setCodigoClub(abbp.getCodigoClub());
		abb.setaceptaterminos(abbp.getaceptaterminos());
		abb.setdebefirmar(abbp.getdebefirmar());
		abb.setfechaacepta(abbp.getfechaacepta());
		abb.setcodigofarmacia(abbp.getcodigofarmacia());
		abb.setmigrado(abbp.getmigrado());
		abb.setnumerointentos(abbp.getnumerointentos());
		abb.setcodigoasesor(abbp.getcodigoasesor());
		
		
	return abi.insertAbTerminos(abb);
			    
	}
	
public Boolean consultarab(Long pcodcli,Long pcodemp,Long pcodclu) throws Exception{ 
		
	Boolean consulta;
		PartnersGroupGpfz abi = new PartnersGroupGpfz();
		
		   consulta= abi.ConsultaAbTerminos(pcodcli, pcodemp, pcodclu);
		   return consulta;
		   	
			    
	}

}
