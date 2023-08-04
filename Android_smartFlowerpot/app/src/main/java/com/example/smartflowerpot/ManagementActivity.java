package com.example.smartflowerpot;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class ManagementActivity extends AppCompatActivity {

    water waterFrag;
    community communityFrag;
    image imageFrag;
    account accountFrag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);

        waterFrag = new water();
        communityFrag = new community();
        imageFrag = new image();
        accountFrag = new account();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, waterFrag).commit();
        BottomNavigationView bottom_menu = findViewById(R.id.bottom_menu);
        bottom_menu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.FirstTab:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, waterFrag).commit();
                        return true;
                    case R.id.SecondTab:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, communityFrag).commit();
                        return true;
                    case R.id.ThirdTab:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, imageFrag).commit();
                        return true;
                    case R.id.FourthTab:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, accountFrag).commit();
                        return true;
                }
                return false;
            }
        });


    }

}