package com.a01luisrene.multirecordatorio.interfaces;

import com.a01luisrene.multirecordatorio.modelos.Recordatorios;

//Cud: create, update, delete
public interface InterfaceCrud {
    public void removerItem();

    //Actualizar item
    public void actualizarItem(Recordatorios recordatorios);
}
