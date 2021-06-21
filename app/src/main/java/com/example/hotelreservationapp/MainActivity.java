package com.example.hotelreservationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.hotelreservationapp.adapter.RecentsAdapter;
import com.example.hotelreservationapp.adapter.TopRoomsAdapter;
import com.example.hotelreservationapp.model.RecentsData;
import com.example.hotelreservationapp.model.TopRoomsData;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.signin.internal.SignInClientImpl;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener{

    TextView userName;
    RecyclerView recentRecycle, topRoomsRecycle;
    RecentsAdapter recentsAdapter;
    TopRoomsAdapter topRoomsAdapter;
    SignInButton signInButton;
    GoogleSignInClient mGoogleSignInClient;

    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        List<RecentsData> recentsDataList = new ArrayList<>();
        recentsDataList.add(new RecentsData("Underwater Bedroom","BR1","$120", R.drawable.habitacion1));
        recentsDataList.add(new RecentsData("Besto Bedroom","BR2","$300", R.drawable.habitacion2));
        recentsDataList.add(new RecentsData("Underwater Bedroom","BR3","$125", R.drawable.habitacion1));
        recentsDataList.add(new RecentsData("Besto Bedroom 2","BR4","$400", R.drawable.habitacion2));

        setRecentRecycle(recentsDataList);

        List<TopRoomsData> topRoomsDataList = new ArrayList<>();
        topRoomsDataList.add(new TopRoomsData("Underwater Bedroom","BR3","$123",R.drawable.habitacion1));
        topRoomsDataList.add(new TopRoomsData("Underwater Bedroom","BR3","$123",R.drawable.habitacion1));
        topRoomsDataList.add(new TopRoomsData("Underwater Bedroom","BR3","$123",R.drawable.habitacion1));
        topRoomsDataList.add(new TopRoomsData("Underwater Bedroom","BR3","$123",R.drawable.habitacion1));
        topRoomsDataList.add(new TopRoomsData("Underwater Bedroom","BR3","$123",R.drawable.habitacion1));
        topRoomsDataList.add(new TopRoomsData("Underwater Bedroom","BR3","$123",R.drawable.habitacion1));

        setTopRoomsRecycle(topRoomsDataList);
    }

    private void setRecentRecycle(List<RecentsData> recentsDataList){

        recentRecycle = findViewById(R.id.recent_recycle);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.HORIZONTAL, false);
        recentRecycle.setLayoutManager(layoutManager);
        recentsAdapter = new RecentsAdapter(this, recentsDataList);
        recentRecycle.setAdapter(recentsAdapter);

    }
    private void setTopRoomsRecycle(List<TopRoomsData> topRoomsDataList){

        topRoomsRecycle = findViewById(R.id.top_room_recycle);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL, false);
        topRoomsRecycle.setLayoutManager(layoutManager);
        topRoomsAdapter = new TopRoomsAdapter(this, topRoomsDataList);
        topRoomsRecycle.setAdapter(topRoomsAdapter);
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
        }
    }

    @Override
    public void onConnectionFailed(@NonNull @NotNull ConnectionResult connectionResult) {

    }


}