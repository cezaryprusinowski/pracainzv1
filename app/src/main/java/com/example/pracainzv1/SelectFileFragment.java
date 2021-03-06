package com.example.pracainzv1;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.pracainzv1.databinding.SelectFileFragmentBinding;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;

public class SelectFileFragment extends Fragment {

    private SelectFileFragmentBinding binding;
    private EncodeVM encodeVM;
    private DecodeVM decodeVM;
    private static final int PICKFILE_RESULT_CODE = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = SelectFileFragmentBinding.inflate(inflater, container, false);
        encodeVM = new ViewModelProvider(requireActivity()).get(EncodeVM.class);
        decodeVM = new ViewModelProvider(requireActivity()).get(DecodeVM.class);

        encodeVM.containerFileMutableLiveData.observe(getViewLifecycleOwner(), data -> {
            String fileName = data.getInFileName().substring(data.getInFileName().lastIndexOf("/") + 1);
            binding.tvSelectFileFragmentFileNameDetails.setText(fileName);

            DecimalFormat df = new DecimalFormat("0.00");
            float fileSizeByte = data.getInContainerFileByteArray().length;
            double fileSizeMB = fileSizeByte / 1000000;
            String fileSize = df.format(fileSizeMB) + " MB";
            binding.tvSelectFileFragmentFileSizeDetails.setText(fileSize);
        });

        decodeVM.containerFileMutableLiveData.observe(getViewLifecycleOwner(), data -> {
            String fileName = data.getInFileName().substring(data.getInFileName().lastIndexOf("/") + 1);
            binding.tvSelectFileFragmentFileNameDetails.setText(fileName);

            DecimalFormat df = new DecimalFormat("0.00");
            float fileSizeByte = data.getInContainerFileByteArray().length;
            double fileSizeMB = fileSizeByte / 1000000;
            String fileSize = df.format(fileSizeMB) + " MB";
            binding.tvSelectFileFragmentFileSizeDetails.setText(fileSize);
        });

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

            if (getActivity() instanceof EncodeActivity) {
                try {
                    FileInputStream fileInputStream = (FileInputStream) getActivity().getContentResolver().openInputStream(uri);
                    encodeVM.setContainerFile(uri.getLastPathSegment(), fileInputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (getActivity() instanceof DecodeActivity) {
                try {
                    FileInputStream fileInputStream = (FileInputStream) getActivity().getContentResolver().openInputStream(uri);
                    decodeVM.setContainerFile(uri.getLastPathSegment(), fileInputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
