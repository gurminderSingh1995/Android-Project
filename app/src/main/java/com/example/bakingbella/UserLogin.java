package com.example.bakingbella;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class UserLogin extends AppCompatActivity {
    TextView txtforgotpassword, txtRegisterLine, txtUsernamelogin;
    EditText txtUserEmail, txtUserPassword,txtUserPhone;
    Button btnUserLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);


      //  txtUserPhone = findViewById(R.id.txtUserPhone);
        txtforgotpassword = findViewById(R.id.txtforgotpassword);
        txtRegisterLine = findViewById(R.id.txtRegisterLine);
//        txtUserEmail = findViewById(R.id.txtUserEmail);
        txtUsernamelogin = findViewById(R.id.txtUsernamelogin);
        txtUserPassword = findViewById(R.id.txtUserPassword);
        btnUserLogin = findViewById(R.id.btnUserLogin);

        Intent register = new Intent(UserLogin.this, UserRegisteration.class);
        txtforgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UserLogin.this, "clicked!",Toast.LENGTH_LONG).show();
            }
        });
        txtRegisterLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(register);
            }
        });
        Intent homeIntent = new Intent(UserLogin.this, MainActivity.class);

        btnUserLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateUsername() && validatePassword()){
                  //  String userEnteredEmail = txtUserEmail.getText().toString().trim();
                    String userEnteredUsername = txtUsernamelogin.getText().toString().trim();
                    // final String userEnteredPhone = txtUserPhone.getText().toString().trim();
                    String userEnteredPassword = txtUserPassword.getText().toString().trim();
                    Log.i("email", userEnteredUsername);
                    Log.i("pass", userEnteredPassword);
//                    Log.i("email2", userEnteredEmail);
//                    Log.i("pass2", userEnteredPassword);

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference reference = database.getReference("users");

                    Query checkUser = reference.orderByChild("username").equalTo(userEnteredUsername);
                    checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {


                            Log.i("snapshot",snapshot.toString());
                            if (snapshot.exists()){
                                txtUsernamelogin.setError(null);
                                String databasePassword = snapshot.child(userEnteredUsername).child("password").getValue(String.class);
                                Log.i("DatabasePassword",databasePassword);
                                if (databasePassword.equals(userEnteredPassword)){
                                    txtUsernamelogin.setError(null);
                                    Toast.makeText(UserLogin.this,"Correct Password",Toast.LENGTH_SHORT).show();
                                    homeIntent.putExtra("username", userEnteredUsername);
//                                    String firstNameDb = snapshot.child(userEnteredEmail).child("firstName").getValue(String.class);
//                                    String lastNameDb = snapshot.child(userEnteredEmail).child("lastName").getValue(String.class);
//                                    String phoneDb = snapshot.child(userEnteredEmail).child("phone").getValue(String.class);
                                    startActivity(homeIntent);
                                }else{
                                    Toast.makeText(UserLogin.this,"Incorrect Password",Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                Toast.makeText(UserLogin.this,"No such user exists",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }


    private Boolean validatePassword() {
        String val = txtUserPassword.getText().toString().trim();
        if (val.isEmpty()) {
            txtUserPassword.setError("Field cannot be empty");
            return false;
        } else {
            txtUserPassword.setError(null);
            //txtUserPassword.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateEmail() {
        String val = txtUserEmail.getText().toString().trim();


        if (val.isEmpty()) {
            txtUserEmail.setError("Field cannot be empty");
            return false;
        } else {
            txtUserEmail.setError(null);
//            txtUserEmail.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validateUsername() {
        String val = txtUsernamelogin.getText().toString();

        if (val.isEmpty()) {
            txtUsernamelogin.setError("Field cannot be empty");
            return false;
        }  else {
            txtUsernamelogin.setError(null);
            return true;
        }
    }
}