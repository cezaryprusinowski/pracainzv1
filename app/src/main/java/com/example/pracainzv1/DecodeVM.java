package com.example.pracainzv1;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class DecodeVM extends ViewModel {
    public MutableLiveData<ContainerFile> containerFileMutableLiveData;
    public MutableLiveData<String> messageFromFileMutableLiveData;
    ContainerFile containerFile;


    public DecodeVM() {
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

        if (message.length() > 0)
            messageFromFileMutableLiveData.setValue(message);
        else
            messageFromFileMutableLiveData.setValue("Brak wiadomości w pliku.");
    }

}
