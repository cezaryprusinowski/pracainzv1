package com.example.pracainzv1;

import android.util.Log;
import android.util.TimingLogger;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.LongBuffer;
import java.util.Arrays;
import java.util.BitSet;

public class UnhideVM extends ViewModel {
    public MutableLiveData<ContainerFile> containerFileMutableLiveData;
    public MutableLiveData<String> messageFromFileMutableLiveData;
    ContainerFile containerFile;



    public UnhideVM() {
        containerFileMutableLiveData = new MutableLiveData<>();
        messageFromFileMutableLiveData = new MutableLiveData<>();
    }


    public void setContainerFile(String fileName, FileInputStream fileInputStream) throws IOException {
        containerFile = new ContainerFile(fileName, fileInputStream);
        containerFileMutableLiveData.setValue(containerFile);
    }

    public void readMessage() {
        ByteBuffer byteBuffer = ByteBuffer.wrap(containerFile.getInContainerFileByteArray());
        InputData inputData = new InputData(byteBuffer);

        String message = inputData.getMessageFromFile();

        if (message.length()>0)
            messageFromFileMutableLiveData.setValue(message);
        else
            messageFromFileMutableLiveData.setValue("Brak wiadomości w pliku.");
    }

}
