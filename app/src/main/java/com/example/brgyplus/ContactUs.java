package com.example.brgyplus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;

public class ContactUs extends AppCompatActivity {

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_BrgyPlus);
        setContentView(R.layout.activity_contact_us);

        drawerLayout = findViewById(R.id.drawer_layout);
    }

    public void ClickMenu(View view){
        Home.openDrawer(drawerLayout);
    }
    public void ClickProfile(View view){
        Home.closeDrawer(drawerLayout);
    }
    public void ClickHome(View view){
        Home.redirectActivity(this,Home.class);
    }
    public void ClickAddress(View view){
        Home.redirectActivity(this,Address.class);
    }
    public void ClickContactUs(View view){
        recreate();
    }
    public void ClickSettings(View view){
        Home.redirectActivity(this,Settings.class);
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