  package com.example.brgyplus.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;

import com.example.brgyplus.R;
import com.example.brgyplus.user.Home;

  public class Request extends AppCompatActivity {

      DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        setTheme(R.style.Theme_BrgyPlus);
        drawerLayout = findViewById(R.id.drawer_layout);
    }

      public void ClickMenu(View view){
          AdminHome.openDrawer(drawerLayout);
      }
      public void ClickSendAnnouncement(View view){ Home.redirectActivity(this,AdminHome.class); }
      public void ClickCheckRequest(View view){
          Home.redirectActivity(this,Request.class);
      }
      public void ClickAdminSettings(View view){
          Home.redirectActivity(this,AdminSettings.class);
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