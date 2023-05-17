package com.example.mytalks;

import static com.google.android.gms.auth.api.signin.GoogleSignIn.getSignedInAccountFromIntent;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mytalks.Models.Users;
import com.example.mytalks.databinding.ActivitySignInBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignIn extends AppCompatActivity {
    ActivitySignInBinding binding;
    ProgressDialog pd;
    private FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference ref;
    private GoogleSignInClient mGoogleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database= FirebaseDatabase.getInstance(); //Realtime database instance
        auth = FirebaseAuth.getInstance(); //firebase instance
        pd = new ProgressDialog(SignIn.this);
        pd.setTitle("Sign-in");
        pd.setMessage("Verifying");
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient= GoogleSignIn.getClient(this,gso); //Build a GoogleSignInClient with the options specified by gso.
        binding.btnsignin.setOnClickListener(view -> {
            pd.show();
            auth.signInWithEmailAndPassword(binding.sinemail.getText().toString(),binding.sinemail.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            pd.dismiss();
                            if(task.isSuccessful())
                            {
                                Intent m= new Intent(SignIn.this,MainActivity.class);
                                startActivity(m);
                                finish();
                            }
                            else{
                                Toast.makeText(SignIn.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

        });
        binding.newusertext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignIn.this,SignUp.class));
                finish();
            }
        });
        //Google Sigin Button
        binding.googlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInM();
            }
        });
        if (auth.getCurrentUser()!=null)
        {
            Intent s = new Intent(SignIn.this,MainActivity.class);
            startActivity(s);
            finish();
        }
    }//onCreate Ends
    private int RESULT_CODE_SINGIN=65;
    //when the signIn Button is clicked then start the signIn Intent
    private void signInM() {
        Intent singInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(singInIntent,RESULT_CODE_SINGIN);
    }

    // onActivityResult (Here we handle the result of the Activity )
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @NonNull Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_CODE_SINGIN) {        //just to verify the code
            //create a Task object and use GoogleSignInAccount from Intent and write a separate method to handle singIn Result.

            Task<GoogleSignInAccount> task = getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> task) {

        //we use try catch block because of Exception.
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            Toast.makeText(SignIn.this,"Signed In successfully",Toast.LENGTH_LONG).show();
            //SignIn successful now show authentication
            FirebaseGoogleAuth(account);
            Intent i = new Intent(SignIn.this,MainActivity.class);
            startActivity(i);
            finish();


        } catch (ApiException e) {
            e.printStackTrace();
            Log.w("TAG", "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(SignIn.this,"SignIn Failed!!!",Toast.LENGTH_LONG).show();
            FirebaseGoogleAuth(null);
        }
    }

    private void FirebaseGoogleAuth(GoogleSignInAccount account) {
        AuthCredential credential;
        credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        //here we are checking the Authentication Credential and checking the task is successful or not and display the message
        //based on that.
        auth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                      Toast.makeText(SignIn.this,"successful login ",Toast.LENGTH_LONG).show();
//                    Snackbar snackbar = Snackbar.make(binding.getRoot(),"Google SignIn Successful",Snackbar.LENGTH_SHORT);
//                    snackbar.show();
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    Users user;
                    user = new Users();
                    user.setUserId(user.getUserId());
                    user.setUserName(user.getUserName());
                    user.setProfilepic(user.getProfilepic().toString());
                    ref=database.getReference().child("Users").child(user.getUserId()); //check if node exsist if not will create
                    ref.setValue(user); // update or add value to the node
                    Intent intent = new Intent(SignIn.this, MainActivity.class);
                    startActivity(intent);

                }
                else {
                    Toast.makeText(SignIn.this,"Failed!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}


