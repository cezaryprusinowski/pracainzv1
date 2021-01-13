package com.example.pracainzv1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.pracainzv1.databinding.SelectFileFragmentBinding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class SelectFileFragment extends Fragment {

    private SelectFileFragmentBinding binding;
    private HideVM hideVM;
    private static final int PICKFILE_RESULT_CODE = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = SelectFileFragmentBinding.inflate(inflater,container,false);
        hideVM = new ViewModelProvider(requireActivity()).get(HideVM.class);

        setListeners();

        return binding.getRoot();
    }

    private void setListeners() {
        binding.btnImage.setOnClickListener(v -> {
            Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
            chooseFile.addCategory(Intent.CATEGORY_OPENABLE);
            chooseFile.setType("image/jpeg");
            startActivityForResult(
                    Intent.createChooser(chooseFile, "Choose a file"),
                    PICKFILE_RESULT_CODE
            );
        });

        binding.btnAudio.setOnClickListener(v -> {
            Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
            chooseFile.addCategory(Intent.CATEGORY_OPENABLE);
            chooseFile.setType("audio/mpeg");
            startActivityForResult(
                    Intent.createChooser(chooseFile, "Choose a file"),
                    PICKFILE_RESULT_CODE
            );
        });

        binding.btnVideo.setOnClickListener(v -> {
            Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
            chooseFile.addCategory(Intent.CATEGORY_OPENABLE);
            chooseFile.setType("video/mp4");
            startActivityForResult(
                    Intent.createChooser(chooseFile, "Choose a file"),
                    PICKFILE_RESULT_CODE
            );
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICKFILE_RESULT_CODE && resultCode == Activity.RESULT_OK){
//            Uri fileData = data.getData();
//            String filePath = fileData.getPath();

            try {
                Context context = getActivity().getApplicationContext();
                Uri uri = data.getData();
                InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);

                File inputFile = new File(uri.getPath());

                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
                LocalDateTime localDateTime = LocalDateTime.now();
                File outFile = new File(context.getFilesDir(), dateTimeFormatter.format(localDateTime) + "_" + inputFile.getName());

                OutputStream outStream = new FileOutputStream(outFile);

                Log.v("Uri", uri.getLastPathSegment());
                Log.v("Uri", String.valueOf(uri));
                Log.v("InputStream","Available MB: " + ((float)inputStream.available()/(1024*1024)));

                //TODO Wyświetlić pasek postępu podczas generowania pliku
                FileUtils.copy(inputStream, outStream);
                Log.v("CreateFile","File created.");

                hideVM.setContainerFile(uri);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


}
