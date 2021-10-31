package com.example.bakingbella;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class AdminLogin extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    EditText emailAdmin, txtPassword;
    Button btnAdminLogin;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        mAuth = FirebaseAuth.getInstance();

        emailAdmin = findViewById(R.id.emailAdmin);
        txtPassword = findViewById(R.id.txtPassword);
        btnAdminLogin = findViewById(R.id.btnAdminLogin);
        pd = new ProgressDialog(AdminLogin.this);
     //   Intent homeIntent = new Intent(AdminLogin.this, MainActivity.class);
        Intent homeIntent = new Intent(AdminLogin.this, AdminHome.class);
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null) {
                    Log.i("info","Signed in "+ user.getUid());

                }
                else{
                    Log.i("info","nothing");
                }
            }
        };


        btnAdminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.dismiss();
                pd.setCancelable(true);
                String email =  emailAdmin.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();

                if (validateEmail() && validatePassword()) {

                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(AdminLogin.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                pd.setMessage("Signing in");
                                pd.show();
                                pd.setCancelable(true);
                                // Sign in success, update UI with the signed-in user's information
                                //   Log.d("", "signInWithEmail:success");
//                                FirebaseUser user = mAuth.getCurrentUser();
                                emailAdmin.setText("");
                                txtPassword.setText("");
                                startActivity(homeIntent);

                                //     updateUI(user);
                            } else {
                                pd.dismiss();
                                // If sign in fails, display a message to the user.
                                //    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(AdminLogin.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                //   updateUI(null);
                            }
                            // ...
                        }

                    });
            }
//            else {
//                    Toast.makeText(AdminLogin.this, "You didn't fill all the fields.", Toast.LENGTH_SHORT).show();
//                }
            }
        });

    }
    @Override
    public void onBackPressed() {
        pd.dismiss();
    };
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

      //  updateUI(currentUser);

    }
    private Boolean validatePassword() {
        String val = txtPassword.getText().toString().trim();
        if (val.isEmpty()) {
            txtPassword.setError("Field cannot be empty");
            return false;
        } else {
            txtPassword.setError(null);
            //txtUserPassword.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateEmail() {
        String val = emailAdmin.getText().toString().trim();


        if (val.isEmpty()) {
            emailAdmin.setError("Field cannot be empty");
            return false;
        } else {
            emailAdmin.setError(null);
//            txtUserEmail.setErrorEnabled(false);
            return true;
        }
    }
}