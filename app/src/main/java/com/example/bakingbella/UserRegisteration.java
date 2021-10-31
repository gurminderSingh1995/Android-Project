package com.example.bakingbella;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserRegisteration extends AppCompatActivity {
    EditText txtFirstName, txtLastName, txtUserEmail, txtPassword, txtConfirmPassword, txtPhoneNumber,txtUsername;
    Button btnUserRegister;
    TextView txtRegisterLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registeration);

        txtFirstName = findViewById(R.id.txtFirstName);
        txtLastName = findViewById(R.id.txtLastName);
        txtUserEmail = findViewById(R.id.txtUserEmail);
        txtPassword = findViewById(R.id.txtPassword);
        txtConfirmPassword = findViewById(R.id.txtConfirmPassword);
        btnUserRegister = findViewById(R.id.btnUserRegister);
        txtRegisterLine = findViewById(R.id.txtRegisterLine);
        txtPhoneNumber = findViewById(R.id.txtPhoneNumber);
        txtUsername = findViewById(R.id.txtUsername);
        Intent userLogin = new Intent(UserRegisteration.this, UserLogin.class);

        btnUserRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference reference = database.getReference("users");
                String firstName = txtFirstName.getText().toString().trim();
                String lastName = txtLastName.getText().toString().trim();
                String phone = txtPhoneNumber.getText().toString().trim();
                String email = txtUserEmail.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();
                String confirmPassword = txtConfirmPassword.getText().toString().trim();
                String username = txtUsername.getText().toString().trim();
                Log.i("p", password);
                Log.i("cp", confirmPassword);

                    if( validatePassword() & validatePhoneNo() & validateEmail() & validateFirstname() & validateUsername()) {


                        if (password.equals(confirmPassword)) {
                            UserModel userModel = new UserModel(firstName, lastName, phone, email, password, username);
                            reference.child(username).setValue(userModel);
                            Toast.makeText(UserRegisteration.this, "Successfully registered", Toast.LENGTH_LONG).show();
                            startActivity(userLogin);
                        } else {
                            txtConfirmPassword.setError("Password mismatch!");
                        }
                    }
            }
        });
    }
    private Boolean validateEmail() {
        String val = txtUserEmail.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            txtUserEmail.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            txtUserEmail.setError("Invalid email address");
            return false;
        } else {
            txtUserEmail.setError(null);
//            txtUserEmail.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePhoneNo() {
        String val = txtPhoneNumber.getText().toString().trim();

        if (val.isEmpty()) {
            txtPhoneNumber.setError("Field cannot be empty");
            return false;
        } else {
            txtPhoneNumber.setError(null);
          //  txtPhoneNumber.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validatePassword() {
        String val = txtPassword.getText().toString();
        String passwordVal = "^" +
                "(?=.*[0-9])" +         //at least 1 digit
                "(?=.*[a-z])" +         //at least 1 lower case letter
                "(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if (val.isEmpty()) {
            txtPassword.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            txtPassword.setError("Password is too weak");
            return false;
        } else {
            txtPassword.setError(null);
          //  txtPassword.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validateFirstname() {
        String val = txtFirstName.getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";

        if (val.isEmpty()) {
            txtFirstName.setError("Field cannot be empty");
            return false;
        } else if (val.length() >= 15) {
            txtFirstName.setError("Username too long");
            return false;
        } else if (!val.matches(noWhiteSpace)) {
            txtFirstName.setError("White Spaces are not allowed");
            return false;
        } else {
            txtFirstName.setError(null);
           // regUsername.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validateUsername() {
        String val = txtUsername.getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";

        if (val.isEmpty()) {
            txtUsername.setError("Field cannot be empty");
            return false;
        } else if (val.length() >= 15) {
            txtUsername.setError("Username too long");
            return false;
        } else if (!val.matches(noWhiteSpace)) {
            txtUsername.setError("White Spaces are not allowed");
            return false;
        } else {
            txtUsername.setError(null);
            return true;
        }
    }
}