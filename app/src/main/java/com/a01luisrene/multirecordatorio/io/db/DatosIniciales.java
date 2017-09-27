package com.a01luisrene.multirecordatorio.io.db;

import android.content.ContentValues;

import com.a01luisrene.multirecordatorio.utilidades.Utilidades;



class DatosIniciales {

    static ContentValues[] datosIniciales(){

        String fechaActual = Utilidades.fechaHora();
        String[] id = new String[]{null};
        String[] rutaImg = new String[]{
                "http://lorempixel.com/400/200/",
                "http://lorempixel.com/450/220/",
                "http://lorempixel.com/500/250/"
        };

        String[] tituloCat = new String[]{"Cumpleaños", "Cita", "Aniversario"};
        int[] proteccion = new int[]{1};
        String[]fecha = new String[]{fechaActual};

        return new ContentValues[]{
                valores(
                        id[0],
                        rutaImg[0],
                        tituloCat[0],
                        proteccion[0],
                        fecha[0]
                ),
                valores(
                        id[0],
                        rutaImg[1],
                        tituloCat[1],
                        proteccion[0],
                        fecha[0]
                ),
                valores(
                        id[0],
                        rutaImg[2],
                        tituloCat[2],
                        proteccion[0],
                        fecha[0]
                )
        };
    }
    private static ContentValues valores(String id,
                                         String rutaImg,
                                         String tituloCat,
                                         int proteccion,
                                         String fechaCreacion){
        // Contenedor de valores
        ContentValues values = new ContentValues();
        // Pares clave-valor
        values.put(Constantes.EntradasCategoria.ID_CATEGORIA, id);
        values.put(Constantes.EntradasCategoria.RUTA_IMAGEN_CATEGORIA, rutaImg);
        values.put(Constantes.EntradasCategoria.TITULO_CATEGORIA, tituloCat);
        values.put(Constantes.EntradasCategoria.PROTECCION_CATEGORIA, proteccion);
        values.put(Constantes.EntradasCategoria.FECHA_CREACION_CATEGORIA, fechaCreacion);

        return values;
    }
}
