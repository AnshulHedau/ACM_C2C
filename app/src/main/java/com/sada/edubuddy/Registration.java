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

public class Registration extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        email = (EditText)findViewById(R.id.email_reg);
        password=(EditText)findViewById(R.id.password_reg);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void Register_done(View view)
    {
        final ProgressDialog progressDialog = ProgressDialog.show(Registration.this,"Please Wait ...","Processing ...",true);
        (firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString()))
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();

                if(task.isSuccessful()){
                    Toast.makeText(Registration.this,"registered Successfully ", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(Registration.this,Login.class);
                    startActivity(i);
                }
                else
                {
                    Log.e("Error",task.getException().toString());
                    Toast.makeText(Registration.this,task.getException().getMessage(), Toast.LENGTH_LONG).show();

                }
            }
        });
    }
}
