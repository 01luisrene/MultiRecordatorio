package com.a01luisrene.multirecordatorio.utilidades;

import android.content.Context;
import android.content.res.Resources;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.widget.Spinner;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utilidades {

    private static final String TAG = "logcat";
    public static boolean smartphone = true;

    //Sirve para formatear cadenas [Int: parametros int]
    public static String formatearCadenasInt(Context context, int Cadena, int textoReemplazar){

        Resources res = context.getResources();
        return String.format(res.getString(Cadena), context.getString(textoReemplazar));

    }
    //Sirve para formatear cadenas [Str: parametros String]
    public static String formatearCadenasStr(Context context, int Cadena, String textoReemplazar){

        Resources res = context.getResources();
        return String.format(res.getString(Cadena),textoReemplazar);

    }

    public static Spannable setSpanCustomText(Context context, String text, int start, int end, int color, float proportion, int styleType) {
        Spannable spanText = new SpannableString(text);
        //change the color text
        spanText.setSpan(new ForegroundColorSpan(color), start, end, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        // change the text size using a proportion
        spanText.setSpan(new RelativeSizeSpan(proportion), start, end, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        //change text style type
        spanText.setSpan(new StyleSpan(styleType), start, end, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spanText;

    }

    //Función que devuelve la fecha y hora del sistema
    public static String fechaHora(){

        return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(new Date());

    }

    public static int getIndexSpinner(Spinner spinner, String categoria)
    {
        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(categoria)) {
                index = i;
            }
        }
        return index;
    }

    public static String obtenerExtensionArchivo(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
    }

    public static long espacioMemoriaDisponible(String path){

        File file = new File(path);

        // Espacio libre en disco (Bytes).
        long libre = file.getFreeSpace(); //Espacio de disco no asignado / libre en bytes.

        //Retorno espacio disponible en MB
        return (libre/1024/1024);

    }

}
