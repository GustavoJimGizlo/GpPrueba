--------------------------------------------------------
-- Archivo creado  - lunes-febrero-14-2022   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table TB_PERSONA
--------------------------------------------------------

  CREATE TABLE "TB_PERSONA" 
  ("ID" NUMBER(19,0), 
  "APELLIDOS" VARCHAR2(100), 
  "CELULAR" VARCHAR2(12), 
  "CORREO_ELECTRONICO" VARCHAR2(150), 
  "FECHA_NACIMIENTO" DATE, 
  "FOTO" VARCHAR2(100), 
  "ESTADO_CIVIL" NUMBER(19,0), 
  "GENERO" NUMBER(19,0), 
  "TIPO_ID" NUMBER(19,0), 
  "IDENTIFICACION" VARCHAR2(20), 
  "NOMBRES" VARCHAR2(100), 
  "TELEFONO" VARCHAR2(12), 
  "CIUDAD_NACIMIENTO" NUMBER(19,0), 
  "CIUDAD_RECIDENCIAS" NUMBER(19,0), 
  "ID_ORGANIZACION" NUMBER(19,0))
--------------------------------------------------------
--  DDL for Table TB_USUARIO
--------------------------------------------------------

  CREATE TABLE "TB_USUARIO" 
  ("TIPO" VARCHAR2(31), 
  "ID" NUMBER(19,0), 
  "ES_CLV_AUTOGENERADA" VARCHAR2(1), 
  "ESTADO" VARCHAR2(3), 
  "USERNAME" VARCHAR2(20), 
  "PASSWORD" VARCHAR2(200), 
  "ID_PERSONA" NUMBER(19,0))  ENABLE ROW MOVEMENT
--------------------------------------------------------
--  DDL for Table TB_USUARIO_ROL
--------------------------------------------------------

  CREATE TABLE "TB_USUARIO_ROL" 
  ("ID" NUMBER(19,0), 
  "ID_ROL" NUMBER(19,0), 
  "ID_USUARIO" NUMBER(19,0))
