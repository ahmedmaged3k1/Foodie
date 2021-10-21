package com.example.foodie.app.features.signIn;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.foodie.app.features.home.HomeActivity;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;


import com.example.foodie.R;
import com.example.foodie.app.features.signUp.SignUpActivity;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class SignInActivity extends AppCompatActivity {
    AppCompatButton signUp ;
    AppCompatButton signInButton ;
    LoginButton facebookLogin ;
    CallbackManager callbackManager ;
    AccessTokenTracker accessTokenTracker;
    FirebaseAuth.AuthStateListener authStateListener;
    ProgressBar signInProgressBar ;
    FirebaseAuth firebaseAuth ;
    TextInputEditText email ;
    Intent homeActivity;
    TextInputEditText password ;
    DatabaseReference firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView( R.layout.activity_sign_in);
        signInProgressBar=findViewById(R.id.signInProgress);

        signInWithFacebook();
        getSupportActionBar().hide();
        setLoginButton();

    }

    private void setLoginButton() {
        signInButton = findViewById(R.id.loginButton);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInButton.setClickable(false);
                signInWithEmail();
            }
        });

    }
    private void signInWithEmail(){
        email = findViewById(R.id.emailInput);
        password = findViewById(R.id.passwordInput);


        firebaseAuth = FirebaseAuth.getInstance();
        if (TextUtils.isEmpty(email.getText().toString())) {
            email.setError("Please Enter Email");
            return;
        }
        if (TextUtils.isEmpty(password.getText().toString())) {
            password.setError("Please Enter Password");
            return;
        }
        signInProgressBar.setVisibility(View.VISIBLE);
        firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    homeActivity = new Intent(getApplicationContext(), HomeActivity.class);
                    firebaseDatabase = FirebaseDatabase.getInstance().getReference("Users").child(firebaseAuth.getCurrentUser().getUid());
                    firebaseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                String username;
                                username = snapshot.child("name").getValue().toString();
                                homeActivity.putExtra("UserName", username);
                                homeActivity.putExtra("User ID", firebaseAuth.getCurrentUser().getUid());
                                startActivity(homeActivity);

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });


                } else {
                    Toast.makeText(SignInActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                    signInProgressBar.setVisibility(View.GONE);
                    signInButton.setClickable(true);
                }
            }
        });
    }
    private void signInWithFacebook(){
        firebaseAuth =FirebaseAuth.getInstance();
        FacebookSdk.sdkInitialize(getApplicationContext());
        facebookLogin=findViewById(R.id.loginFacebook);
        facebookLogin.setReadPermissions("email","public_profile");
        callbackManager=CallbackManager.Factory.create();
        facebookLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                signInProgressBar.setVisibility(View.VISIBLE);
                Log.d(TAG, "onSuccess: "+loginResult);
                handleFacebookToken(loginResult.getAccessToken());
                
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(@NonNull FacebookException e) {
                Log.d(TAG, "onError: "+e.getMessage());
            }
        });

        accessTokenTracker= new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if(currentAccessToken==null){
                    firebaseAuth.signOut();
                }
            }
        };



        signUp=findViewById(R.id.signUpButton);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(resultCode,requestCode,data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void handleFacebookToken(AccessToken token ){

        AuthCredential authCredential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                    FirebaseUser user =firebaseAuth.getCurrentUser();
                    Log.d(TAG, "Facebook: open intent "+      user.getPhoneNumber());
                    intent.putExtra("UserId",user.getUid());
                    startActivity(intent);
                    signInProgressBar.setVisibility(View.GONE);
                    firebaseAuth.signOut();
                    LoginManager.getInstance().logOut();



                }
                else{
                    Log.d(TAG, "onComplete: "+task.getException());
                }
            }
        });

    }
    /*@Override
   protected void onStart() {
        super.onStart();
        FirebaseUser user =firebaseAuth.getCurrentUser();
        firebaseAuth.addAuthStateListener(authStateListener);
    }*/

    @Override
    protected void onStop() {
        super.onStop();
        if (authStateListener!=null)
        {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
        signInProgressBar.setVisibility(View.GONE);
        signInButton = findViewById(R.id.loginButton);
        signInButton.setClickable(true);
    }
}