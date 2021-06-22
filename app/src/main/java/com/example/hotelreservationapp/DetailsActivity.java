package com.example.hotelreservationapp;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hotelreservationapp.model.Habitacion;
import com.example.hotelreservationapp.model.Reservacion;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    private GlobalData data;
    public Date currentTime;
    public FirebaseDatabase dataFire;
    public DatabaseReference mData;
    public TextView txtnombreCuarto, txtnumeroCuarto, txtprecio, txttipoHabitacion;
    public EditText txtnumeroPersonas;
    public DatePicker txtfechaReserva, txtfechaSalida;
    public ImageView ivimagenUrl;
    public Button btnenviar;


    String idHabitacion;
    String nombreHabitacion;
    public String nombreCliente = "NombreCliente";
    int numeroHabitacion;
    String tipoHabitacion;
    double precio;
    String caracteristicas;
    int numeroPiso;
    int numeroPersonas;
    String disponibilidad;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        setContentView(R.layout.activity_details);

        txtnombreCuarto = (TextView) findViewById(R.id.nombreHabitacion);
        txtnumeroCuarto = (TextView) findViewById(R.id.numeroHabitacion);
        txtprecio = (TextView) findViewById(R.id.precio);
        txttipoHabitacion = (TextView) findViewById(R.id.tipoHabitacion);
        txtnumeroPersonas = (EditText)   findViewById(R.id.numeroPersonas);
        ivimagenUrl = (ImageView) findViewById(R.id.imgUrl);
        btnenviar = (Button) findViewById(R.id.buttonInsertar);
        mData = dataFire.getInstance().getReference();


        data = (GlobalData) getApplicationContext();
        List<Habitacion> datos = data.getDatos();
        Habitacion dato = datos.get(data.getIdSeleccionado());

        idHabitacion = dato.getId();
        nombreHabitacion = dato.getBedroomName();
        numeroHabitacion = dato.getBedroomNumber();
        precio = dato.getPrice();
        tipoHabitacion = dato.getBedroomClass();
        caracteristicas = dato.getCharacteristics();
        numeroPiso = dato.getFloorNumber();
        disponibilidad = dato.getAvailability();
        url = dato.getImageUrl();

        txtnombreCuarto.setText(nombreHabitacion);
        txtnumeroCuarto.setText(Integer.toString(numeroHabitacion));
        txtprecio.setText(Double.toString(precio));
        txttipoHabitacion.setText(tipoHabitacion);
        txtnumeroPersonas.setText(Integer.toString(numeroPersonas));
        Picasso.get().load(url).into(ivimagenUrl);
    }

    public void agregarReserva(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateEntrada = sdf.format(txtfechaReserva);
        String dateSalida = sdf.format(txtfechaSalida);

        Reservacion r = new Reservacion(idHabitacion,nombreCliente,dateEntrada, dateSalida, txtnumeroPersonas.getText().toString(),txtnombreCuarto.getText().toString());
        mData.child("Reservaciones").child(String.valueOf(r.getReservacionId())).setValue(r);
    }

    public void enviar(View view) {
        agregarReserva();
    }
}