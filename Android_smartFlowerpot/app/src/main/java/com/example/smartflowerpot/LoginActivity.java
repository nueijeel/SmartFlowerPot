package com.example.smartflowerpot;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    Button btnLogin;
    EditText edtEmail, edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = (Button)findViewById(R.id.btn_login);
        edtEmail = (EditText) findViewById(R.id.edt_em);
        edtPassword = (EditText)findViewById(R.id.edt_pw);
        mAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(view
                -> signIn(edtEmail.getText().toString(),edtPassword.getText().toString()));
    }

    @Override
    protected void onStart() {
        super.onStart();
    }



    private void signIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    // Sign in success, update UI with the signed-in user's information
                    FirebaseUser currentUser = mAuth.getCurrentUser();
                    Toast.makeText(LoginActivity.this, "로그인에 성공하였습니다.",
                            Toast.LENGTH_SHORT).show();
                    moveMainPage(currentUser);
                }
                else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(LoginActivity.this, "로그인에 실패하였습니다.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    void moveMainPage(FirebaseUser user){
        if(user!=null){
            Intent intent = new Intent(getApplicationContext(),ManagementActivity.class);
            startActivity(intent);
            finish();
        }
    }

}