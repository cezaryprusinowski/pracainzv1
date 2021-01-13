package com.example.pracainzv1;

import android.net.Uri;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HideVM extends ViewModel {
    public MutableLiveData<ContainerFile> containerFileMutableLiveData;
//    private String filePath="/data/data/com.example.pracainzv1/files/foo.txt";
    private ContainerFile containerFile;


    public HideVM() {
        containerFileMutableLiveData = new MutableLiveData<ContainerFile>();
    }


    public void setContainerFile(Uri fileData){
        //BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        File file = new File(fileData.getPath());
//        boolean check =  file.getAbsoluteFile().exists();
        //if (containerFile == null) {
            containerFile = new ContainerFile(
                    file.getName(),
                    file.getPath(),
                    file.length());
        //}
        containerFileMutableLiveData.setValue(containerFile);
    }


}
