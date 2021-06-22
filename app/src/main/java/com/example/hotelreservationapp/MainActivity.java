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

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener{

    GlobalData data;
    TextView userName;
    ImageView userImage;
    RecyclerView recentRecycle, topRoomsRecycle;
    RecentsAdapter recentsAdapter;
    TopRoomsAdapter topRoomsAdapter;
    Button logBTN;
    SignInButton signInButton;
    GoogleSignInClient mGoogleSignInClient;
    DatabaseReference mDataBase;

    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDataBase = FirebaseDatabase.getInstance().getReference();

        logBTN = (Button)findViewById(R.id.buttonLogin);
        logBTN.setOnClickListener(v -> {
            Intent logBTN = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(logBTN);
        });

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // ...
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        signInButton = (SignInButton) findViewById(R.id.signInButton);
        signInButton.setOnClickListener(this);

        userName = (TextView) findViewById(R.id.userTextView);
        userImage = (ImageView) findViewById(R.id.IB);

        View.OnClickListener clickListener = v -> {
            if (v.equals(userImage)) {
                logout();
            }
        };
        userImage.setOnClickListener(clickListener);

        //AQUI CARGAMOS EL LISTVIEW DE EL APARTADO TOPROOMS
        getHabitaciones();
    }

    private void setRecentRecycle(List<Habitacion> habitacionList){

        recentRecycle = findViewById(R.id.recent_recycle);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.HORIZONTAL, false);
        recentRecycle.setLayoutManager(layoutManager);
        recentsAdapter = new RecentsAdapter(this, habitacionList);

        recentRecycle.setAdapter(recentsAdapter);

    }
    private void setTopRoomsRecycle(List<TopRoomsData> topRoomsDataList){

        topRoomsRecycle = findViewById(R.id.top_room_recycle);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL, false);
        topRoomsRecycle.setLayoutManager(layoutManager);
        topRoomsAdapter = new TopRoomsAdapter(this, topRoomsDataList);
        topRoomsRecycle.setAdapter(topRoomsAdapter);
    }

    public void logout(){
        FirebaseAuth.getInstance().signOut();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signInButton:
                signIn();
                break;
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            updateUI(null);
                        }
                    }
                });
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

    @Override
    public void onConnectionFailed(@NonNull @NotNull ConnectionResult connectionResult) {

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
}