package com.example.himanshu.myblogapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
                    Intent intent=new Intent(MainActivity.this,PostListActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Not Signed In",Toast.LENGTH_LONG).show();

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

        CreateAcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,CreateAccountActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void Login(String email, String pwd) {
        mAuth.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(MainActivity.this,"SignedIn",Toast.LENGTH_LONG).show();
                    FirebaseUser user=mAuth.getCurrentUser();
                    Intent intent=new Intent(MainActivity.this,PostListActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Wrong Details Entered",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.add_posts)
        {
            // TODO: 27-09-2018
        }
        if(item.getItemId()==R.id.sign_out)
        {
            mAuth.signOut();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListener!=null)
        {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
