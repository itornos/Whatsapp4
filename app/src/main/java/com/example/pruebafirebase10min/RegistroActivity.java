package com.example.pruebafirebase10min;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistroActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    EditText Username, Email, Password, ConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regitro);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Username = findViewById(R.id.Username);
        Email = findViewById(R.id.Email);
        Password = findViewById(R.id.Password);
        ConfirmPassword = findViewById(R.id.ConfirmPassword);
    }


    public void tryRegistro(View v){
        String username = Username.getText().toString();
        String email = Email.getText().toString();
        String password = Password.getText().toString();
        String confirmPass = ConfirmPassword.getText().toString();

        if(username.isEmpty() || username.length() < 5){
            showError(Username,"Username no valido");

        }else if (email.isEmpty() || !email.contains("@")){
            showError(Email,"Email no valido");

        }else if (password.isEmpty() || password.length() < 7){
            showError(Password,"Clave no valida minimo 7 caracteres");

        }else if (confirmPass.isEmpty() || !confirmPass.equals(password)){
            showError(ConfirmPassword,"Clave no valida, no coincide.");

        }else{
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Usuario user = new Usuario(username, email, password);
                        mDatabase.child("users").child(username).setValue(user);

                        Intent intent = new Intent(RegistroActivity.this, MainActivity.class);
                        intent.putExtra("registro", true);
                        startActivity(intent);

                    }else{
                        showToast("Error en el Registro: " + task.getException().getMessage());
                    }
                }
            });
        }
    }

    private void showError(EditText input, String s){
        input.setError(s);
        input.requestFocus();
    }

    public void goLogin(View v){
        Intent intent = new Intent(RegistroActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void showToast(String message) {
        Toast toast = new Toast(RegistroActivity.this);
        View view = LayoutInflater.from(RegistroActivity.this).inflate(R.layout.toast_layout, null);
        TextView tvMessage = view.findViewById(R.id.tvMessage);
        tvMessage.setText(message);
        toast.setView(view);
        toast.setGravity(Gravity.TOP, 0, 100);
        toast.show();
    }
}