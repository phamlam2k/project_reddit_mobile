package com.example.userinterface.user;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.userinterface.MainActivity;
import com.example.userinterface.R;
import com.example.userinterface.api.ApiService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogIn extends AppCompatActivity implements View.OnClickListener {

    private Button btnHome;
    private TextView btnRegister, btnResetPassword;
    private EditText emailEdit, passwordEdit;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        btnHome = findViewById(R.id.btn_sign_in);

        btnRegister = findViewById(R.id.btn_sign_up);

        emailEdit = findViewById(R.id.emailEdit);
        btnResetPassword = findViewById(R.id.forgotPassword);
        passwordEdit = findViewById(R.id.passwordEdit);

        progressBar = findViewById(R.id.loadingLogin);

        mAuth = FirebaseAuth.getInstance();

        btnHome.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        btnResetPassword.setOnClickListener(this);
    }

    public void onRegister(){
        Intent intent = new Intent();
        intent.setClass(this, SignUp.class);
        startActivity(intent);
    }

    public void userLogin() {
        String email = emailEdit.getText().toString().trim();
        String password = passwordEdit.getText().toString().trim();

        if(email.isEmpty()){
            emailEdit.setError("Emai is required!");
            emailEdit.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    Intent intent = new Intent();
                    intent.setClass(LogIn.this, MainActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(LogIn.this, "Wrong email or password", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    public void onClickForgotPassword(){

        FirebaseAuth auth = FirebaseAuth.getInstance();
        String email = emailEdit.getText().toString().trim();

        if(email.isEmpty()){
            Toast.makeText(LogIn.this, "Please fill the email input", Toast.LENGTH_SHORT).show();

        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(LogIn.this, "Your email is not valid", Toast.LENGTH_SHORT).show();
        }
        else{
            auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(LogIn.this, "Email sending ...", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.forgotPassword:
                onClickForgotPassword();
                break;
            case R.id.btn_sign_in:
                userLogin();
                break;
            case R.id.btn_sign_up:
                onRegister();
                break;
            default:
                break;
        }
    }
}