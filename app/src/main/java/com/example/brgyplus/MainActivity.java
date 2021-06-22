package com.example.brgyplus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {

    TextView toSignUpBtn, forgotPassword;
    EditText userEmail, userPassword;
    Button signInBtn;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    CheckBox rememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_BrgyPlus);
        setContentView(R.layout.activity_main);

        toSignUpBtn = findViewById(R.id.to_sign_up);
        userEmail = findViewById(R.id.email);
        userPassword = findViewById(R.id.password);
        signInBtn = findViewById(R.id.SignInButton);
        forgotPassword = findViewById(R.id.forgot_password);
        rememberMe = findViewById(R.id.rememberMeBox);

        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

        toSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Register.class));
                finish();
            }
        });

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = userEmail.getText().toString().trim();
                String password = userPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    userEmail.setError("Email is required!");
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

                progressBar.setVisibility(View.VISIBLE);

                // authenticate user

                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Logged in successfully!", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), Home.class));
                        }else{
                            Toast.makeText(MainActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText resetMail = new EditText(v.getContext());
                final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password?");
                passwordResetDialog.setMessage("Enter Email to receive reset link!");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // extract the email and send reset link

                        String mail = resetMail.getText().toString();
                        mAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(MainActivity.this, "Reset Link has been sent to your email!", Toast.LENGTH_LONG).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull @NotNull Exception e) {
                                Toast.makeText(MainActivity.this, "Error! Reset Link not sent " + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });

                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // close dialog
                    }
                });

                passwordResetDialog.create().show();
            }
        });
    }
}