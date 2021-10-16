package com.example.lab5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class WelcomeActivity extends AppCompatActivity {

    private TextView welcome;
    public static ArrayList<Note> notes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // 1. Display welcome message. Fetch username from SharedPreferences.
        welcome = (TextView) findViewById(R.id.welcomeText);

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.lab5",Context.MODE_PRIVATE);
        String username = sharedPreferences.getString(MainActivity.usernameKey,"");

        welcome.setText("Welcome "+username+"!");

        // 2. Get SQLiteDatabase Instance
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes",Context.MODE_PRIVATE,null);
        DBHelper notesDB = new DBHelper(sqLiteDatabase);

        // 3. Initiate the "notes" class variable using readNotes method in DBHelper class using username from SharedPreferences
        notes = notesDB.readNotes(username);

        // 4. Create an ArrayList<String> by iterating over notes object
        ArrayList<String> displayNotes = new ArrayList<>();
        for (Note note: notes) {
            displayNotes.add(String.format("Title:%s\nDate:%s",note.getTitle(),note.getDate()));
        }

        // 5. Use ListView view to display notes on screen
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,displayNotes);
        ListView listView = (ListView) findViewById(R.id.notesList);
        listView.setAdapter(adapter);

        // 6. Add onItemClickListener for ListView item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), AddNoteActivity.class);
                intent.putExtra("noteid",position);
                startActivity(intent);
            }
        });

    }

    private void logout() {
        Intent intent = new Intent(this, MainActivity.class);
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.lab5",Context.MODE_PRIVATE);
        sharedPreferences.edit().remove(MainActivity.usernameKey).apply();
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()){
            case R.id.logout:
                logout();
                break;
            case R.id.addNote:
                addNewNote();
                break;
        }
        return true;
    }

    private void addNewNote() {
        Intent intent = new Intent(this,AddNoteActivity.class);
        startActivity(intent);
    }
}