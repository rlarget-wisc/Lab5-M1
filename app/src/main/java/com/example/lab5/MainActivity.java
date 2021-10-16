package com.example.lab5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static String usernameKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.lab5",Context.MODE_PRIVATE);

        if(sharedPreferences.getString(MainActivity.usernameKey,"").equals("")) {
            setContentView(R.layout.activity_main);
        } else {
            login();
        }

    }

    public void onButtonClick(View view) {
        EditText usernameInput = (EditText) findViewById(R.id.usernameInput);
        String username = usernameInput.getText().toString();

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.lab5", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(MainActivity.usernameKey,username).apply();

        login();
    }

    private void login() {
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
    }
}