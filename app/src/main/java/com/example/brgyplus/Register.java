package com.example.brgyplus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.brgyplus.user.Home;
import com.example.brgyplus.user.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class Register extends AppCompatActivity {

    FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView toSignInBtn;
    EditText userFirstName, userLastName, userEmail, userPassword, confirmPassword;
    Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_BrgyPlus);
        setContentView(R.layout.activity_register);

        userFirstName = findViewById(R.id.first_name);
        userLastName = findViewById(R.id.last_name);
        userEmail = findViewById(R.id.email);
        userPassword = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirm_password);
        toSignInBtn = findViewById(R.id.to_sign_in);
        registerBtn = findViewById(R.id.register_button);

        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

        // Checks if user is already registered
        if(mAuth.getCurrentUser() != null){
            userEmail.setError("Email is already exists!");
            userEmail.requestFocus();
            finish();
        }

        toSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstname = userFirstName.getText().toString().trim();
                String lastname = userLastName.getText().toString().trim();
                String email = userEmail.getText().toString().trim();
                String password = userPassword.getText().toString().trim();
                String confirmPass = confirmPassword.getText().toString().trim();

                String userType = "user";

                checkString(firstname, lastname, email, password, confirmPass);

                progressBar.setVisibility(View.VISIBLE);

                // register user to Firebase
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(firstname, lastname, email, userType);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(Register.this, "User has been created!", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                        // redirect to home page
                                        startHomeActivity();
                                    } else {
                                        Toast.makeText(Register.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });

                        }else{
                            Toast.makeText(Register.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

    }

    private void startHomeActivity(){
        Intent intent = new Intent(this, Home.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void checkString(String firstname, String lastname, String email, String password, String confirmPass){
        if(TextUtils.isEmpty(firstname)){
            userFirstName.setError("First Name is required!");
            userFirstName.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(lastname)){
            userLastName.setError("Last Name is required!");
            userLastName.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(email)){
            userEmail.setError("Email is required!");
            userEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            userEmail.setError("Email ID is invalid!");
            userEmail.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(password)){
            userPassword.setError("Password is required!");
            userPassword.requestFocus();
            return;
        }

        if(password.length() < 6){
            userPassword.setError("Password must be 6 characters long!");
            userPassword.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(confirmPass)){
            confirmPassword.setError("Password is required!");
            confirmPassword.requestFocus();
            return;
        }

        if(!password.equals(confirmPass)){
            confirmPassword.setError("Passwords must match!");
            confirmPassword.requestFocus();
            return;
        }
    }
}