package com.example.pracainzv1;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
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

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
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
            Uri uri = data.getData();
            try {
                FileInputStream fileInputStream = (FileInputStream) getActivity().getContentResolver().openInputStream(uri);
                hideVM.setContainerFile(uri.getLastPathSegment(),fileInputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
//            Uri fileData = data.getData();
//            String filePath = fileData.getPath();

            //                byte[] inputStreamByteArray = new byte[inputStream.available()];
//                inputStream.read(inputStreamByteArray);

            //String s = new String(inputStreamByteArray);
//                byte[] bytes = "FLG".getBytes();
//                String bytesString = new String(bytes);
//                String s = "";
//
//                for(byte b : bytes){
//                    s = s+ " " + Integer.toBinaryString(b);
//                }
//                Log.v("Byte",bytesString);
//                Log.v("Byte",s);
//                Context context = getActivity().getApplicationContext();
//                File filesDir = context.getFilesDir();
//////                String uriTextFile = sdcard.getAbsolutePath(); //"/sdcard/DCIM/text_1.txt";
////
//                File textFile = new File(filesDir, "text_1.txt");
////                Log.v("TextFile", textFile.getAbsolutePath());
//                FileInputStream textFileInputStream = new FileInputStream(textFile);
//                Log.v("TextFile", String.valueOf(textFileInputStream.available()));
//                byte[] textFileBytesArray = IOUtils.toByteArray(textFileInputStream);
//
//                byte[] textFlagBytesArray = "FLG".getBytes();
//                byte[] textFileWithFlagBytesArray = ByteBuffer.allocate(textFileBytesArray.length + textFlagBytesArray.length + textFlagBytesArray.length)
//                        .put(textFlagBytesArray)
//                        .put(textFileBytesArray)
//                        .put(textFlagBytesArray)
//                        .array();
//
//                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
//                LocalDateTime localDateTime = LocalDateTime.now();
//                File outFile = new File(filesDir, dateTimeFormatter.format(localDateTime) + "_" + textFile.getName());
//                FileOutputStream fos = new FileOutputStream(outFile);
//                fos.write(textFileWithFlagBytesArray);

//                String textFileBytesToString = new String(textFileBytesArray);
//                String textFileInBits = "";
//                for (byte b : textFileBytesArray){
//                    textFileInBits = textFileInBits + Integer.toBinaryString(b) + " ";
//                }
//                Log.v("BytesBits",textFileInBits);
//                Log.v("BytesText",textFileBytesToString);

            /** MAIN APP */
//                Context context = getActivity().getApplicationContext();

//
//                InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);
//                File inputFile = new File(uri.getPath());
//
//                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
//                LocalDateTime localDateTime = LocalDateTime.now();
//                File outFile = new File(context.getFilesDir(), dateTimeFormatter.format(localDateTime) + "_" + inputFile.getName());
//                OutputStream outStream = new FileOutputStream(outFile);
//
//                Log.v("Uri", uri.getLastPathSegment());
//                Log.v("Uri", String.valueOf(uri));
//                Log.v("InputStream","Available MB: " + ((float)inputStream.available()/(1024*1024)));
//                //Log.v("InputStream",inputStream.toString());
//
//
//                //TODO Wyświetlić pasek postępu podczas generowania pliku
//                FileUtils.copy(inputStream, outStream);
//                Log.v("CreateFile","File created.");




        }
    }


}
