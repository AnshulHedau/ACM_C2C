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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private FirebaseAuth firebaseAuth;
    private EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        email = (EditText)findViewById(R.id.email_reg);
        password=(EditText)findViewById(R.id.password_reg);
        name = (EditText) findViewById(R.id.name_reg);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void Register_done(View view)
    {
        final ProgressDialog progressDialog = ProgressDialog.show(RegistrationActivity.this,"Please Wait ...","Processing ...",true);
        (firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString()))
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();

                if(task.isSuccessful()){
                    Toast.makeText(RegistrationActivity.this,"registered Successfully ", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(RegistrationActivity.this,LoginActivity.class);
                    startActivity(i);
                    String userId = null;
                    try {
                        userId = firebaseAuth.getCurrentUser().getUid();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    DatabaseReference currentUserRoot = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
                    currentUserRoot.child("name").setValue(name.getText().toString());
                    currentUserRoot.child("email").setValue(email.getText().toString());
                }
                else
                {
                    Log.e("Error",task.getException().toString());
                    Toast.makeText(RegistrationActivity.this,task.getException().getMessage(), Toast.LENGTH_LONG).show();

                }
            }
        });
    }
}
