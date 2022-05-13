--------------------------------------------------------
-- Archivo creado  - lunes-febrero-14-2022   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table FA_FACTURAS
--------------------------------------------------------

  CREATE TABLE "FA_FACTURAS" 
  ("FARMACIA" NUMBER(12,0), 
  "DOCUMENTO_VENTA" NUMBER(10,0), 
  "FECHA" DATE, 
  "NUMERO_SRI" VARCHAR2(30), 
  "COSTO_TOTAL_FACTURA" NUMBER(16,4), 
  "PVP_TOTAL_FACTURA" NUMBER(12,2), 
  "VENTA_TOTAL_FACTURA" NUMBER(12,2), 
  "CANAL_VENTA" CHAR(1), 
  "VALOR_IVA" NUMBER(12,2), 
  "CANCELADA" VARCHAR2(1), 
  "TIPO_DOCUMENTO" VARCHAR2(1), 
  "CAJA" NUMBER(10,0), 
  "TIPO_MOVIMIENTO" VARCHAR2(3), 
  "CLASIFICACION_MOVIMIENTO" VARCHAR2(3), 
  "CLIENTE" NUMBER(10,0), 
  "DOCUMENTO_VENTA_PADRE" NUMBER(10,0), 
  "FARMACIA_PADRE" NUMBER(12,0), 
  "USUARIO" VARCHAR2(30) DEFAULT USER, 
  "EMPLEADO_REALIZA" NUMBER(12,0), 
  "EMPLEADO_COBRA" NUMBER(12,0), 
  "PERSONA" NUMBER(12,0), 
  "PRIMER_APELLIDO" VARCHAR2(80), 
  "SEGUNDO_APELLIDO" VARCHAR2(80), 
  "NOMBRES" VARCHAR2(80), 
  "IDENTIFICACION" VARCHAR2(16), 
  "DIRECCION" VARCHAR2(120), 
  "FECHA_SISTEMA" DATE, 
  "DONACION" CHAR(1) DEFAULT 'S', 
  "TRATAMIENTO_CONTINUO" CHAR(1) DEFAULT 'S', 
  "EMPLEADO_ENTREGA" NUMBER(12,0), 
  "INCLUYEIVA" CHAR(1) DEFAULT 'S', 
  "TOMO_PEDIDO_DOMICILIO" NUMBER(12,0), 
  "DIRECCION_ENVIO" NUMBER(12,0), 
  "GENERO_NOTA_CREDITO" CHAR(1) DEFAULT 'N') 

   COMMENT ON COLUMN "FA_FACTURAS"."DOCUMENTO_VENTA" IS '(Obligatorio, 10 dígitos),Número de documento interno'
   COMMENT ON COLUMN "FA_FACTURAS"."FECHA" IS '(Obligatorio, formato DD/MON/YYYY) Fecha de factura.'
   COMMENT ON COLUMN "FA_FACTURAS"."NUMERO_SRI" IS '(Obligatorio, maximo 30 caracteres) Descripcion generica.'
   COMMENT ON COLUMN "FA_FACTURAS"."COSTO_TOTAL_FACTURA" IS '(Obligatorio,16 dígitos con 4 decimales),Costo total de la factura'
   COMMENT ON COLUMN "FA_FACTURAS"."PVP_TOTAL_FACTURA" IS '(Obligatorio,12 dígitos con 2 decimales),Valor total del P.V.P'
   COMMENT ON COLUMN "FA_FACTURAS"."VENTA_TOTAL_FACTURA" IS '(Obligatorio,12 dígitos con 2 decimales),Valor total de la factura precio con descuento'
   COMMENT ON COLUMN "FA_FACTURAS"."CANAL_VENTA" IS '(Obligatorio,Lista de valores) Canal de Ventas'
   COMMENT ON COLUMN "FA_FACTURAS"."VALOR_IVA" IS '(lObligatorio,12 dígitos con 2 decimales),Clasifica el valor gravado y no gravado.'
   COMMENT ON COLUMN "FA_FACTURAS"."CANCELADA" IS '(Obligatorio, lista de valores) SI/NO.'
   COMMENT ON COLUMN "FA_FACTURAS"."TIPO_DOCUMENTO" IS '(Obligatorio,Lista de Valores) Tipo de documento'
   COMMENT ON COLUMN "FA_FACTURAS"."TIPO_MOVIMIENTO" IS '(Obligatorio, máximo 3 caracteres) Código'
   COMMENT ON COLUMN "FA_FACTURAS"."CLASIFICACION_MOVIMIENTO" IS '(Obligatorio, máximo 3 caracteres) Código.'
   COMMENT ON COLUMN "FA_FACTURAS"."DOCUMENTO_VENTA_PADRE" IS '(Obligatorio, 10 dígitos),Número de documento interno'
   COMMENT ON COLUMN "FA_FACTURAS"."USUARIO" IS '(Obligatorio,máximo 30 caracteres),Usuario quien realiza la transaccion'
   COMMENT ON COLUMN "FA_FACTURAS"."DONACION" IS '(Obligatorio/Opcional, lista de valores) SI/NO es una donación.'
   COMMENT ON COLUMN "FA_FACTURAS"."TRATAMIENTO_CONTINUO" IS '(Obligatorio/Opcional, lista de valores) SI/NO.'
   COMMENT ON COLUMN "FA_FACTURAS"."INCLUYEIVA" IS '(Obligatorio/Opcional, lista de valores) SI/NO.'
   COMMENT ON COLUMN "FA_FACTURAS"."GENERO_NOTA_CREDITO" IS '(Obligatorio/Opcional, lista de valores) SI/NO.'
--------------------------------------------------------
--  DDL for Table FA_DETALLES_FACTURA
--------------------------------------------------------

  CREATE TABLE "FA_DETALLES_FACTURA" 
  ("DOCUMENTO_VENTA" NUMBER(10,0), 
  "FARMACIA" NUMBER(12,0), 
  "CODIGO" NUMBER(4,0), 
  "CANTIDAD" NUMBER(7,0), 
  "COSTO" NUMBER(16,4), 
  "PVP" NUMBER(16,4), 
  "PRECIO_FYBECA" NUMBER(16,4), 
  "VENTA" NUMBER(16,4), 
  "UNIDADES" NUMBER(6,0), 
  "PORCENTAJE_IVA" NUMBER(6,2), 
  "MEDICO" NUMBER(10,0), 
  "ITEM" NUMBER(12,0), 
  "SECUENCIAL" NUMBER(4,0), 
  "LISTA_DESPACHO" NUMBER(12,0), 
  "SECCION" NUMBER(12,0), 
  "PROMOCION" VARCHAR2(1), 
  "TIPO_NEGOCIO" VARCHAR2(1), 
  "DOCUMENTO_ABONO" NUMBER(10,0), 
  "CUPON" NUMBER(12,0) DEFAULT 0, 
  "TRANSACCION_FVC" VARCHAR2(3), 
  "USUARIO_REALIZA" NUMBER(12,0), 
  "USUARIO_CALLCENTER" NUMBER(12,0), 
  "DESCUENTO_TC" NUMBER(12,4)) 

   COMMENT ON COLUMN "FA_DETALLES_FACTURA"."DOCUMENTO_VENTA" IS '(Obligatorio, 10 dígitos),Número de documento interno'
   COMMENT ON COLUMN "FA_DETALLES_FACTURA"."CODIGO" IS '(Obligatorio, máximo 4 dígitos) Código numérico.'
   COMMENT ON COLUMN "FA_DETALLES_FACTURA"."CANTIDAD" IS '(Obligatorio,7 dígitos),Unidades vendidas'
   COMMENT ON COLUMN "FA_DETALLES_FACTURA"."COSTO" IS '(Obligatorio,16 dígitos con 4 decimales),Costo por unidad del producto.'
   COMMENT ON COLUMN "FA_DETALLES_FACTURA"."PVP" IS '(Obligatorio,16 dígitos con 4 decimales),P.V.P. por unidad del producto'
   COMMENT ON COLUMN "FA_DETALLES_FACTURA"."PRECIO_FYBECA" IS '(Obligatorio,16 dígitos con 4 decimales),Precio de comisariato por unidad del producto'
   COMMENT ON COLUMN "FA_DETALLES_FACTURA"."VENTA" IS '(Obligatorio,16 dígitos con 4 decimales),Venta por unidad del producto'
   COMMENT ON COLUMN "FA_DETALLES_FACTURA"."UNIDADES" IS '(Obligatorio,6 dígitos), Unidades del producto.'
   COMMENT ON COLUMN "FA_DETALLES_FACTURA"."PORCENTAJE_IVA" IS '(Obligatorio,máximo 12  dígitos con 2 decimales),Identifica si el producto paga iva o no.'
   COMMENT ON COLUMN "FA_DETALLES_FACTURA"."ITEM" IS '(Obligatorio, máximo 10 caracteres) Código.'
   COMMENT ON COLUMN "FA_DETALLES_FACTURA"."SECUENCIAL" IS '(Obligatorio, máximo 10 caracteres) Código alfanumérico.'
   COMMENT ON COLUMN "FA_DETALLES_FACTURA"."LISTA_DESPACHO" IS '(Obligatorio, máximo 12 dígitos) Código numérico.'
   COMMENT ON COLUMN "FA_DETALLES_FACTURA"."CUPON" IS '(Obligatorio/Opcional, lista de valores) SI/NO.'
   COMMENT ON COLUMN "FA_DETALLES_FACTURA"."TRANSACCION_FVC" IS '(Obligatorio, máximo 3 caracters ) Codigo de la transacción'
--------------------------------------------------------
--  DDL for Table FA_FACTURA_ADICIONAL
--------------------------------------------------------

  CREATE TABLE "FA_FACTURA_ADICIONAL" 
  ("FARMACIA" NUMBER(12,0), 
  "DOCUMENTO_VENTA" NUMBER(10,0), "TIPO_IMPRESION" VARCHAR2(1), "CP_NUM1" NUMBER, 
  "CP_NUM2" NUMBER, "CP_NUM3" NUMBER, "CP_NUM4" NUMBER, "CP_NUM5" NUMBER, "CP_NUM6" NUMBER, 
  "CP_NUM7" NUMBER, "CP_NUM8" NUMBER, "CP_NUM9" NUMBER, "CP_NUM10" NUMBER, "CP_VAR1" VARCHAR2(256), 
  "CP_VAR2" VARCHAR2(256), "CP_VAR3" VARCHAR2(256), "CP_VAR4" VARCHAR2(256), "CP_VAR5" VARCHAR2(256), 
  "CP_VAR6" VARCHAR2(256), "CP_VAR7" VARCHAR2(256), "CP_VAR8" VARCHAR2(256), "CP_VAR9" VARCHAR2(256), 
  "CP_VAR10" VARCHAR2(256), "CP_CHAR1" VARCHAR2(1), "CP_CHAR2" VARCHAR2(1), "CP_CHAR3" VARCHAR2(1), 
  "CP_CHAR4" VARCHAR2(1), "CP_CHAR5" VARCHAR2(1), "CP_CHAR6" VARCHAR2(1), "CP_CHAR7" VARCHAR2(1), 
  "CP_CHAR8" VARCHAR2(1), "CP_CHAR9" VARCHAR2(1), "CP_CHAR10" VARCHAR2(1), "CP_DATE1" DATE, 
  "CP_DATE2" DATE, "CP_DATE3" DATE, "CP_DATE4" DATE, "CP_DATE5" DATE, "CP_DATE6" DATE, "CP_DATE7" DATE, 
  "CP_DATE8" DATE, "CP_DATE9" DATE, "CP_DATE10" DATE) 

   COMMENT ON COLUMN "FA_FACTURA_ADICIONAL"."DOCUMENTO_VENTA" IS '(Obligatorio, 10 dígitos),Número de documento interno'
   COMMENT ON COLUMN "FA_FACTURA_ADICIONAL"."TIPO_IMPRESION" IS 'Indica el tipo de impresión que se realizo Térmica o Matricial'
--------------------------------------------------------
--  DDL for Table FA_DETALLES_FORMAS_PAGO
--------------------------------------------------------

  CREATE TABLE "FA_DETALLES_FORMAS_PAGO" 
  ("DOCUMENTO_VENTA" NUMBER(10,0), 
  "FARMACIA" NUMBER(12,0), "FORMA_PAGO" VARCHAR2(3), "PVP_FACTURA" NUMBER(12,2), 
  "VENTA_FACTURA" NUMBER(12,2), "NUMERO_TARJETA" VARCHAR2(100), 
  "NUMERO_ODA" VARCHAR2(15), "NUMERO_AUTORIZACION" VARCHAR2(15), 
  "NUMERO_AUTORIZACION_BOLETIN" VARCHAR2(15), "NUMERO_AUTORIZACION_FYBECA" VARCHAR2(15), 
  "INTERES" NUMBER(12,2), "NUMERO_CUOTAS" NUMBER(2,0), "TARJETA_DESCUENTO" VARCHAR2(20), 
  "TARJETADT" VARCHAR2(1), "TARJETAHABIENTE" VARCHAR2(80), "FECHA_CADUCIDAD" DATE, 
  "USUARIO" VARCHAR2(30) DEFAULT 'USER', "TELEFONO" VARCHAR2(16), "MEDIO_DESCUENTO" VARCHAR2(3), 
  "CREDITO_EMPLEADO" NUMBER(12,0), "EMPRESA" NUMBER(12,0), "EMPLEADO" NUMBER(12,0), 
  "CLIENTE" NUMBER(12,0), "PLAN_CREDITO" VARCHAR2(3), "CHEQUE_RECIBIDO" NUMBER(12,0), 
  "REMISION" NUMBER(12,0), "TIPO_CREDITO" VARCHAR2(3), "TARJETA_VITALCARD" VARCHAR2(20), 
  "TIPO_SERVICIO" VARCHAR2(3), "COSTO_FACTURA" NUMBER(16,4), "AUTORIZACION_FVC" VARCHAR2(20)) 

   COMMENT ON COLUMN "FA_DETALLES_FORMAS_PAGO"."DOCUMENTO_VENTA" IS '(Obligatorio, 10 dígitos),Número de documento interno'
   COMMENT ON COLUMN "FA_DETALLES_FORMAS_PAGO"."FORMA_PAGO" IS '(Obligatorio, máximo 3 caracteres) Código.'
   COMMENT ON COLUMN "FA_DETALLES_FORMAS_PAGO"."PVP_FACTURA" IS '(Obligatorio,12 dígitos con 2 decimales),PVP de la factura por forma de pago'
   COMMENT ON COLUMN "FA_DETALLES_FORMAS_PAGO"."VENTA_FACTURA" IS '(Obligatorio,12 dígitos con 2 decimales),Venta de la factura por forma de pago'
   COMMENT ON COLUMN "FA_DETALLES_FORMAS_PAGO"."NUMERO_TARJETA" IS '(Obligatorio, máximo 20 caracteres) Número de tarjeta de crédito.'
   COMMENT ON COLUMN "FA_DETALLES_FORMAS_PAGO"."NUMERO_ODA" IS '(Obligatorio, máximo 15 caracteres) Número de Oda.'
   COMMENT ON COLUMN "FA_DETALLES_FORMAS_PAGO"."NUMERO_AUTORIZACION" IS '(Obligatorio, máximo 15 caracteres) Número de autorización por límite de piso.'
   COMMENT ON COLUMN "FA_DETALLES_FORMAS_PAGO"."NUMERO_AUTORIZACION_BOLETIN" IS '(Obligatorio, máximo 15 caracteres) Número de autorización por boletín.'
   COMMENT ON COLUMN "FA_DETALLES_FORMAS_PAGO"."NUMERO_AUTORIZACION_FYBECA" IS '(Obligatorio, máximo 15 caracteres) Número de autorización Fybeca.'
   COMMENT ON COLUMN "FA_DETALLES_FORMAS_PAGO"."INTERES" IS '(Opcional,12 dígitos con 2 decimales),Intereses Tarjeta de Crèdido.'
   COMMENT ON COLUMN "FA_DETALLES_FORMAS_PAGO"."NUMERO_CUOTAS" IS '(Obligatorio,2 dígitos),Nùmero de cuotas para creditos empleados'
   COMMENT ON COLUMN "FA_DETALLES_FORMAS_PAGO"."TARJETA_DESCUENTO" IS '(Opcional,máximo 20 caracteres),Nùmero de tarjeta de Descuento'
   COMMENT ON COLUMN "FA_DETALLES_FORMAS_PAGO"."TARJETADT" IS '(Opcional,máximo 1 caracteres),Tarjeta de Crédito deslizada o tecleada'
   COMMENT ON COLUMN "FA_DETALLES_FORMAS_PAGO"."TARJETAHABIENTE" IS '(Opcional,máximo 80 caracteres),Nombre del Tarjetahabiente'
   COMMENT ON COLUMN "FA_DETALLES_FORMAS_PAGO"."FECHA_CADUCIDAD" IS '(Opcional,dato tipo fecha),Fecha de caducidad de la tarjeta'
   COMMENT ON COLUMN "FA_DETALLES_FORMAS_PAGO"."USUARIO" IS '(Obligatorio, máximo 30 caracteres) Usuario de Oracle.'
   COMMENT ON COLUMN "FA_DETALLES_FORMAS_PAGO"."TELEFONO" IS '(Opcional,máximo 16 caracteres),Telefono del Tarjetahabiente'
   COMMENT ON COLUMN "FA_DETALLES_FORMAS_PAGO"."MEDIO_DESCUENTO" IS '(Obligatorio, máximo 3 caracteres) Código .'
   COMMENT ON COLUMN "FA_DETALLES_FORMAS_PAGO"."PLAN_CREDITO" IS '(Obligatorio, máximo 3 caracteres) Código.'
   COMMENT ON COLUMN "FA_DETALLES_FORMAS_PAGO"."TIPO_CREDITO" IS '(Obligatorio, máximo 3 caracteres) Código alfanumérico.'
   COMMENT ON COLUMN "FA_DETALLES_FORMAS_PAGO"."COSTO_FACTURA" IS '(Obligatorio/Opcional, máximo 16 dígitos 4 decimales) Precio.'
--------------------------------------------------------
--  DDL for Table FA_NOTAS_CREDITO
--------------------------------------------------------

  CREATE TABLE "FA_NOTAS_CREDITO" 
  ("CODIGO" NUMBER(12,0), "FECHA_CANCELACION" DATE, "FECHA" DATE, 
  "CANCELADA" VARCHAR2(1), "USUARIO" VARCHAR2(30) DEFAULT USER, 
  "VALOR" NUMBER(16,4), "DOCUMENTO_VENTA" NUMBER(10,0), 
  "FORMA_PAGO" VARCHAR2(3), "FARMACIA" NUMBER(12,0), 
  "EMPLEADO_COBRA" NUMBER(12,0), "TIPO_CANCELACION" VARCHAR2(1), 
  "FARMACIA_CANJE" NUMBER(12,0), "DOCUMENTO_CANCELACION" NUMBER(10,0)) 

   COMMENT ON COLUMN "FA_NOTAS_CREDITO"."FECHA_CANCELACION" IS '(Obligatorio/Opcional, formato DD/MON/YYYY) Fecha genérica.'
   COMMENT ON COLUMN "FA_NOTAS_CREDITO"."FECHA" IS '(Obligatorio/Opcional, formato DD/MON/YYYY) Fecha genérica.'
   COMMENT ON COLUMN "FA_NOTAS_CREDITO"."USUARIO" IS '(Obligatorio/Opcional, máximo 30 caracteres) Usuario de Oracle.'
   COMMENT ON COLUMN "FA_NOTAS_CREDITO"."VALOR" IS '(Obligatorio/Opcional, máximo 16 dígitos 4 decimales) Precio.'
   COMMENT ON COLUMN "FA_NOTAS_CREDITO"."DOCUMENTO_VENTA" IS '(Obligatorio, 10 dígitos),Número de documento interno'
   COMMENT ON COLUMN "FA_NOTAS_CREDITO"."FORMA_PAGO" IS '(Obligatorio, máximo 3 caracteres) Código.'
   COMMENT ON COLUMN "FA_NOTAS_CREDITO"."DOCUMENTO_CANCELACION" IS '(Obligatorio, 10 dígitos),Número de documento interno'
--------------------------------------------------------
--  DDL for Table FA_CREDITOS
--------------------------------------------------------

  CREATE TABLE "FA_CREDITOS" 
  ("REFERENCIA" NUMBER(10,0), 
  "TOTAL_VENTA" NUMBER(12,2), 
  "TOTAL_PVP" NUMBER(12,2), 
  "FECHA" DATE, 
  "CANCELADA" VARCHAR2(1), 
  "CLIENTE" NUMBER(12,0), 
  "FORMA_PAGO" VARCHAR2(3), 
  "FARMACIA" NUMBER(12,0), 
  "DOCUMENTO_VENTA" NUMBER(10,0), 
  "CLASIFICACION_MOVIMIENTO" VARCHAR2(3), 
  "TIPO_MOVIMIENTO" VARCHAR2(3), 
  "EMPLEADO" NUMBER(12,0), 
  "EMPRESA" NUMBER(12,0)) 

   COMMENT ON COLUMN "FA_CREDITOS"."REFERENCIA" IS '(Opcional, máximo 30 caracteres) Documento de referencia para devoluciones'
   COMMENT ON COLUMN "FA_CREDITOS"."TOTAL_VENTA" IS '(Obligatorio,12 dídgitos con 2 decimales),Total del crèdito aplicado descuentos'
   COMMENT ON COLUMN "FA_CREDITOS"."TOTAL_PVP" IS '(Obligatorio,12 dídgitos con 2 decimales),Total del crèdito'
   COMMENT ON COLUMN "FA_CREDITOS"."FECHA" IS '(Obligatorio, formato DD/MON/YYYY) Fecha del crédito.'
   COMMENT ON COLUMN "FA_CREDITOS"."FORMA_PAGO" IS '(Obligatorio, máximo 3 caracteres) Código.'
   COMMENT ON COLUMN "FA_CREDITOS"."DOCUMENTO_VENTA" IS '(Obligatorio, 10 dígitos),Número de documento interno'
   COMMENT ON COLUMN "FA_CREDITOS"."CLASIFICACION_MOVIMIENTO" IS '(Obligatorio, máximo 3 caracteres) Código.'
   COMMENT ON COLUMN "FA_CREDITOS"."TIPO_MOVIMIENTO" IS '(Obligatorio, máximo 3 caracteres) Código'
