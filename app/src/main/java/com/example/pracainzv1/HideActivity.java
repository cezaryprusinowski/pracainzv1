package com.example.pracainzv1;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.pracainzv1.databinding.ActivityHideBinding;

public class HideActivity extends AppCompatActivity {

    private ActivityHideBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHideBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        HideVM hideVM = new ViewModelProvider(this).get(HideVM.class);

        hideVM.contenerFileMutableLiveData.observe(this, data ->{
            binding.VMFileName.setText(data.getFileName());
            binding.VMFilePath.setText(data.getFilePath());
        });

        binding.btnHideActivityExecute.setOnClickListener(v -> {
            hideVM.setContenerFile();
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.frame_SelectFile, SelectFileFragment.class, null)
                    .add(R.id.frame_SelectText, SelectTextFragment.class, null)
                    .commit();
        }
    }
}