package com.example.pracainzv1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.pracainzv1.databinding.ActivityWelcomeBinding;

public class WelcomeActivity extends Activity {

    private ActivityWelcomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWelcomeBinding.inflate(getLayoutInflater());

        View view = binding.getRoot();
        setContentView(view);

        setListeners();
    }

    private void setListeners() {
        binding.btnWelcomeStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });
    }
}