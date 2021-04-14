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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {

    TextView txt_signIn;
    EditText reg_name,reg_age,reg_email,reg_pass;
    Button btn_signup;
    FirebaseAuth auth;
    FirebaseDatabase database;
    ProgressDialog progressDialog;
    int savings=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        ProgressBar progressBar = (ProgressBar)findViewById(R.id.progress);
        Sprite doubleBounce = new Wave();
        progressBar.setIndeterminateDrawable(doubleBounce);


        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);

        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();

        reg_age=findViewById(R.id.age);
        reg_email=findViewById(R.id.email);
        reg_name=findViewById(R.id.fullName);
        reg_pass=findViewById(R.id.password);

        btn_signup=findViewById(R.id.registerUser);
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                progressDialog.show();
                String name=reg_name.getText().toString();
                String age=reg_age.getText().toString();
                String email=reg_email.getText().toString();
                String password=reg_pass.getText().toString();

                if (name.isEmpty()) {
                    progressDialog.dismiss();
                    reg_name.setError("full name is required");
                    reg_name.requestFocus();
                    return;
                }
                if (age.isEmpty()) {
                    progressDialog.dismiss();
                    reg_age.setError("age is required");
                    reg_age.requestFocus();
                    return;
                }
                if (email.isEmpty()) {
                    progressDialog.dismiss();
                    reg_email.setError("Email is required");
                    reg_email.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    progressDialog.dismiss();
                    reg_email.setError("Please provide valid email");
                    reg_email.requestFocus();
                    return;
                }
                if (password.isEmpty()) {
                    progressDialog.dismiss();
                    reg_pass.setError("Password is required");
                    reg_pass.requestFocus();
                    return;
                }
                if (password.length() < 6) {
                    progressDialog.dismiss();
                    reg_pass.setError("Minimum password length should be 6 character");
                    reg_pass.requestFocus();
                    return;
                }
                else{
                    progressDialog.dismiss();
                    auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                DatabaseReference reference=database.getReference().child("user").child(auth.getUid());
                                Users users=new Users(auth.getUid(),name,age,email,password,savings);
                                reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            progressBar.setVisibility(View.GONE);
                                            startActivity(new Intent(RegistrationActivity.this,homeActivity.class));
                                            Toast.makeText(RegistrationActivity.this, "Created", Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(RegistrationActivity.this, "error", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }else{
                                Toast.makeText(RegistrationActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }


            }
        });


        txt_signIn=findViewById(R.id.txt_signin);
        txt_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                startActivity(new Intent(RegistrationActivity.this,loginActivity.class));
                progressBar.setVisibility(View.GONE);
            }
        });

    }
}