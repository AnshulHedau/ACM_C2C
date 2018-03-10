package com.sada.edubuddy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private FirebaseAuth firebaseAuth;
    private Button bRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = (EditText)findViewById(R.id.email_login);
        password=(EditText)findViewById(R.id.password_login);
        bRegister = (Button) findViewById(R.id.Register);
        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),RegistrationActivity.class));
                finish();
            }
        });
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void login_button(View view)
    {
        final ProgressDialog progressDialog = ProgressDialog.show(LoginActivity.this,"Please Wait ...","Processing ...",true);
        (firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()))
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful())
                        {
                            Toast.makeText(LoginActivity.this,"Logged in ", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            Log.e("Error",task.getException().toString());

                            Toast.makeText(LoginActivity.this,task.getException().getMessage(), Toast.LENGTH_LONG).show();

                        }
                    }
                });

    }
}
