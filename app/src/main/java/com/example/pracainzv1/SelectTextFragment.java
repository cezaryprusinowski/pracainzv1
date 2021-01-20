package com.example.pracainzv1;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.pracainzv1.databinding.SelectTextFragmentBinding;

import java.io.FileInputStream;
import java.io.IOException;

public class SelectTextFragment extends Fragment {

    private static final int PICKFILE_RESULT_CODE = 1;


    private SelectTextFragmentBinding binding;
    private HideVM hideVM;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = SelectTextFragmentBinding.inflate(inflater,container,false);
        hideVM = new ViewModelProvider(requireActivity()).get(HideVM.class);

        setListeners();

        return binding.getRoot();
    }

    private void setListeners() {
        binding.btnTextFile.setOnClickListener(v -> {
            Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
            chooseFile.addCategory(Intent.CATEGORY_OPENABLE);
            chooseFile.setType("text/*");
            startActivityForResult(
                    Intent.createChooser(chooseFile, "Choose a file"),
                    PICKFILE_RESULT_CODE
            );
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICKFILE_RESULT_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            try {
                FileInputStream fileInputStream = (FileInputStream) getActivity().getContentResolver().openInputStream(uri);
                hideVM.setTextFile(uri.getLastPathSegment(),fileInputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
