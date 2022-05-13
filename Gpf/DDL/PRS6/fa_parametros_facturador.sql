--------------------------------------------------------
--  DDL for Table PARAMETROS_FACTURADOR
--------------------------------------------------------

CREATE SEQUENCE  FARMACIAS.seq_fa_parametros_facturador MINVALUE 0 MAXVALUE 10000000 INCREMENT BY 1 START WITH 1 NOCACHE  NOORDER  NOCYCLE ;
  GRANT SELECT ON FARMACIAS.seq_fa_parametros_facturador TO WEBLINK;


  CREATE TABLE FARMACIAS.FA_PARAMETROS_FACTURADOR
   (  
		ID 		NUMBER(19,0) not NULL, 
		CLAVE 	VARCHAR2(20) not NULL, 
		VALOR 	VARCHAR2(20) not NULL

   ) PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
  STORAGE(
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "INTEGRACION_HIS" ;
  GRANT SELECT ON FARMACIAS.FA_PARAMETROS_FACTURADOR TO WEBLINK;
comment on column  FARMACIAS.FA_PARAMETROS_FACTURADOR.ID
is 'Secuencial automático habilitador por generador de secuencie seq_fa_parametro_facturador' ;
comment on column  FARMACIAS.FA_PARAMETROS_FACTURADOR.CLAVE
is 'Identificador de parametro que sera usada en los proyectos para adquirir el valor del parametro';
comment on column  FARMACIAS.FA_PARAMETROS_FACTURADOR.VALOR
is 'Valor del parametro';


