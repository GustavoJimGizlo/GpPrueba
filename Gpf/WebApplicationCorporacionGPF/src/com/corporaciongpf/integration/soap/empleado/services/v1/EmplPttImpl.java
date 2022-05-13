package com.corporaciongpf.integration.soap.empleado.services.v1;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.Action;

import com.corporaciongpf.integration.soap.empleado.consultas.Consultas;

@WebService(name = "empl_ptt", targetNamespace = "http://www.corporaciongpf.com/integration/empleado/services/v1", serviceName = "EmpleadoService", portName = "empl_ptt")
public class EmplPttImpl implements EmplPtt {
    public EmplPttImpl() {
    }

    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    @Action(input = "query", output = "http://www.corporaciongpf.com/integration/empleado/services/v1/empl_ptt/queryResponse")
    @WebMethod(action = "query")
    @WebResult(name = "EmpleadoResponse", targetNamespace = "http://www.corporaciongpf.com/integration/empleado/services/v1", partName = "EmpleadoResponse")
    public EmpleadoResponse query(@WebParam(name = "EmpleadoRequest", partName = "EmpleadoRequest", targetNamespace = "http://www.corporaciongpf.com/integration/empleado/services/v1")
        EmpleadoRequest EmpleadoRequest) {
    	
    	EmpleadoResponse empResponse = new EmpleadoResponse();
    	Consultas consulta = new Consultas();
    	String estadoEmpleado = EmpleadoRequest.getEstadoEmpleado().getEstado();
        
        
       if(estadoEmpleado.equals("R")) {
        	empResponse.setEmpleados(consulta.getEmpleadosRetirados());
        }else if(estadoEmpleado.equals("A")) {
        	empResponse.setEmpleados(consulta.getEmpleadosActivos());;
        }
 
        return empResponse;
    }
}
