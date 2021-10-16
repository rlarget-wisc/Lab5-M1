package com.example.lab5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

    private TextView welcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        welcome = (TextView) findViewById(R.id.welcomeText);
        Intent intent = getIntent();
//        String username = intent.getStringExtra("username");
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.lab5",Context.MODE_PRIVATE);
        String username = sharedPreferences.getString(MainActivity.usernameKey,"");
        welcome.setText("Welcome "+username+"!");
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

        if (item.getItemId()==R.id.logout) {
            logout();
            return true;
        }
        return false;
    }
}