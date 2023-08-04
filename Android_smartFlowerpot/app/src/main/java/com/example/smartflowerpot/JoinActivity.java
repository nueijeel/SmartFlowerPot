package com.example.smartflowerpot;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class JoinActivity extends AppCompatActivity {

    private static final String TAG = "EmailPassword";
    private FirebaseAuth mAuth;
    Button btnJoin;
    EditText edtEmail, edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        btnJoin = (Button)findViewById(R.id.btn_join);
        edtEmail = (EditText) findViewById(R.id.edt_em);
        edtPassword = (EditText)findViewById(R.id.edt_pw);
        mAuth = FirebaseAuth.getInstance();

        btnJoin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                createAccount(edtEmail.getText().toString(),edtPassword.getText().toString());
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
    }



    private void createAccount(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success");
                    Toast.makeText(JoinActivity.this, "계정이 성공적으로 생성되었습니다.",
                            Toast.LENGTH_SHORT).show();
                    FirebaseUser currentUser = mAuth.getCurrentUser();
                    Toast.makeText(JoinActivity.this, "자동 로그인 되었습니다.",Toast.LENGTH_SHORT)
                            .show();
                    Intent intent = new Intent(getApplicationContext(),ManagementActivity.class);
                    startActivity(intent);

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                    Toast.makeText(JoinActivity.this, "계정 생성에 실패하였습니다.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}