package com.example.foodie.app.features.signUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.foodie.R;
import com.example.foodie.app.features.home.HomeActivity;
import com.example.foodie.entities.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class SignUpActivity extends AppCompatActivity {
    Intent loginActivity ;
    TextInputEditText userName ;
    TextInputEditText email ;
    TextInputEditText password ;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    ProgressBar registrationProgressBar ;
    AppCompatButton signUpButton ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();
        signUpButton=findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }
    private void register(){
        userName=findViewById(R.id.userNameInput);
        email=findViewById(R.id.emailInput);
        password=findViewById(R.id.passwordInput);
        registrationProgressBar=findViewById(R.id.registerProgress);
        firebaseAuth =FirebaseAuth.getInstance();
        if(TextUtils.isEmpty(userName.getText().toString())){
            userName.setError("Please Enter Your Name");
            return;
        }
        if(TextUtils.isEmpty(password.getText().toString())){
            password.setError("Please Enter Your Password");
            return;
        }
        if(TextUtils.isEmpty(email.getText().toString())){
            email.setError("Please Enter Your Email");
            return;
        }
        registrationProgressBar.setVisibility(View.VISIBLE);
        firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    User user =new User(userName.getText().toString(),email.getText().toString(),password.getText().toString());

                    databaseReference = FirebaseDatabase.getInstance().getReference("Users");
                    databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                            if(task.isSuccessful()){
                                loginActivity=new Intent(SignUpActivity.this, HomeActivity.class);
                                startActivity(loginActivity);
                                registrationProgressBar.setVisibility(View.GONE);
                            }
                        }
                    });

                }
                else{

                    registrationProgressBar.setVisibility(View.GONE);
                }
            }
        });


    }
}