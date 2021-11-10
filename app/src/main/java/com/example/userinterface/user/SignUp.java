package com.example.userinterface.user;

import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.userinterface.R;
import com.example.userinterface.post.Comment;
import com.example.userinterface.post.Post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    private TextView btnLogin;
    private Button btnSignUp;
    FirebaseDatabase rootNode;
    FirebaseAuth mAuth;
    DatabaseReference reference;

    EditText email, password, confirmPassword;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_up);

        email = findViewById(R.id.emailEdit);
        password = findViewById(R.id.passwordEdit);
        confirmPassword = findViewById(R.id.passwordCofirmEdit);

        progressBar = findViewById(R.id.loadingSignUp);

        btnLogin = findViewById(R.id.btn_switch_login);
        btnSignUp = findViewById(R.id.signup);

        mAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
    }

    public  void onLogin (){
        Intent intent = new Intent();
        intent.setClass(this, LogIn.class);
        startActivity(intent);
    }

    public void onSignUp(){
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("users");

        String emailUser = email.getText().toString().trim();
        String passwordUser = password.getText().toString().trim();
        String passwordConfim = confirmPassword.getText().toString().trim();

        if(emailUser.isEmpty()){
            email.setError("Email is required");
            email.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(emailUser).matches()){
            email.setError("Please enter a valid email!");
            email.requestFocus();
            return;
        }

        if(passwordUser.isEmpty()){
            password.setError("Password is required");
            password.requestFocus();
            return;
        }

        if(passwordUser.length() < 7){
            password.setError("Password must be more than 7");
            password.requestFocus();
            return;
        }

        if(!passwordConfim.equals(passwordUser)){
            confirmPassword.setError("Password must be more than 7");
            confirmPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(emailUser, passwordUser)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User("unknow", emailUser, passwordUser, true);

                            FirebaseDatabase.getInstance().getReference("users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(SignUp.this, "User has been registered successfully!", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent();
                                        intent.setClass(SignUp.this, LogIn.class);
                                        startActivity(intent);
                                        finishAffinity();
                                    }else{
                                        Toast.makeText(SignUp.this, "Failed to register!Try again", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }else {
                            Toast.makeText(SignUp.this, "Failed to register", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_switch_login:
                onLogin();
                break;
            case R.id.signup:
                onSignUp();
                break;
        }
    }
}