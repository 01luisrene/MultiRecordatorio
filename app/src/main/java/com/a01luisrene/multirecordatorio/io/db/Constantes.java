package com.a01luisrene.multirecordatorio.io.db;

import android.provider.BaseColumns;


public final class Constantes {
    Constantes(){}

    //
    //===========================================================================
    //  CONSTANTES PARA LA GENERACIÓN DE BASE DE DATOS MULTI RECORDATORIOS
    //===========================================================================
    //

    public static class EntradasRecordatorio implements BaseColumns {
        public static final String TABLA_RECORDATORIOS = "recordatorios";

        /*[COLUMNAS PARA LA TABLA RECORDATORIO]*/
        public static final String ID_RECORDATORIO = "_id";
        public static final String TITULO_RECORDATORIO = "titulo";
        public static final String ENTIDAD_OTROS_RECORDATORIO = "entidad_otros";
        public static final String LLAVE_CATEGORIA_RECORDATORIO = "id_categoria";
        public static final String CONTENIDO_MENSAJE_RECORDATOIO = "contenido_mensaje";
        public static final String TELEFONO_RECORDATORIO = "numero_smartphone";
        public static final String ENVIO_MENSAJE_RECORDATORIO = "ennviar_mensaje";
        public static final String PUBLICAR_FACEBOOK_RECORDATORIO = "publicar_facebook";
        public static final String PUBLICAR_TWITTER_RECORDATORIO = "publicar_twitter";
        public static final String FECHA_CREACION_RECORDATORIO = "fecha_creacion";
        public static final String FECHA_AVISO_RECORDATORIO = "fecha_aviso_recordatorio";
        public static final String HORA_AVISO_RECORDATORIO = "hora_aviso_recordatorio";
        public static final String ESTADO_RECORDATORIO = "estado_recordatorio";
    }

    public static class EntradasCategoria implements BaseColumns {
        public static final String TABLA_CATEGORIAS_RECORDATORIOS = "categoria_recordatorios";

        /*[COLUMNAS PARA LA TABLA CATEGORIAS DE RECORDATORIOS]*/
        public static final String ID_CATEGORIA = "_id";
        public static final String RUTA_IMAGEN_CATEGORIA = "ruta_imagen_categoria";
        public static final String TITULO_CATEGORIA = "titulo_categoria";
        public static final String PROTECCION_CATEGORIA = "proteccion";
        public static final String FECHA_CREACION_CATEGORIA = "fecha_creacion";
    }

    public static class EntradasUsuario implements BaseColumns {
        public static final String TABLA_USUARIOS = "usuarios";

        /*[COLUMNAS PARA LA TABLA USUARIO]*/
        public static final String ID_USUARIO = "_id";
        public static final String AVATAR_USUARIO = "avatar";
        public static final String NOMBRE_USUARIO = "user";
        public static final String PASSWORD_USUARIO = "password";
        public static final String CONFIRMAR_PASSWORD_USUARIO = "confirmar_password";
        public static final String EMAIL_USUARIO = "correo_electronico";
        public static final String FECHA_CREACION_USUARIO = "fecha_creacion";
        public static final String ESTADO_USUARIO = "estado";
    }
}
