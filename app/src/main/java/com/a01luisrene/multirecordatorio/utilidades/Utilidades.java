package com.a01luisrene.multirecordatorio.utilidades;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;

/**
 * Created by LUIS on 09/08/2017.
 */

public class Utilidades {

    public static boolean smartphone = true;


    /**
     * this method sets custom style text
     *
     * @param context
     * @param text
     * @param start
     * @param end
     * @param color
     * @param proportion
     * @param styleType
     * @return
     */
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
}
