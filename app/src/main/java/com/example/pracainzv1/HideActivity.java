package com.example.pracainzv1;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class HideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hide);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.frame_SelectFile, SelectFileFragment.class, null)
                    .commit();
        }
    }
}