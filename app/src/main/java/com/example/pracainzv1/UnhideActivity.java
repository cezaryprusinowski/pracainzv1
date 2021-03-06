package com.example.pracainzv1;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.pracainzv1.databinding.ActivityUnhideBinding;

public class UnhideActivity extends AppCompatActivity {

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    private ActivityUnhideBinding binding;
    private static final int PICKFILE_RESULT_CODE = 1;
    private UnhideVM unhideVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUnhideBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        verifyStoragePermissions(this);

        unhideVM = new ViewModelProvider(this).get(UnhideVM.class);
        setObservers();
        setListeners();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.frame_SelectFileInUnhideActivity, SelectFileFragment.class, null)
                    .commit();
        }
    }

    private void setListeners() {
        binding.btnUnhideActivityExecute.setOnClickListener(v -> {
            if (unhideVM.containerFile == null){
                Toast.makeText(this, "Wybierz plik", Toast.LENGTH_LONG).show();
            }
            else{
                unhideVM.readMessage();
            }
        });
    }

    private void setObservers() {
//        unhideVM.containerFileMutableLiveData.observe(this, data ->{
//            binding.tvContainerWithMessage.setText(data.getInFileName());
//        });

        unhideVM.messageFromFileMutableLiveData.observe(this, data ->{
            binding.tvMessageText.setText(data);
        });
    }

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