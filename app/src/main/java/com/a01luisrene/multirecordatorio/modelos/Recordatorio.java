package com.a01luisrene.multirecordatorio.modelos;

/**
 * Created by LUIS on 04/06/2017.
 */

public class Recordatorio {
    private String id;
    private String title;
    private String name;
    private String contentMessage;

    public Recordatorio() {

    }

    public Recordatorio(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public Recordatorio(String id, String title, String name, String contentMessage) {
        this.id = id;
        this.title = title;
        this.name = name;
        this.contentMessage = contentMessage;
    }

    /*
    *
    * GETTER & SETTER
    *
    * */

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContentMessage() {
        return contentMessage;
    }

    public void setContentMessage(String contentMessage) {
        this.contentMessage = contentMessage;
    }
}
