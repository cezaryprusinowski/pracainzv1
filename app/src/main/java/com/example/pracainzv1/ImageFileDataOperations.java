package com.example.pracainzv1;

import android.util.Log;

import java.nio.ByteBuffer;

public class ImageFileDataOperations {
    ByteBuffer InputImageData;

    public ImageFileDataOperations(ContainerFile containerFile) {
        InputImageData = ByteBuffer.wrap(containerFile.getInContainerFileByteArray());
    }

    public void run(){
        LogInputImageDataAsBinary();
    }

    private void LogInputImageDataAsBinary(){
        String inputData = InputImageData.toString();
        Log.i("InputData",inputData);
    }
}
