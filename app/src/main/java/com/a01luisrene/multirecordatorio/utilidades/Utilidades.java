package com.a01luisrene.multirecordatorio.utilidades;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by LUIS on 09/08/2017.
 */

public class Utilidades {

    public static boolean smartphone = true;


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

}
