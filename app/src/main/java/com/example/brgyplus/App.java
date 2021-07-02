package com.example.brgyplus;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;

public class App extends Application{

    public static final String USER_CHANNEL_ID = "user_channel";
    public static final String ADMIN_CHANNEL_ID = "admin_channel";


    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannels();
    }

    private void createNotificationChannels(){
        NotificationChannel user_channel = new NotificationChannel(
                USER_CHANNEL_ID,
                "User Channel",
                NotificationManager.IMPORTANCE_DEFAULT
        );
        user_channel.setDescription("User Channel for Announcements");

        NotificationChannel admin_channel = new NotificationChannel(
                ADMIN_CHANNEL_ID,
                "Admin Channel",
                NotificationManager.IMPORTANCE_DEFAULT
        );
        admin_channel.setDescription("Admin Channel for Requests");

        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(user_channel);
        manager.createNotificationChannel(admin_channel);
    }
}
