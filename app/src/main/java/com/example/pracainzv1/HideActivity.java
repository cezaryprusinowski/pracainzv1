package com.example.pracainzv1;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.pracainzv1.databinding.ActivityHideBinding;

import java.io.File;
import java.io.IOException;

public class HideActivity extends AppCompatActivity {

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    private ActivityHideBinding binding;
    private static final int PICKFILE_RESULT_CODE = 1;
    private HideVM hideVM;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHideBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        verifyStoragePermissions(this);

        hideVM = new ViewModelProvider(this).get(HideVM.class);

        hideVM.containerFileMutableLiveData.observe(this, data ->{
            binding.tvContainer.setText(data.getInFileName());
            binding.tvContainerBytes.setText(Integer.toString(data.getInContainerFileByteArray().length));
        });

        hideVM.textFileMutableLiveData.observe(this, data ->{
            binding.tvText.setText(data.getInFileName());
            binding.tvTextBytes.setText(Integer.toString(data.getInTextFileByteArray().length));
        });

        binding.btnHideActivityExecute.setOnClickListener( v -> {
            try {
                File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                hideVM.hideMessageAndGenerateFile(file);
//                hideVM.hideMessageAndGenerateFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
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
                    .add(R.id.frame_SelectFileInHideActivity, SelectFileFragment.class, null)
                    .add(R.id.frame_SelectTextFileInHideActivity, SelectTextFragment.class, null)
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

    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}