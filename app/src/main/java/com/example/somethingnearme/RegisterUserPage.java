package com.example.somethingnearme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

public class RegisterUserPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user_page);

        // Buttons
        Button oldUserLoginButton = findViewById(R.id.oldUserLoginButton);
        Button signupSubmitButton = findViewById(R.id.signupSubmitButton);

        // EditText
        EditText newEmailEdittext = findViewById(R.id.newEmailEdittext);
        EditText newPasswordEdittext = findViewById(R.id.newPasswordEdittext);
        EditText confirmPasswordEdittext = findViewById(R.id.confirmPasswordEdittext);

        // Go to login Page
        oldUserLoginButton.setOnClickListener(View -> {
            Intent loginPage = new Intent(RegisterUserPage.this, LoginUserPage.class);
            startActivity(loginPage);
            // Close this activity
            finish();
        });


        // Handle new user registration
        signupSubmitButton.setOnClickListener(View -> {
            // Database class object
            DBHandler dbHandler = new DBHandler(RegisterUserPage.this);

            String email = newEmailEdittext.getText().toString();
            String password = newPasswordEdittext.getText().toString();
            String confirmPassword = confirmPasswordEdittext.getText().toString();

            if(password.equals(confirmPassword)) {
                // Add user to database
                dbHandler.addNewUser(email, password);

                // Close this activity
                // Return back to login page
                finish();
            }
            else {
                Toast.makeText(RegisterUserPage.this, "Password doesn't match. Please check.", Toast.LENGTH_SHORT).show();
            }
        });
    }

}