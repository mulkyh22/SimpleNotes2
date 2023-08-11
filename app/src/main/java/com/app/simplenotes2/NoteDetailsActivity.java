package com.app.simplenotes2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NoteDetailsActivity extends AppCompatActivity {

    TextView title, description;
    FloatingActionButton deleteButton, editButton;
    String key = "";

    // Define notification channel ID
    private static final String CHANNEL_ID = "note_deleted_channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);

        title = findViewById(R.id.edit_title);
        description = findViewById(R.id.edit_note);

        deleteButton = findViewById(R.id.deleteButton);
        editButton = findViewById(R.id.editButton);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            title.setText(bundle.getString("Title"));
            description.setText(bundle.getString("Description"));
            key = bundle.getString("Key");
        }

        String user = FirebaseAuth.getInstance().getUid();

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Notes").child(user);
                reference.child(key).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        showDeletedNotification();
                        Toast.makeText(NoteDetailsActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                });
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NoteDetailsActivity.this, NoteEditActivity.class)
                        .putExtra("Title", title.getText().toString())
                        .putExtra("Description", description.getText().toString())
                        .putExtra("Key", key)
                        .putExtra("User", user);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(NoteDetailsActivity.this, MainActivity.class));
        finish();
    }

    private void showDeletedNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Note Deleted")
                .setContentText("The note has been deleted.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, builder.build());
    }
}

//Signature :
//10120146 - Irshal Mulky H - IF4