package com.example.hotelreservationapp;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    public TextView txtnombreCuarto, txtnumeroCuarto, txtprecio, txttipoHabitacion, txtDescripcion, txtDisponibilidad;
    public EditText txtnumeroPersonas, fechaReserva, fechaSalida;
    public DatePickerDialog picker;
    public ImageView ivimagenUrl;
    public Button btnenviar;


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
        txtDescripcion =  (TextView) findViewById(R.id.informacion);
        txtDisponibilidad = (TextView) findViewById(R.id.disponiblilidad);
        txtnumeroPersonas = (EditText) findViewById(R.id.numeroPersonas);
        fechaReserva = (EditText) findViewById(R.id.fechaEntrada);
        fechaReserva.setInputType(InputType.TYPE_NULL);
        fechaSalida = (EditText) findViewById(R.id.fechaSalida);
        fechaSalida.setInputType(InputType.TYPE_NULL);
        ivimagenUrl = (ImageView) findViewById(R.id.imgUrl);
        btnenviar = (Button) findViewById(R.id.buttonInsertar);
        mData = dataFire.getInstance().getReference();

        data = (GlobalData) getApplicationContext();
        List<Habitacion> datos = data.getDatos();
        Habitacion dato = datos.get(data.getIdSeleccionado());

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
        txtDescripcion.setText(caracteristicas);
        txtDisponibilidad.setText(disponibilidad);
        Picasso.get().load(url).into(ivimagenUrl);

        fechaReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                //datepicker dialog
                picker = new DatePickerDialog(DetailsActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                fechaReserva.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
        fechaSalida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                //datepicker dialog
                picker = new DatePickerDialog(DetailsActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                fechaSalida.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
    }

    public void agregarReserva(){
        Reservacion r = new Reservacion(nombreCliente,fechaReserva.getText().toString(), fechaSalida.getText().toString(), txtnumeroPersonas.getText().toString(),txtnombreCuarto.getText().toString());
        mData.child("Reservaciones").push().setValue(r);
    }

    public void enviar(View view) {
        agregarReserva();
        Toast.makeText(data, "Habitacion reservada exitosamente!", Toast.LENGTH_LONG).show();
        super.onBackPressed();
    }
}