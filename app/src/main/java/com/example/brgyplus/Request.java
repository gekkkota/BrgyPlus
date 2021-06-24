  package com.example.brgyplus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;

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
          Home.openDrawer(drawerLayout);
      }
      public void ClickHome(View view){
          Home.redirectActivity(this,Home.class);
      }
      public void ClickSendAnnouncement(View view){
          Home.redirectActivity(this,SendAnnouncement.class);
      }
      public void ClickCheckRequest(View view){
          Home.redirectActivity(this,Request.class);
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