package com.example.hotelreservationapp;

import android.app.Application;
import com.example.hotelreservationapp.model.RecentsData;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class GlobalData extends Application {
    List<RecentsData> datos;
    int idSeleccionado;
    FirebaseUser currentUser;

    public GlobalData(){

    }

    public List<RecentsData> getDatos() {return datos;}

    public void setDatos(List<RecentsData> datos) {this.datos = datos;}

    public int getIdSeleccionado() {return idSeleccionado;}

    public void setIdSeleccionado(int idSeleccionado) {this.idSeleccionado = idSeleccionado;}

    public FirebaseUser getCurrentUser() {return currentUser;}

    public void setCurrentUser(FirebaseUser currentUser) {this.currentUser = currentUser;}
}
