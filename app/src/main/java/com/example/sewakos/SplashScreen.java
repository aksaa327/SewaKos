package com.example.sewakos;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.sewakos.Autentication.LogIn;
import com.example.sewakos.PemilikKos.BottomNavbarPemilikKos;
import com.example.sewakos.PencariKos.BottomNavbarPencariKos;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashScreen extends AppCompatActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        auth = FirebaseAuth.getInstance();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser currentUser = auth.getCurrentUser();
                if (currentUser != null) {
                    if (currentUser.isEmailVerified()) {
                        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users");
                        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                                        String role = userSnapshot.child("role").getValue(String.class); // Perubahan disini, menggunakan userSnapshot
                                        if (role != null) {
                                            if (role.equals("Pemilik Kos")) {
                                                startActivity(new Intent(getApplicationContext(), BottomNavbarPemilikKos.class));
                                                return;
                                            } else if (role.equals("Pencari Kos")) {
                                                startActivity(new Intent(getApplicationContext(), BottomNavbarPencariKos.class));
                                                return;
                                            }
                                        }
                                    }
                                    Toast.makeText(getApplicationContext(), "Role tidak ditemukan", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Data pengguna tidak ditemukan", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(getApplicationContext(), "Gagal membaca data pengguna", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        startActivity(new Intent(getApplicationContext(), LogIn.class));
                    }
                } else {
                    startActivity(new Intent(getApplicationContext(), LogIn.class));
                    finish();
                }
            }
        }, 3000L);
    }
}