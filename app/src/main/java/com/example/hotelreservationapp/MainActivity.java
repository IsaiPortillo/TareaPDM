package com.example.hotelreservationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hotelreservationapp.adapter.RecentsAdapter;
import com.example.hotelreservationapp.adapter.TopRoomsAdapter;
import com.example.hotelreservationapp.model.Habitacion;
import com.example.hotelreservationapp.model.TopRoomsData;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    GlobalData data;
    TextView userName;
    ImageView userImage;
    RecyclerView recentRecycle;
    RecentsAdapter recentsAdapter;
    Button logBTN;
    DatabaseReference mDataBase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDataBase = FirebaseDatabase.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        logBTN = (Button)findViewById(R.id.buttonLogin);
        logBTN.setOnClickListener(v -> {
            Intent logBTN = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(logBTN);
        });
        userName = (TextView) findViewById(R.id.userTextView);
        userImage = (ImageView) findViewById(R.id.IB);

        updateUI(user);

        View.OnClickListener clickListener = v -> {
            if (v.equals(userImage)) {
                logout();
                updateUI(user);
            }
        };
        userImage.setOnClickListener(clickListener);

        //AQUI CARGAMOS EL LISTVIEW DE EL APARTADO TOPROOMS
        getHabitaciones();
    }

    @Override
    public void onResume(){
        super.onResume();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        updateUI(user);

    }
    private void setRecentRecycle(List<Habitacion> habitacionList){

        recentRecycle = findViewById(R.id.recent_recycle);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.HORIZONTAL, false);
        recentRecycle.setLayoutManager(layoutManager);
        recentsAdapter = new RecentsAdapter(this, habitacionList);

        recentRecycle.setAdapter(recentsAdapter);

    }

    private void updateUI(FirebaseUser currentUser) {
        if(currentUser != null){
            userName.setText("Bienvenido: " + currentUser.getDisplayName());
            Picasso.get().load(currentUser.getPhotoUrl()).into(userImage);

        }else{
            userName.setText("Guest");
            userImage.setImageResource(R.drawable.profile);
        }
    }
    public void getHabitaciones(){
        List<Habitacion> habitaciones = new ArrayList<>();
        mDataBase.child("Habitacion").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot habitacion: snapshot.getChildren()){
                        String caracteristicas = habitacion.child("caracteristicas").getValue().toString();
                        String disponibilidad = habitacion.child("disponibilidad").getValue().toString();
                        String nombreHabitacion = habitacion.child("nombreHabitacion").getValue().toString();
                        String numeroHabitacion = habitacion.child("numeroHabitacion").getValue().toString();
                        String numeroPiso = habitacion.child("numeroPiso").getValue().toString();
                        String precio = habitacion.child("precio").getValue().toString();
                        String tipoHabitacion = habitacion.child("tipoHabitacion").getValue().toString();
                        String urlImagen = habitacion.child("urlImagen").getValue().toString();
                        habitaciones.add(new Habitacion(nombreHabitacion, tipoHabitacion, Integer.parseInt(numeroHabitacion), Double.parseDouble(precio), caracteristicas, Integer.parseInt(numeroPiso), disponibilidad, urlImagen));
                    }
                    data = (GlobalData) getApplicationContext();
                    data.setDatos(habitaciones);
                    setRecentRecycle(habitaciones);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    public void logout(){
        FirebaseAuth.getInstance().signOut();
    }
}