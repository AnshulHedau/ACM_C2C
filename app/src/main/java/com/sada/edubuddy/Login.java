package com.sada.edubuddy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = (EditText)findViewById(R.id.email_login);
        password=(EditText)findViewById(R.id.password_login);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void login_button(View view)
    {
        final ProgressDialog progressDialog = ProgressDialog.show(Login.this,"Please Wait ...","Processing ...",true);
        (firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()))
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful())
                        {
                            Toast.makeText(Login.this,"Logges in ", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(Login.this,profile.class);

                        }
                        else
                        {
                            Log.e("Error",task.getException().toString());

                            Toast.makeText(Login.this,task.getException().getMessage(), Toast.LENGTH_LONG).show();

                        }
                    }
                });

    }
}
