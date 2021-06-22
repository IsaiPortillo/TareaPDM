package com.example.hotelreservationapp;

import android.app.Application;

import com.example.hotelreservationapp.model.Cliente;
import com.example.hotelreservationapp.model.Habitacion;

import java.util.List;

public class GlobalData extends Application {
    List<Habitacion> datos;
    int idSeleccionado;
    Cliente currentUser;

    public GlobalData(){

    }

    public List<Habitacion> getDatos() {return datos;}

    public void setDatos(List<Habitacion> datos) {this.datos = datos;}

    public int getIdSeleccionado() {return idSeleccionado;}

    public void setIdSeleccionado(int idSeleccionado) {this.idSeleccionado = idSeleccionado;}

    public Cliente getCurrentUser() {return currentUser;}

    public void setCurrentUser(Cliente currentUser) {this.currentUser = currentUser;}
}
