package com.example.pracainzv1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.pracainzv1.databinding.ActivityMenuBinding;

public class MenuActivity extends Activity {

    private ActivityMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setListeners();
    }

    private void setListeners() {
        binding.btnMenuHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, EncodeActivity.class);
                startActivity(intent);
            }
        });

        binding.btnMenuUnhide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, DecodeActivity.class);
                startActivity(intent);
            }
        });

        binding.btnMenuStat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MenuActivity.this, "Statystyki", Toast.LENGTH_SHORT).show();
            }
        });
    }
}