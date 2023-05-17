package com.example.mytalks;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mytalks.Models.Users;
import com.example.mytalks.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    ActivitySignUpBinding binding; //object or implementation for binding
    private FirebaseAuth Auth;
    ProgressDialog pd;
    DatabaseReference ref;
    FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //FirebaseAuth
        Auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        getSupportActionBar().hide();
        // progess Box

        pd = new ProgressDialog(SignUp.this);
        pd.setTitle("Create Account");
        pd.setMessage("In Progress");


        // sign up Button
        binding.btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.show();
                Auth.createUserWithEmailAndPassword(binding.supemail.getText().toString(),binding.suppassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        pd.dismiss();
                        if (task.isSuccessful()) {
                            Users user = new Users(binding.username.getText().toString(), binding.supemail.toString(), binding.suppassword.getText().toString());
                        String id = task.getResult().getUser().getUid();
                        ref = database.getReference().child("users").child(id);
                        ref.setValue(user);

                        Toast.makeText(SignUp.this, "Regiostration Succesfully Done", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(SignUp.this,MainActivity.class);
                        startActivity(i);
                        finish();
                        }
                        else {
                        Toast.makeText(SignUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                
            }
        });

        binding.oldusertext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {;
                Intent i = new Intent(SignUp.this, SignIn.class);
                startActivity(i);
            }
        });


    }
}