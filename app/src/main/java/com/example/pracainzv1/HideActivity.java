package com.example.pracainzv1;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.pracainzv1.databinding.ActivityHideBinding;

public class HideActivity extends AppCompatActivity {

    private ActivityHideBinding binding;
    private static final int PICKFILE_RESULT_CODE = 1;
    private HideVM hideVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHideBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        hideVM = new ViewModelProvider(this).get(HideVM.class);

        hideVM.containerFileMutableLiveData.observe(this, data ->{
            binding.VMFileName.setText(data.getFileName());
            binding.VMFilePath.setText(data.getFilePath());
            binding.VMFileBytesCount.setText(data.getFileBytesCountString());
        });

//        binding.btnHideActivityChooseFile.setOnClickListener(v -> {
//            Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
//            chooseFile.addCategory(Intent.CATEGORY_OPENABLE);
//            chooseFile.setType("image/jpeg");
//            startActivityForResult(
//                    Intent.createChooser(chooseFile, "Choose a file"),
//                    PICKFILE_RESULT_CODE
//            );
//        });

//        binding.btnHideActivityExecute.setOnClickListener(v -> {
//            hideVM.setContainerFile();
//        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.frame_SelectFile, SelectFileFragment.class, null)
                    .add(R.id.frame_SelectText, SelectTextFragment.class, null)
                    .commit();
        }
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == PICKFILE_RESULT_CODE && resultCode == Activity.RESULT_OK){
//            Uri fileData = data.getData();
//            String filePath = fileData.getPath();
//            hideVM.setContainerFile(filePath);
//        }
//    }
}