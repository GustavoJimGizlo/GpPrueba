--------------------------------------------------------
--  DDL for Table FA_RELACION_FORMA_PAGO
--------------------------------------------------------



CREATE SEQUENCE  FARMACIAS.seq_fa_relacion_forma_pago  MINVALUE 0 MAXVALUE 10000000 INCREMENT BY 1 START WITH 23 NOCACHE  NOORDER  NOCYCLE ;
GRANT SELECT ON FARMACIAS.seq_fa_relacion_forma_pago TO WEBLINK;


  CREATE TABLE FARMACIAS.FA_RELACION_FORMA_PAGO
   (  "ID" NUMBER(19,0), 
  "CODIGO_PAGO" VARCHAR2(10), 
  "CODIGO_METODO_PAGO" VARCHAR2(10),
  "CODIGO_METODO_PAGO_FYBECA" VARCHAR2(10)

   ) PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
  STORAGE(
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "INTEGRACION_HIS" ;
  GRANT SELECT ON FARMACIAS.FA_RELACION_FORMA_PAGO TO WEBLINK;


comment on column  FARMACIAS.FA_RELACION_FORMA_PAGO.ID
is 'Secuencial automático habilitador por generador de sequence seq_fa_relacion_forma_pago' ;
comment on column  FARMACIAS.FA_RELACION_FORMA_PAGO.CODIGO_PAGO
is 'Codigo que se envía desde facturador';
comment on column  FARMACIAS.FA_RELACION_FORMA_PAGO.CODIGO_METODO_PAGO
is 'Codigo que se envía desde facturador';
comment on column  FARMACIAS.FA_RELACION_FORMA_PAGO.CODIGO_METODO_PAGO_FYBECA
is 'Codigo de pago correspondiente a la forma de pago fybeca';

GRANT SELECT ON FARMACIAS.FA_RELACION_FORMA_PAGO TO WEBLINK;


insert into FARMACIAS.fa_relacion_forma_pago (id,codigo_pago,codigo_metodo_pago,codigo_metodo_pago_fybeca) values (1,'P2P','TCR', 'PP');
insert into FARMACIAS.fa_relacion_forma_pago (id,codigo_pago,codigo_metodo_pago,codigo_metodo_pago_fybeca) values (2,'CV','CONVENIOS', 'CE');
