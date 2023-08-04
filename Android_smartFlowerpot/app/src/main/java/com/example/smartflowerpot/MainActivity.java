package com.example.smartflowerpot;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    Button btnCreateAcc, btnLogin, btnLogout;
    ImageView img1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCreateAcc = (Button)findViewById(R.id.BtnCreateAcc);
        btnLogin = (Button) findViewById(R.id.BtnLoginAcc);
        btnLogout = (Button) findViewById(R.id.BtnLogoutAcc);
        img1 = (ImageView) findViewById(R.id.ImgPlant);
        mAuth = FirebaseAuth.getInstance();

        img1.setImageResource(R.drawable.smartfarm);

        btnCreateAcc.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(),JoinActivity.class);
                startActivity(intent1);
            }
        });
        btnLogin.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent2);
            }
        });


        btnLogout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                btnLogout.setVisibility(View.GONE);
                btnLogin.setVisibility(View.VISIBLE);
                btnCreateAcc.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, "로그아웃되었습니다.",
                        Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Toast.makeText(MainActivity.this, "로그인 되었습니다.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(),ManagementActivity.class);
            startActivity(intent);
        }

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        FirebaseAuth.getInstance().signOut();

    }


}