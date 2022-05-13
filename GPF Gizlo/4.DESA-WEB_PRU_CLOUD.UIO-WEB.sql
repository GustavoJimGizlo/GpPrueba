--------------------------------------------------------
-- Archivo creado  - lunes-febrero-14-2022   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table WB_CABECERA_ORDEN
--------------------------------------------------------

  CREATE TABLE "WB_CABECERA_ORDEN" 
  ("FORMA_PAGO_TARJETAHAB" VARCHAR2(100), 
  "FORMA_PAGO_VALOR" VARCHAR2(50), 
  "CLI_PRIMER_NOMBRE" VARCHAR2(150), 
  "DIR_PRINCIPAL" VARCHAR2(500), 
  "CLI_ID" VARCHAR2(15), 
  "ORDEN_ESTADO" VARCHAR2(1), 
  "FORMA_PAGO_TIPO_CRED" VARCHAR2(5), 
  "ORDEN_USUARIO_PROC" VARCHAR2(15), 
  "DIR_REFERENCIA" VARCHAR2(500), 
  "FORMA_PAGO_CVV" VARCHAR2(4), 
  "CLI_TIPO_ID" VARCHAR2(1), 
  "MEDIO_CONT_VALOR" VARCHAR2(15), 
  "FORMA_PAGO_TIPO" VARCHAR2(2), 
  "MEDIO_DESC" VARCHAR2(2), 
  "ORDEN_MOTIVO_ANUL" VARCHAR2(300), 
  "ORDEN_DOCUMENTO_PROC" NUMBER(12,0), 
  "DIR_NO" VARCHAR2(100), 
  "FARMACIA" NUMBER(12,0), 
  "FACTURA_CLI_PRIMER_APE" VARCHAR2(40), 
  "MEDIO_DESC_VALOR" VARCHAR2(20), 
  "CLI_PRIMER_APELLIDO" VARCHAR2(60), 
  "ORDEN_FECHA_GEN" DATE, 
  "CLI_SEGUNDO_NOMBRE" VARCHAR2(60), 
  "DIR_CODIGO" NUMBER(12,0), 
  "FORMA_PAGO_FECHA_CAD" VARCHAR2(8), 
  "CLI_SEGUNDO_APELLIDO" VARCHAR2(30), 
  "FACTURA_CLI_SEGUNDO_APE" VARCHAR2(60), 
  "ORDEN_CANAL" VARCHAR2(1), 
  "FACTURA_NOM_CLI" VARCHAR2(60), 
  "ORDEN_FECHA_PROC" DATE, 
  "ID" NUMBER(12,0), 
  "MEDIO_CONT_ID" NUMBER(8,0), 
  "FACTURA_ID_CLI" VARCHAR2(15), 
  "DIR_INTERSECCION" VARCHAR2(150), 
  "FACTURA_DIR" VARCHAR2(500), 
  "MEDIO_CONT_TIPO" NUMBER(12,0), 
  "CLI_EMAIL" VARCHAR2(50), 
  "BASE0" NUMBER(12,4), 
  "BASE12" NUMBER(12,4), 
  "SUBTOTAL" NUMBER(12,4), 
  "TOTAL" NUMBER(12,4), 
  "TOTAL_IVA" NUMBER(12,4), 
  "ENVIO_SIN_IVA" NUMBER(12,4), 
  "ENVIO_CON_IVA" NUMBER(12,4), 
  "IVA_ENVIO" NUMBER(12,4), 
  "GRAVAMEN_ENVIO" NUMBER(12,0), 
  "REVERSOP2P" VARCHAR2(50), 
  "FPVALOR" NUMBER(12,4), 
  "LATITUDE" VARCHAR2(50), 
  "LONGITUDE" VARCHAR2(50))  ENABLE ROW MOVEMENT 

   COMMENT ON COLUMN "WB_CABECERA_ORDEN"."FORMA_PAGO_VALOR" IS 'Tarjeta en caso de pgar con una tarjeta'
   COMMENT ON COLUMN "WB_CABECERA_ORDEN"."LATITUDE" IS 'Geolocalización, Latitud del cliente que realiza la compra, viene del XML de SF ruta: shipments/shipment/shipping-address/custom-attributes/custom-attribute[@attribute-id=''latitude'']'
   COMMENT ON COLUMN "WB_CABECERA_ORDEN"."LONGITUDE" IS 'Geolocalización, Longitud del cliente que realiza la compra, viene del XML de SF ruta: shipments/shipment/shipping-address/custom-attributes/custom-attribute[@attribute-id=''longitude'']'
