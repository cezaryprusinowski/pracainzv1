package com.example.pracainzv1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pracainzv1.databinding.SelectFileFragmentBinding;

public class SelectFileFragment extends Fragment {

    private SelectFileFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = SelectFileFragmentBinding.inflate(inflater,container,false);

        setListeners();

        return binding.getRoot();
    }

    private void setListeners() {
        binding.btnAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Select audio file", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Select image file", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Select video file", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
