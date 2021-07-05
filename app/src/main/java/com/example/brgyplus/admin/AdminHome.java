package com.example.brgyplus.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.brgyplus.MainActivity;
import com.example.brgyplus.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import org.jetbrains.annotations.NotNull;

public class AdminHome extends AppCompatActivity {

    DrawerLayout drawerLayout;

    EditText enterTitle, enterMessage;
    Button sendNotif;

    private static final String CHANNEL_ID = "brgy_plus_announcement";
    private static final String CHANNEL_NAME = "Brgy Plus";
    private static final String CHANNEL_DESC = "Send Notif to all";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_BrgyPlus);
        setContentView(R.layout.activity_admin_home);

        drawerLayout = findViewById(R.id.drawer_layout);

        enterTitle = findViewById(R.id.inputTitle);
        enterMessage = findViewById(R.id.inputMessage);
        sendNotif = findViewById(R.id.sendNotif);

        sendNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayNotification();
            }
        });

        // FCM Token
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<String> task) {
                if(task.isSuccessful()){

                    // Get new FCM Token
                    String token = task.getResult();

                    // Save token
                    saveToken(token);
                }else{
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void saveToken(String token){
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        AdminToken adminToken = new AdminToken(email, token);

        FirebaseDatabase.getInstance().getReference("AdminToken")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(adminToken).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(AdminHome.this, "Token created!", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(AdminHome.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void displayNotification(){
        String title = enterTitle.getText().toString();
        String message = enterMessage.getText().toString();

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(1, mBuilder.build());
    }

    public void ClickAdminMenu(View view) { openAdminDrawer(drawerLayout); }
    public static void openAdminDrawer(DrawerLayout drawerLayout) {

        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickMenu2(View view) {
        closeDrawer(drawerLayout);
    }
    public static void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    // OnClick
    public void ClickSendAnnouncement(View view){
        redirectActivity(this,AdminHome.class);
    }
    public void ClickCheckRequest(View view){
        redirectActivity(this,Request.class);
    }
    public void ClickAdminSettings(View view){
        redirectActivity(this,AdminSettings.class);
    }
    public void ClickLogout(View view){
        logout(this);
    }

    // Logout Function
    public static void logout(Activity activity) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout ?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseAuth.getInstance().signOut();
                activity.startActivity(new Intent(activity, MainActivity.class));
                activity.finishAffinity();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();

    }

    // Redirect to Activity Function
    public static void redirectActivity(Activity activity,Class aClass) {
        Intent intent = new Intent(activity,aClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }
}