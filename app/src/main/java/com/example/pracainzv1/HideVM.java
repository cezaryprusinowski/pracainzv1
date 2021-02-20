package com.example.pracainzv1;

import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.BitSet;

public class HideVM extends ViewModel {
    public MutableLiveData<ContainerFile> containerFileMutableLiveData;
    public MutableLiveData<TextFile> textFileMutableLiveData;
    ContainerFile containerFile;
    TextFile textFile;
//    private String filePath="/data/data/com.example.pracainzv1/files/foo.txt";
//    private ContainerFile containerFile;


    public HideVM() {
        containerFileMutableLiveData = new MutableLiveData<>();
        textFileMutableLiveData = new MutableLiveData<>();
    }


    public void setContainerFile(String fileName, FileInputStream fileInputStream) throws IOException {
        containerFile = new ContainerFile(fileName, fileInputStream);
        containerFileMutableLiveData.setValue(containerFile);
    }

    public void setTextFile(String fileName, FileInputStream fileInputStream) throws IOException {
        textFile = new TextFile(fileName, fileInputStream);
        textFileMutableLiveData.setValue(textFile);
    }

    public void hideMessageAndGenerateFile (){
        ImageFileDataOperations imageFileDataOperations = new ImageFileDataOperations(containerFile, textFile);
        imageFileDataOperations.run();
    }


}
