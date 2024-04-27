package com.example.sewakos.Autentication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.sewakos.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    private TextView log_in;

    private TextInputEditText etUsername, etEmail, etPassword, etRepassword;
    private RelativeLayout button_daftar;

    private FirebaseAuth auth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        log_in = findViewById(R.id.log_in);

        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etRepassword = findViewById(R.id.etRepassword);

        button_daftar = findViewById(R.id.button_daftar);

        auth = FirebaseAuth.getInstance();

        log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this, LogIn.class));
            }
        });

        button_daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String repassword = etRepassword.getText().toString();

                if (!username.isEmpty() && !email.isEmpty() && !password.isEmpty() && !repassword.isEmpty()) {

                    if (password.length() > 6) {
                        if (repassword.equals(password)) {
                            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(Register.this, "Daftar berhasil", Toast.LENGTH_SHORT).show();

                                                    startActivity(new Intent(Register.this, LogIn.class));
                                                } else {
                                                    Toast.makeText(Register.this, "Verifikasi gagal di kirim", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    } else {
                                        Toast.makeText(Register.this, "Daftar gagal", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            etRepassword.setError("Password tidak sama!!!");
                        }
                    } else {
                        etPassword.setError("Password Harus Lebih dari 6 karakter!!!");
                    }

                } else {
                    Toast.makeText(Register.this, "Ada data yang masih kosong!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}