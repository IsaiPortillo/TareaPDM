package com.example.hotelreservationapp;

import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hotelreservationapp.model.RecentsData;
import com.example.hotelreservationapp.model.Reservacion;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetailsActivity extends AppCompatActivity {

    private GlobalData data;
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
        setContentView(R.layout.activity_details);

        setContentView(R.layout.activity_details);

        id = (TextView) findViewById(R.id.idHabitacion);
        nombreCuarto = (TextView) findViewById(R.id.nombreHabitacion);
        numeroCuarto = (TextView) findViewById(R.id.numeroHabitacion);
        precio = (TextView) findViewById(R.id.precio);
        imagenUrl = (ImageView) findViewById(R.id.imgUrl);
        enviar = (Button) findViewById(R.id.buttonInsertar);
        mData = dataFire.getInstance().getReference();


        data = (GlobalData) getApplicationContext();
        List<RecentsData> datos = data.getDatos();
        RecentsData dato = datos.get(data.getIdSeleccionado());
        id.setText(dato.getId());
        nombreCuarto.setText(dato.getBedroomName());
        numeroCuarto.setText(dato.getBedroomNumber());
        precio.setText(dato.getPrice());

    }

    public void agregarReserva(){
        Reservacion r = new Reservacion(id.getText().toString(), clientName, nombreCuarto.getText().toString(), numeroCuarto.getText().toString(), precio.getText().toString());
        mData.child("Reservaciones").child(String.valueOf(r.getReservacionId())).setValue(r);
    }

    public void enviar(View view) {
        agregarReserva();
    }
}