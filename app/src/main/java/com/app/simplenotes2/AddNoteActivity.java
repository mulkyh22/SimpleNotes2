package com.app.simplenotes2;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Locale;

public class AddNoteActivity extends AppCompatActivity {

    private EditText titleEditText;
    private EditText noteEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        titleEditText = findViewById(R.id.edit_text_title);
        noteEditText = findViewById(R.id.edit_text_note);
        MaterialButton saveButton = findViewById(R.id.save_note_btn);

        createNotificationChannel(); // Create notification channel

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });
    }

    private void saveNote() {
        String titleNote = titleEditText.getText().toString();
        String descriptionNote = noteEditText.getText().toString();
        String uid = FirebaseAuth.getInstance().getUid();
        long createdTime = new Timestamp(System.currentTimeMillis()).getTime();

        NoteModel note = new NoteModel(titleNote, descriptionNote, getDate(createdTime), uid);

        DatabaseReference notesRef = FirebaseDatabase.getInstance().getReference("Notes").child(uid);
        notesRef.child(titleNote).setValue(note).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(AddNoteActivity.this, "Note saved", Toast.LENGTH_SHORT).show();
                    sendNotification(titleNote); // Send notification
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddNoteActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void sendNotification(String noteTitle) {
        String channelId = "note_saved_channel";
        String notificationTitle = "Note Saved";
        String notificationMessage = "Your note '" + noteTitle + "' has been saved.";

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(notificationTitle)
                .setContentText(notificationMessage)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, builder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "note_saved_channel";
            CharSequence channelName = "Note Saved Channel";
            String channelDescription = "Notifications for saved notes";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
            channel.setDescription(channelDescription);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        String date = DateFormat.format("dd-MM-yyyy", cal).toString();
        return date;
    }
}

//Signature :
//10120146 - Irshal Mulky H - IF4