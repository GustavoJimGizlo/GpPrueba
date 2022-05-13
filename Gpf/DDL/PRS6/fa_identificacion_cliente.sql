

--------------------------------------------------------
--  DDL for Table FA_IDENTIFICACION_CLIENTE
--------------------------------------------------------



CREATE SEQUENCE  FARMACIAS.seq_fa_identificacion_cliente  MINVALUE 0 MAXVALUE 10000000 INCREMENT BY 1 START WITH 23 NOCACHE  NOORDER  NOCYCLE ;
GRANT SELECT ON FARMACIAS.seq_fa_identificacion_cliente TO WEBLINK;


  CREATE TABLE FARMACIAS.FA_IDENTIFICACION_CLIENTE 
   (  "ID" NUMBER(19,0), 
  "CODIGO" VARCHAR2(20), 
  "DESCRIPCION" VARCHAR2(100)

   ) PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
  STORAGE(
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "INTEGRACION_HIS";

  GRANT SELECT ON FARMACIAS.FA_IDENTIFICACION_CLIENTE TO WEBLINK;

comment on column  FARMACIAS.FA_IDENTIFICACION_CLIENTE.ID
is 'Secuencial automático habilitador por generador de sequence seq_fa_identificacion_cliente' ;
comment on column  FARMACIAS.FA_IDENTIFICACION_CLIENTE.CODIGO
is 'Codigo según ficha técnica del sri';
comment on column  FARMACIAS.FA_IDENTIFICACION_CLIENTE.DESCRIPCION
is 'Descripción del código según ficha técnica del sri';

insert into FARMACIAS.FA_IDENTIFICACION_CLIENTE (id,codigo,descripcion) values (1,'04','RUC');
insert into FARMACIAS.FA_IDENTIFICACION_CLIENTE (id,codigo,descripcion) values (2,'05','CEDULA');
insert into FARMACIAS.FA_IDENTIFICACION_CLIENTE (id,codigo,descripcion) values (3,'06','PASAPORTE');
insert into FARMACIAS.FA_IDENTIFICACION_CLIENTE (id,codigo,descripcion) values (4,'07','VENTA A CONSUMIDOR FINAL');
insert into FARMACIAS.FA_IDENTIFICACION_CLIENTE (id,codigo,descripcion) values (5,'08','IDENTIFICACION DEL EXTERIOR');





