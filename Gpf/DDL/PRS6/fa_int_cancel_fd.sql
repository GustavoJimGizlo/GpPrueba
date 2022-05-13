--------------------------------------------------------
--  DDL for Table FA_INT_CANCEL_FD
--------------------------------------------------------
CREATE SEQUENCE   FARMACIAS.seq_fa_int_cancel_fd 
     MINVALUE 0 MAXVALUE 10000000 
	 INCREMENT BY 1 START WITH 23 
	 NOCACHE  
	 NOORDER  
	 NOCYCLE ;

GRANT SELECT ON FARMACIAS.seq_fa_int_cancel_fd TO WEBLINK;


  CREATE TABLE FARMACIAS.FA_INT_CANCEL_FD 
   (  "ID" NUMBER(20), 
  "DOCUMENTO_VENTA" NUMBER(20), 
  "FARMACIA" NUMBER(12), 
  "TRAZA_ID" NUMBER(12), 
  "ORDEN_SFCC"VARCHAR2(50), 
  "FECHA_REQUEST" DATE, 
  "MENSAJE" VARCHAR2(200), 
  "JSON_REQUEST" CLOB, 
  "MENSAJE_REQUEST" VARCHAR2(200), 
  "TRAZA_REQUEST" CHAR(1), 
  "FECHA_RESPONSE" DATE, 
  "JSON_RESPONSE" CLOB, 
  "NUMERO_ENVIOS" NUMBER(2), 
  "FECHA_INSERTA" DATE, 
  "USUARIO_INSERTA" VARCHAR2(20), 
  "FECHA_ACTUALIZA" DATE, 
  "USUARIO_ACTUALIZA" VARCHAR2(20), 



  
   ) PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
  STORAGE(
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "INTEGRACION_HIS" ;
  GRANT SELECT ON FARMACIAS.FA_INT_CANCEL_FD TO WEBLINK;

comment on column  FARMACIAS.FA_INT_CANCEL_FD.ID
is 'Secuencial automático habilitador por generador de sequence seq_fa_int_cancel_fd' ;
comment on column  FARMACIAS.FA_INT_CANCEL_FD.DOCUMENTO_VENTA
is 'Documento de venta de PDV';
comment on column  FARMACIAS.FA_INT_CANCEL_FD.FARMACIA
is 'Identificar del código de farmacia';
comment on column  FARMACIAS.FA_INT_CANCEL_FD.TRAZA_ID
is 'Número identificador del ID de traza pedidos se lo obtiene con un MAX del campo WB_CABECERA_ORDEN.ID';
comment on column  FARMACIAS.FA_INT_CANCEL_FD.ORDEN_SFCC
is 'Número Orden de compra Sales Force, Nota:actualmente se almacenamos ese dato en la tabla WEB.UIO->FARCOMED. WB_ORDEN_SALES_FORCE';

comment on column  FARMACIAS.FA_INT_CANCEL_FD.FECHA_REQUEST
is 'Fecha y hora en la que llega el mensaje del request para presentarlo en la pantalla de cancelaciones.';


comment on column  FARMACIAS.FA_INT_CANCEL_FD.JSON_REQUEST
is 'JSON recibido del facturador digital para presentarlo en la pantalla de cancelaciones.';

comment on column  FARMACIAS.FA_INT_CANCEL_FD.MENSAJE_REQUEST
is 'Mensaje de error en caso de existir una novedad al enviar el JSON para presentarlo en la pantalla de cancelaciones.';


comment on column  FARMACIAS.FA_INT_CANCEL_FD.TRAZA_REQUEST
is 'Identificador si se actualizó los campos necesarios la tabla WB_CABECERA_ORDEN. “N” = NO ; “S” = SI';


comment on column  FARMACIAS.FA_INT_CANCEL_FD.FECHA_RESPONSE
is 'Fecha y hora en la que se notifica por primera vez al Facturador Digital de la cancelación';



comment on column  FARMACIAS.FA_INT_CANCEL_FD.JSON_RESPONSE
is 'JSON enviado al facturador digital al momento de informar la cancelación de la compra';

comment on column  FARMACIAS.FA_INT_CANCEL_FD.NUMERO_ENVIOS
is 'Número de envíos de JSON al facturador digital al momento de informar sobre la cancelación de la compra';


comment on column  FARMACIAS.FA_INT_CANCEL_FD.FECHA_INSERTA
is 'Fecha y hora del registro';


comment on column  FARMACIAS.FA_INT_CANCEL_FD.USUARIO_INSERTA
is 'Usuario que realiza el insert';

comment on column  FARMACIAS.FA_INT_CANCEL_FD.FECHA_ACTUALIZA
is 'Fecha y hora en que se realiza la última actualización del registro';

comment on column  FARMACIAS.FA_INT_CANCEL_FD.USUARIO_ACTUALIZA
is 'Usuario que realiza la actualización';


