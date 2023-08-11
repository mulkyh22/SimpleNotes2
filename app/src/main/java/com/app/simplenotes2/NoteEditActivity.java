package com.app.simplenotes2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Locale;

public class NoteEditActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "note_edit_channel";
    private static final int NOTIFICATION_ID = 1;

    TextView title, description;
    String titleNew, descriptionNew;
    String key;
    String user;
    MaterialButton editButton;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);

        title = findViewById(R.id.edit_title);
        description = findViewById(R.id.edit_notes);
        String user = FirebaseAuth.getInstance().getUid();
        editButton = findViewById(R.id.save_edit_note_btn);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            title.setText(bundle.getString("Title"));
            description.setText(bundle.getString("Description"));
            key = bundle.getString("Key");
            user = bundle.getString("User");
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("Notes").child(user).child(key);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
    }

    private void saveData() {
        titleNew = title.getText().toString().trim();
        descriptionNew = description.getText().toString();
        long createdTime = new Timestamp(System.currentTimeMillis()).getTime();

        NoteModel noteModel = new NoteModel(titleNew, descriptionNew, getDate(createdTime), user);

        databaseReference.setValue(noteModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    showNotification("Note Edited", "Your note has been edited and saved.");
                    Intent intent = new Intent(NoteEditActivity.this, NoteDetailsActivity.class)
                            .putExtra("Title", titleNew)
                            .putExtra("Description", descriptionNew)
                            .putExtra("Key", key)
                            .putExtra("User", user);
                    Toast.makeText(NoteEditActivity.this, "Note Edited", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(NoteEditActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        String date = DateFormat.format("dd-MM-yyyy", cal).toString();
        return date;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void showNotification(String title, String message) {
        createNotificationChannel();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}

//Signature :
//10120146 - Irshal Mulky H - IF4