package com.a01luisrene.multirecordatorio.interfaces;

import com.a01luisrene.multirecordatorio.modelos.Recordatorios;

//Cud: create, update, delete
public interface InterfaceCrud {
    //Modificar items del RecyclerView
    void agregarItem(Recordatorios recordatorios);
    void actualizarItem(Recordatorios recordatorios);
    void removerItem();

    //Cargar datos en el formulario recordatorio
    void cargarItem(Recordatorios recordatorios);
}
