package com.example.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddNoteActivity extends AppCompatActivity {

    private int noteid; // -1
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        // 1. get EditText view
        EditText editNote = (EditText) findViewById(R.id.addNoteText);
        // 2. get Intent
        Intent intent = getIntent();
        // 3. get the value of the integer "noteid" from intent
        // 4. initialize class variable noteid with value from intent
        noteid = intent.getIntExtra("noteid",-1);

        Log.i("NoteID","Note id:"+noteid);
        if (noteid != -1) {
            Note note = WelcomeActivity.notes.get(noteid);
            String noteContent = note.getContent();
            editNote.setText(noteContent);
        }
    }

    public void saveNote(View view) {
        // 1. get editText view and the content that the user entered
        EditText editNote = (EditText) findViewById(R.id.addNoteText);
        String content = editNote.getText().toString();
        // 2. Initialize SQLiteDatabase instance
        // 3. Initialize DBHelper class
        DBHelper helper = new DBHelper(getApplicationContext());

        // 4. get username from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.lab5", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString(MainActivity.usernameKey,"");

        // 5. save information to DB
        String title;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String date = dateFormat.format(new Date());

        if (noteid == -1) { // add note
            title = "NOTE_" + (WelcomeActivity.notes.size()+1);
            helper.saveNotes(username,title,content,date);
        } else {
            title = "NOTE_" + (noteid+1);
            helper.updateNotes(title,date,username,content);
        }
        // 6. Go to second activity using intents
        Intent intent = new Intent(getApplicationContext(),WelcomeActivity.class);
        startActivity(intent);
    }
}