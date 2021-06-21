package com.example.hotelreservationapp;

import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;
import java.util.Date;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hotelreservationapp.model.Reservacion;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetailsActivity extends AppCompatActivity {

    public Date currentTime;
    public FirebaseDatabase dataFire;
    public DatabaseReference mData;
    public TextView id, nombreCuarto, numeroCuarto, precio;
    public ImageView imagenUrl;
    public Button enviar;
    public String clientName = "NombreCliente";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        currentTime = Calendar.getInstance().getTime();
        super.onCreate(savedInstanceState);
        id = (TextView) findViewById(R.id.id);
        nombreCuarto = (TextView) findViewById(R.id.nombreHabitacion);
        numeroCuarto = (TextView) findViewById(R.id.numeroHabitacion);
        precio = (TextView) findViewById(R.id.precio);
        //imagenUrl = (ImageView) findViewById(R.id.imgUrl);
        enviar = (Button) findViewById(R.id.buttonInsertar);
        mData = dataFire.getInstance().getReference();
        setContentView(R.layout.activity_details);
    }

    public void agregarReserva(){
        Reservacion r = new Reservacion("1", clientName, "nombreCuarto", "numeroCuarto", "23.7");
        mData.child("Reservaciones").child(String.valueOf(r.getReservacionId())).setValue(r);
    }

    public void enviar(View view) {
        agregarReserva();
    }
}