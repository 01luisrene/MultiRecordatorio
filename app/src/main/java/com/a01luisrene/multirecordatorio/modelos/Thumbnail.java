package com.a01luisrene.multirecordatorio.modelos;

/**
 * Created by LUIS on 04/07/2017.
 */

public class Thumbnail {

    private String path;
    private String name;

    public String getFullPath(){

        return path + name;

    }
}
