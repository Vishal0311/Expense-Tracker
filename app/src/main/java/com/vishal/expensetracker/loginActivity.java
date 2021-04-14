package com.vishal.expensetracker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Wave;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginActivity extends AppCompatActivity {

    TextView txt_signup;
    EditText login_email,login_password;
    Button signIn_btn;
    FirebaseAuth auth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //for progress bar
        ProgressBar progressBar = (ProgressBar)findViewById(R.id.progress);
        Sprite doubleBounce = new Wave();
        progressBar.setIndeterminateDrawable(doubleBounce);

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);


        auth=FirebaseAuth.getInstance();

        txt_signup=findViewById(R.id.txt_signup);
        login_email=findViewById(R.id.email);
        login_password=findViewById(R.id.password);
        signIn_btn=findViewById(R.id.signIn);

        signIn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                progressDialog.show();
                String email=login_email.getText().toString();
                String password=login_password.getText().toString();

                if(email.isEmpty()){
                    progressDialog.dismiss();
                    login_email.setError("Email is required");
                    login_email.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    progressDialog.dismiss();
                    login_email.setError("Please provide valid email");
                    login_email.requestFocus();
                    return;
                }
                if(password.isEmpty()){
                    progressDialog.dismiss();
                    login_password.setError("Password is required");
                    login_password.requestFocus();
                    return;
                }
                if(password.length()<6){
                    progressDialog.dismiss();
                    login_password.setError("Minimum password length should be 6 character");
                    login_password.requestFocus();
                    return;
                }
                else{
                    progressDialog.dismiss();
                    auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                progressBar.setVisibility(View.GONE);
                                startActivity(new Intent(loginActivity.this,homeActivity.class));
                            }else{
                                Toast.makeText(loginActivity.this, "Error in login", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }




            }
        });

        txt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                startActivity(new Intent(loginActivity.this,RegistrationActivity.class));
                progressBar.setVisibility(View.GONE);
            }
        });

    }
}