package com.example.brgyplus.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.brgyplus.MainActivity;
import com.example.brgyplus.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

public class Home extends AppCompatActivity {

    DrawerLayout drawerLayout;
    LinearLayout brgyBusinessClear, brgyClear, brgyCert, otherConcerns;
    private String firstName;

    private static final String CHANNEL_ID = "brgy_plus_announcement";
    private static final String CHANNEL_NAME = "Brgy Plus";
    private static final String CHANNEL_DESC = "Send Notif to all";

    LayoutInflater inflater;
    View myView;
    TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_BrgyPlus);
        setContentView(R.layout.activity_home);

        drawerLayout = findViewById(R.id.drawer_layout);

        brgyCert = findViewById(R.id.barangayCertificate);
        brgyBusinessClear = findViewById(R.id.barangayBusinessClearance);
        brgyClear = findViewById(R.id.barangayClearance);
        otherConcerns = findViewById(R.id.otherConcerns);

        inflater = getLayoutInflater();
        myView = inflater.inflate(R.layout.main_nav_drawer, null);
        name = myView.findViewById(R.id.nameTextView);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        String userID = user.getUid();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null){
                    String firstname = userProfile.firstname;
                    firstName = firstname;
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });

        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
        channel.setDescription(CHANNEL_DESC);

        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channel);

        brgyCert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String request = "Barangay Certificate";
                displayNotification(request);
            }
        });

        brgyBusinessClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String request = "Barangay Business Clearance";
                displayNotification(request);
            }
        });

        brgyClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String request = "Barangay Clearance";
                displayNotification(request);
            }
        });

        otherConcerns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String request = "Other Concerns";
                displayNotification(request);
            }
        });

    }

    public void ClickMenu(View view) {
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout) {

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

    public void ClickHome(View view){
        redirectActivity(this, Home.class);
    }

    public void ClickAddress(View view){
        redirectActivity(this, Notification.class);
    }

    public void ClickContactUs(View view){
        redirectActivity(this,ContactUs.class);
    }

    public void ClickSettings(View view){
        redirectActivity(this,Settings.class);
    }

    public void ClickLogout(View view){
        logout(this);
    }

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

    public void displayNotification(String request){

        String requests = request;
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications)
                .setContentTitle(requests)
                .setContentText(firstName + " is issuing a request!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(1, mBuilder.build());
    }
}