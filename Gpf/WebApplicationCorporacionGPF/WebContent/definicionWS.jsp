<%-- 
    Document   : index
    Created on : 09-sep-2011, 8:57:22
    Author     : mftellor
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Corporación GPF</title>
    </head>
    <body>
        <h3>Defición WebServices</h3>
        <table border="1">
            <tr><td colspan="2"><b>Pago Agil</b></td></tr>
            <tr><td width="100px;">Url</td><td>http://172.20.200.192/WebApplicationCorporacionGPF/resources/Fybeca/PagoAgil/<b>idEmpresa</b>/<b>idFarmacia</b>/<b>idPersona</b></td></tr>

            <tr><td colspan="2"><b>Facturas Prod</b></td></tr>
            <tr><td width="100px;">Url</td><td>http://172.20.200.192/WebApplicationCorporacionGPF/resources/Fybeca/FacturasProd/<b>codigoItem</b>/<b>codigoProducto</b>/<b>codigoPersona</b>/<b>identificacion</b>/<b>fechaDesde</b>/<b>fechaHasta</b>/<b>codigoFarmacia</b></td></tr>

            <tr><td colspan="2"><b>Grupo Bodega</b></td></tr>
            <tr><td width="100px;">Url</td><td>http://172.20.200.192/WebApplicationCorporacionGPF/resources/Fybeca/BodegaItem/<b>idEmpresa</b>/<b>idTem</b></td></tr>

            <tr><td colspan="2"><b>Paso estadisticas ventas entre 2 locales</b></td></tr>
            <tr><td width="100px;">Url</td><td>http://172.20.200.192/WebApplicationCorporacionGPF/resources/Fybeca/PasoEstadisticasLocales/<b>idFarmaciaOrigen</b>/<b>idFarmaciaDestino</b>/<b>tipoEstadisticas(M ó N ó T)</b></td></tr>

            <tr><td colspan="2"><b>Stock Por Farmacias</b></td></tr>
            <tr><td width="100px;">Url</td><td>http://172.20.200.192/WebApplicationCorporacionGPF/resources/Fybeca/StockItemsFarmacias/<b>item</b>/<b>empresas (1-2-3-etc)/farmacia</b></td></tr>

            <tr><td colspan="2"><b>Ventas Top Cliente</b></td></tr>
            <tr><td width="100px;">Url</td><td>http://172.20.200.192/WebApplicationCorporacionGPF/resources/Fybeca/VentasTop/<b>fechaInicio</b>/<b>fechaFinal</b>/<b>codigoPersona</b></td></tr>

            <tr><td colspan="2"><b>AlertasLocal</b></td></tr>
            <tr><td width="100px;">Url</td><td>http://172.20.200.192/WebApplicationCorporacionGPF/resources/Fybeca/AlertasLocal/<b>codigoAlerta</b>//<b>codigoLocal</b>/<b>codigoTipoAlerta</b>/<b>codigoItem</b>/<b>cantidad</b>/<b>tipoAbc</b></td></tr>

            <tr><td colspan="2"><b>Imagenes Prod->Local</b></td></tr>
            <tr><td width="100px;">Url</td><td>http://172.20.200.192/WebApplicationCorporacionGPF/resources/Fybeca/buscaImagen/<b>idImagen</b>/<b>ancho</b>/<b>alto</b></td></tr>

            <tr><td colspan="2"><b>Consulta Ventas PMC</b></td></tr>
            <tr><td width="100px;">Url</td><td>http://172.20.200.192/WebApplicationCorporacionGPF/resources/Fybeca/ConsultaPmc/<b>p_persona</b>/<b>p_item</b>/<b>p_tipo_id</b>/<b>p_cod_club</b>/<b>empresa</b></td></tr>

            <tr><td colspan="2"><b>ConsultaSaldo</b></td></tr>
            <tr><td width="100px;">Url</td><td>http://172.20.200.192/WebApplicationCorporacionGPF/resources/Locales/ConsultaSaldo/<b>vnCuenta</b></td></tr>

            <tr><td colspan="2"><b>ConsultaDetalle</b></td></tr>
            <tr><td width="100px;">Url</td><td>http://172.20.200.192/WebApplicationCorporacionGPF/resources/Locales/ConsultaDetalle/<b>pbodega</b>/<b>pdocumento</b></td></tr>

            <tr><td colspan="2"><b>ConsultaGrupo</b></td></tr>
            <tr><td width="100px;">Url</td><td>http://172.20.200.192/WebApplicationCorporacionGPF/resources/Locales/ConsultaGrupo/<b>pbodega</b>/<b>pdocumento</b></td></tr>

           <tr><td colspan="2"><b>ConsultaAcuPmc</b></td></tr>
           <tr><td width="100px;">Url</td><td>http://172.20.200.192/WebApplicationCorporacionGPF/resources/Locales/ConsultaAcuPmc/<b>p_persona</b>/<b>p_promo</b>/<b>p_item</b>/<b>empresa</b></td></tr>

           <tr><td colspan="2"><b>ConsultaPmcAcu</b></td></tr>
           <tr><td width="100px;">Url</td><td>http://172.20.200.192/WebApplicationCorporacionGPF/resources/Locales/ConsultaAcuPmc/<b>p_persona</b>/<b>p_promo</b>/<b>p_item</b>/<b>empresa</b></td></tr>

           <tr><td colspan="2"><b>ConsultaPmcNew</b></td></tr>
            <tr><td width="100px;">Url</td><td>http://172.20.200.192/WebApplicationCorporacionGPF/resources/Locales/ConsultaPmcNew/<b>p_persona</b>/<b>p_item</b>/<b>p_tipo_id</b>/<b>p_cod_club</b>/<b>empresa</b></td></tr>

           <tr><td colspan="2"><b>AutorizarCheque</b></td></tr>
            <tr><td width="100px;">Url</td><td>http://172.20.200.192/WebApplicationCorporacionGPF/resources/Locales/AutorizarCheque/<b>tipocedula</b>/<b>cedula</b>/<b>anyoNacimiento</b>/<b>banco</b>/<b>numerocuenta</b>/<b>numerocheque</b>/<b>monto</b>/<b>codestablecimiento</b>/<b>sexo</b>/<b>telefono</b></td></tr>

           <tr><td colspan="2"><b>ConfirmarAutorizacionCheque</b></td></tr>
           <tr><td width="100px;">Url</td><td>http://172.20.200.192/WebApplicationCorporacionGPF/resources/Locales/ConfirmarAutorizacionCheque/<b>cedula</b>/<b>banco</b>/<b>numerocuenta</b>/<b>numerocheque</b>/<b>resultado</b>/<b>codigoC</b></td></tr>

            <tr><td colspan="2"><b>ValidarCedula</b></td></tr>
            <tr><td width="100px;">Url</td><td>http://172.20.200.192/WebApplicationCorporacionGPF/resources/Locales/VerificarCedula/<b>cedula</b></td></tr>
            
            <tr><td colspan="2"><b>ConsultaCupoDisponible</b></td></tr>
            <tr><td width="100px;">Url</td><td>http://172.20.200.192/WebApplicationCorporacionGPF/resources/Locales/ConsultaCupoDisponible/<b>idPersona</b></td></tr>
            
            
            <tr><td colspan="2"><b>Imagen Gastos Personales</b></td></tr>
            <tr><td width="100px;">Url</td><td>http://172.20.200.192/WebApplicationCorporacionGPF/resources/GPFResource/getImagenWfArchivos/<b>idWfArchivos</b></td></tr>
            
            <tr><td colspan="2"><b>Formulario 107</b></td></tr>
            <tr><td width="100px;">Url</td><td>http://172.20.200.192/WebApplicationCorporacionGPF/resources/GPFResource/getImagenWfArchivosContador/<b>idWfArchivos</b></td></tr>

            <tr><td colspan="2"><b>Formulario 107</b></td></tr>
            <tr><td width="100px;">Url</td><td>http://172.20.200.192/WebApplicationCorporacionGPF/resources/GPFResource/getImagenWfArchivos107/<b>codigoEmpleadoEmpresaAnyo</b></td></tr>
            
            <tr><td colspan="2"><b>Saldo Cupo Vitalcard</b></td></tr>
            <tr><td width="100px;">Url</td><td>http://172.20.200.192/WebApplicationCorporacionGPF/resources/Locales/CupoVitalCard/<b>numeroTarjeta</b>/<b>valorConsumo</b>/<b>banderaConsumo</b></td></tr>
            
            <tr><td colspan="2"><b>Consulta Saldo</b></td></tr>
            <tr><td width="100px;">Url</td><td>http://172.20.200.192/WebApplicationCorporacionGPF/resources/Locales/ConsultaSaldo/<b>cedula</b>/<b>empresa</b></td></tr>


            <tr><td colspan="2"><b>Consulta Locales Cercanos</b></td></tr>
            <tr><td width="100px;">Url</td><td>http://172.20.200.192/WebApplicationCorporacionGPF/resources/Locales/ConsultaLocalesCercanos/<b>idLocal</b>/<b>item</b>/<b>empresa</b></td></tr>


            <tr><td colspan="2"><b>Detalles Acumulados</b></td></tr>
            <tr><td width="100px;">Url</td><td>http://172.20.200.192/WebApplicationCorporacionGPF/resources/Locales/DetallesAcumulados/<b>persona</b>/<b>promocion</b>/<b>item</b>/<b>empresa</b></td></tr>

            <tr><td colspan="2"><b>Club Persona Obtener</b></td></tr>
            <tr><td width="100px;">Url</td><td>http://172.20.200.192/WebApplicationCorporacionGPF/resources/Locales/ClubPersonaObtener/<b>persona</b>/<b>club</b></td></tr>

            <tr><td colspan="2"><b>Club Persona Grabar</b></td></tr>
            <tr><td width="100px;">Url</td><td>http://172.20.200.192/WebApplicationCorporacionGPF/resources/Locales/ClubPersonaGrabar/<b>persona</b>/<b>club</b></td></tr>


            <tr><td colspan="2"><b>Generar URl PagoAgil </b></td></tr>
            <tr><td width="100px;">Url</td><td>http://172.20.200.192/WebApplicationCorporacionGPF/resources/Locales/PagoAgilAutenticacion/<b>usuario</b></td></tr>


            <tr><td colspan="2"><b>Datos Centro de Costos </b></td></tr>
            <tr><td width="100px;">Url</td><td>http://172.20.200.192/WebApplicationCorporacionGPF/resources/GPFResource/datosCargosCentroCostosRest/<b>codigoCentroCostos</b></td></tr>

            <tr><td colspan="2"><b>Datos Cargos</b></td></tr>
            <tr><td width="100px;">Url</td><td>http://172.20.200.192/WebApplicationCorporacionGPF/resources/GPFResource/datosCargosCentroCostosRest/<b>codigoCargo</b></td></tr>


            <tr><td colspan="2"><b>Datos Completos Empleados</b></td></tr>
            <tr><td width="100px;">Url</td><td>http://172.20.200.192/WebApplicationCorporacionGPF/resources/GPFResource/datosCompletosEmpleado/<b>cedulaEmpleado</b></td></tr>


            <tr><td colspan="2"><b>Detalles Acumulados</b></td></tr>
            <tr><td width="100px;">Url</td><td>http://172.20.200.192/WebApplicationCorporacionGPF/resources/Locales/DetallesAcumulados/<b>persona</b>/<b>promocion</b>/<b>item</b>/<b>empresa</b></td></tr>

            <tr><td colspan="2"><b>Puntomatico</b></td></tr>
            <tr><td width="100px;">Url</td><td>http://172.20.200.192/WebApplicationCorporacionGPF/resources/Locales/Puntomatico/<b>fechaTransaccion</b>/<b>codigoLocal</b>/<b>codigoUsuario</b>/<b>valor</b>/<b>tipo</b>/<b>idTransaccion</b></td></tr>



        </table>

        <h3>Defición WebServices consumo a Pfizer de Locales</h3>
        <table border="1">
            <tr><td colspan="2"><b>Procesar Compra</b></td></tr>
            <tr><td width="100px;">Url</td><td>http://172.20.200.192/WebApplicationCorporacionGPF/resources/Locales/ProcesarCompra/<b>programaIntld</b>/<b>cadenaIntld</b>/<b>sucursalIntld</b>/<b>terminalIntld</b>/<b>tarjeta</b>/<b>usuarioStrId</b>/<b>usuarioCapturaStrId</b>/<b>itemsCantidad</b> <b>itemsCantidad</b>=(codigo barras, cantidad)(34523456234534,2)(667897654123532373568,9)</td></tr>

            <tr><td colspan="2"><b>Cerrar Compra</b></td></tr>
            <tr><td width="100px;">Url</td><td>http://172.20.200.192/WebApplicationCorporacionGPF/resources/Locales/CerrarCompra/<b>cotizacionId</b>/<b>tarjeta</b></td></tr>

            <tr><td colspan="2"><b>Cancelar Compra</b></td></tr>
            <tr><td width="100px;">Url</td><td>http://172.20.200.192/WebApplicationCorporacionGPF/resources/Locales/CancelarCompra/<b>cotizacionId</b>/<b>tarjeta</b></td></tr>

            <tr><td colspan="2"><b>Consultar Devolucion</b></td></tr>
            <tr><td width="100px;">Url</td><td>http://172.20.200.192/WebApplicationCorporacionGPF/resources/Locales/ConsultarDevolucion/<b>programaIntld</b>/<b>cadenaIntld</b>/<b>sucursalIntld</b>/<b>terminalIntld</b>/<b>tarjeta</b>/<b>usuarioStrId</b>/<b>cotizacionId</b>/<b>referTransactionDevIntId</b></td></tr>

            <tr><td colspan="2"><b>Devolver Compra</b></td></tr>
            <tr><td width="100px;">Url</td><td>http://172.20.200.192/WebApplicationCorporacionGPF/resources/Locales/DevolverCompra/<b>programaIntld</b>/<b>cadenaIntld</b>/<b>sucursalIntld</b>/<b>terminalIntld</b>/<b>tarjeta</b>/<b>usuarioStrId</b>/<b>cotizacionId</b>/<b>referTransactionDevIntId</b>/<b>itemsCantidad</b>   <b>itemsCantidad</b>=(codigo barras, cantidad,bonificacion)(34523456234534,2,1)(667897654123532373568,9,3)</td></tr>

            <tr><td colspan="2"><b>Consulta Tarjeta</b></td></tr>
            <tr><td width="100px;">Url</td><td>http://172.20.200.192/WebApplicationCorporacionGPF/resources/Locales/ConsultaTarjeta/<b>programaIntId</b>/<b>cadenaIntId</b>/<b>sucursalIntId</b>/<b>terminalIntId</b>/<b>usuarioStrId</b>/<b>tarjeta</b>/<b>cedula</b></td></tr>

            <tr><td colspan="2"><b>Consulta Transacciones</b></td></tr>
            <tr><td width="100px;">Url</td><td>http://172.20.200.192/WebApplicationCorporacionGPF/resources/Locales/ConsultaTransacciones/<b>programaID</b>/<b>cadenaid</b>/<b>key</b>/<b>fechaInicial</b>/<b>fechaFinal</b></td></tr>

        </table>
        <h3>Defición WebServices Postgres</h3>
        <table border="1">
            <tr><td colspan="2"><b>Alertas local</b></td></tr>
            <tr><td width="100px;">Url</td><td>http://172.20.200.192/WebApplicationCorporacionGPF/resources/Fybeca/AlertasLocal/<b>codigoAlerta</b>/<b>codigoLocal</b>/<b>codigoTipoAlerta</b>/<b>codigoItem</b>/<b>cantidad</b>/<b>tipoAbc</b></td></tr>
            <tr><td width="100px;">Url</td><td>http://172.20.200.192/WebApplicationCorporacionGPF/resources/Fybeca/PedidoManual/<b>codigoLocal</b>/<b>codigoPedido</b></td></tr>
        </table>

        <h3>Defición WebServices consumo a Receta ABF de Locales</h3>
        <table border="1">
            <tr><td colspan="2"><b>Obtener Receta</b></td></tr>
            <tr><td width="100px;">Url</td><td>http://172.20.200.192/WebApplicationCorporacionGPF/resources/RecetaAbf/getRecetaAbf/<b>identificacionPaciente</b></td></tr>
            <tr><td colspan="2"><b>Marcar Receta como procesada</b></td></tr>
            <tr><td width="100px;">Url</td><td>http://172.20.200.192/WebApplicationCorporacionGPF/resources/RecetaAbf/setEstadoRecetaAbf/<b>recetaId</b></td></tr>
            
        </table>
    </body>
</html>
