package com.a01luisrene.multirecordatorio.io.db;

import android.provider.BaseColumns;


final class Constantes {
    Constantes(){}

    //
    //===========================================================================
    //  CONSTANTES PARA LA GENERACIÓN DE BASE DE DATOS MULTI RECORDATORIOS
    //===========================================================================
    //

    static class EntradasRecordatorio implements BaseColumns {
        static final String TABLA_RECORDATORIOS = "recordatorios";

        /*[COLUMNAS PARA LA TABLA RECORDATORIO]*/
        static final String ID_RECORDATORIO = "_id";
        static final String TITULO_RECORDATORIO = "titulo";
        static final String ENTIDAD_OTROS_RECORDATORIO = "entidad_otros";
        static final String LLAVE_CATEGORIA_RECORDATORIO = "id_categoria";
        static final String CONTENIDO_MENSAJE_RECORDATOIO = "contenido_mensaje";
        static final String TELEFONO_RECORDATORIO = "numero_smartphone";
        static final String ENVIO_MENSAJE_RECORDATORIO = "ennviar_mensaje";
        static final String PUBLICAR_FACEBOOK_RECORDATORIO = "publicar_facebook";
        static final String PUBLICAR_TWITTER_RECORDATORIO = "publicar_twitter";
        static final String FECHA_CREACION_RECORDATORIO = "fecha_creacion";
        static final String FECHA_AVISO_RECORDATORIO = "fecha_aviso_recordatorio";
        static final String HORA_AVISO_RECORDATORIO = "hora_aviso_recordatorio";
        static final String ESTADO_RECORDATORIO = "estado_recordatorio";
    }

    static class EntradasCategoria implements BaseColumns {
        static final String TABLA_CATEGORIAS_RECORDATORIOS = "categoria_recordatorios";

        /*[COLUMNAS PARA LA TABLA CATEGORIAS DE RECORDATORIOS]*/
        static final String ID_CATEGORIA = "_id";
        static final String RUTA_IMAGEN_CATEGORIA = "ruta_imagen_categoria";
        static final String TITULO_CATEGORIA = "titulo_categoria";
        static final String PROTECCION_CATEGORIA = "proteccion";
        static final String FECHA_CREACION_CATEGORIA = "fecha_creacion";
    }

    static class EntradasUsuario implements BaseColumns {
        static final String TABLA_USUARIOS = "usuarios";

        /*[COLUMNAS PARA LA TABLA USUARIO]*/
        static final String ID_USUARIO = "_id";
        static final String AVATAR_USUARIO = "avatar";
        static final String NOMBRE_USUARIO = "user";
        static final String PASSWORD_USUARIO = "password";
        static final String CONFIRMAR_PASSWORD_USUARIO = "confirmar_password";
        static final String EMAIL_USUARIO = "correo_electronico";
        static final String FECHA_CREACION_USUARIO = "fecha_creacion";
        static final String ESTADO_USUARIO = "estado";
    }
}
