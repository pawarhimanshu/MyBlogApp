package com.example.himanshu.myblogapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
   private FirebaseAuth mAuth;
   private FirebaseUser mUser;
   private FirebaseAuth.AuthStateListener mAuthListener;
   private Button LoginButton;
   private Button CreateAcButton;
   private EditText EmailField;
   private EditText PasswordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoginButton = findViewById(R.id.loginID);
        CreateAcButton = findViewById(R.id.createAccountID);
        EmailField = findViewById(R.id.emailId);
        PasswordField = findViewById(R.id.passwordId);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                mUser = firebaseAuth.getCurrentUser();
                if(mUser!=null)
                {
                    Toast.makeText(MainActivity.this,"Signed In",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Signed Out",Toast.LENGTH_LONG).show();

                }
            }
        };
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!EmailField.getText().toString().equals("")&&!PasswordField.getText().toString().equals(""))
                {
                    String email=EmailField.getText().toString();
                    String pwd=PasswordField.getText().toString();
                    Login(email,pwd);
                }
                else
                {
                    
                }

            }
        });
    }

    private void Login(String email, String pwd) {
        mAuth.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful())
                {
                    Toast.makeText(MainActivity.this,"SignedIn",Toast.LENGTH_LONG).show();
                }
                else
                {
                    //TODO
                }
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
}
