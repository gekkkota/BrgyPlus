package com.example.brgyplus.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;

import com.example.brgyplus.R;

public class Settings extends AppCompatActivity {

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTheme(R.style.Theme_BrgyPlus);
        drawerLayout = findViewById(R.id.drawer_layout);
    }
    public void ClickMenu(View view){
        Home.openDrawer(drawerLayout);
    }
    public void ClickHome(View view){
        Home.redirectActivity(this,Home.class);
    }
    public void ClickAddress(View view){
        Home.redirectActivity(this, Notification.class);
    }
    public void ClickContactUs(View view){
        Home.redirectActivity(this,ContactUs.class);
    }
    public void ClickSettings(View view){
        Home.redirectActivity(this,ContactUs.class);
    }

    public void ClickLogout(View view){
        Home.logout(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Home.closeDrawer(drawerLayout);
    }
}