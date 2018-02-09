package com.example.mrticiekurs.firestoregridview;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText email, password;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.emailEditText);
        password = findViewById(R.id.passwordEditText);
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();



        findViewById(R.id.loginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

        findViewById(R.id.registerHereText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (auth.getCurrentUser() !=null){
            finish();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
    }

    private void loginUser(){

        String emailValue = email.getText().toString();
        String passwordValue = password.getText().toString();

        if (emailValue.isEmpty()){
            email.setError("Emails is needed");
            email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailValue).matches()){
            email.setError("Enter a valid email");
            email.requestFocus();
            return;
        }


        if (passwordValue.isEmpty()){
            password.setError("Password is needed");
            password.requestFocus();
            return;
        }

        //TODO: Disable pogu, ja netiek ievaditi vismaz 6 simboli
        if (passwordValue.length() < 6){
            password.setError("Minimum length of password is 6");
            password.requestFocus();
            return;
        }


        auth.signInWithEmailAndPassword(emailValue, passwordValue)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            finish();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        }else {
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }

}
