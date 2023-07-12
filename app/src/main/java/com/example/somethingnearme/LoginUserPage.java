package com.example.somethingnearme;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginUserPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user_page);

        // Database Connection
        FirebaseAuth databaseAuth = FirebaseAuth.getInstance();

        // Buttons
        Button loginSubmitButton = findViewById(R.id.loginSubmitButton);
        Button newUserSignupButton = findViewById(R.id.newUserSignupButton);
        // EditText
        EditText emailEdittext = findViewById(R.id.emailEdittext);
        EditText passwordEdittext = findViewById(R.id.passwordEdittext);
        // TextView
        TextView forgotPasswordTextview = findViewById(R.id.forgotPasswordTextview);


        // Direct new user to registration page
        newUserSignupButton.setOnClickListener(View -> {
            Intent newUser = new Intent(LoginUserPage.this, RegisterUserPage.class);
            startActivity(newUser);
        });


        // Handle forgot password event
        forgotPasswordTextview.setOnClickListener(View -> {
            // Handle Forgot Password Query

        });


        // Submit login credentials
        loginSubmitButton.setOnClickListener(View -> {
            // Get input from user
            String email = emailEdittext.getText().toString();
            String password = passwordEdittext.getText().toString();

            try {
                // Create database connection object
                DBHandler dbHandler = new DBHandler(LoginUserPage.this);

                if(dbHandler.isLoginValid(email, password)) {
                    Toast.makeText(LoginUserPage.this, "Login Success", Toast.LENGTH_SHORT).show();

                    // Go to Main Landing Page
                    Intent mainLandingPage = new Intent(LoginUserPage.this, MainLandingPage.class);
                    startActivity(mainLandingPage);
                    // Close login activity
                    finish();
                }
                else {
                    Toast.makeText(LoginUserPage.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }
            } catch(Exception e) {
                Toast.makeText(LoginUserPage.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("loginError" , e.getMessage());
            }

        });

    }

}