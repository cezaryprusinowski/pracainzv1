package com.example.pracainzv1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pracainzv1.databinding.SelectTextFragmentBinding;

public class SelectTextFragment extends Fragment {

    private SelectTextFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = SelectTextFragmentBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
}
