--------------------------------------------------------
--  DDL for Table TB_LOG_FACTURA_DIGITAL
--------------------------------------------------------
CREATE SEQUENCE  GINVOICE.seq_tb_log_factura_digital  MINVALUE 0 MAXVALUE 10000000 INCREMENT BY 1 START WITH 23 NOCACHE  NOORDER  NOCYCLE ;
GRANT SELECT ON GINVOICE.seq_tb_log_factura_digital TO WEBLINK;
GRANT SELECT ON GINVOICE.seq_tb_log_factura_digital TO GINVOICE;
GRANT SELECT ON GINVOICE.seq_tb_log_factura_digital TO ADM;


  CREATE TABLE GINVOICE.TB_LOG_FACTURA_DIGITAL 
   (  "ID" NUMBER(19,0), 
  "JSON" CLOB, 
  "ERROR" VARCHAR(1), 
  "REINTEGRAR" VARCHAR2(2 BYTE), 
  "INTENTOS" NUMBER(3), 
  "CODIGO" VARCHAR(4), 
  "MENSAJE" VARCHAR2(200), 
  "FECHA_INSERTA" DATE, 
  "USUARIO_INSERTA" VARCHAR2(20)

  
   ) PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
  STORAGE(
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "GINVOICE" ;
  GRANT SELECT ON GINVOICE.TB_LOG_FACTURA_DIGITAL TO WEBLINK;
  GRANT SELECT ON GINVOICE.TB_LOG_FACTURA_DIGITAL TO GINVOICE;
  GRANT SELECT ON GINVOICE.TB_LOG_FACTURA_DIGITAL TO ADM;  
  
  
  
comment on column  GINVOICE.TB_LOG_FACTURA_DIGITAL.ID
is 'Secuencial automático habilitador por generador de secuencie seq_tb_log_factura_digital' ;
comment on column  GINVOICE.TB_LOG_FACTURA_DIGITAL.JSON
is 'JSON que envia la petición y se almacena en el log para un posible reenvio si falla la aplicación';
comment on column GINVOICE.TB_LOG_FACTURA_DIGITAL.ERROR
is 'Tipo de Error';
comment on column GINVOICE.TB_LOG_FACTURA_DIGITAL.INTENTOS
is 'Número de intentos que se debe actualizar cuando el scheduler realice el reenvio';
comment on column GINVOICE.TB_LOG_FACTURA_DIGITAL.CODIGO
is 'Codigo de error que se ingresa en la tabla';
comment on column GINVOICE.TB_LOG_FACTURA_DIGITAL.MENSAJE
is 'mensaje informativo del  error';
   
comment on column GINVOICE.TB_LOG_FACTURA_DIGITAL.USUARIO_INSERTA
is 'usuario que realiza la inserción en la baes de datos';
   
  