package com.example.pracainzv1;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public int hideMessageAndGenerateFile (File androidFilesDirectory) throws Exception {
//        WriteLog imageFileDataOperations = new WriteLog(containerFile, textFile);
//        imageFileDataOperations.run();

        ByteBuffer byteBuffer1 = ByteBuffer.wrap(containerFile.getInContainerFileByteArray());
        ByteBuffer byteBuffer2 = ByteBuffer.wrap(textFile.getInTextFileByteArray());

        OutputData outputData;

        switch (containerFile.getFileType()) {
            case AUDIO:
                outputData = new OutputDataAudio(byteBuffer1, byteBuffer2);
                break;
            case IMAGE:
                outputData = new OutputDataImage(byteBuffer1, byteBuffer2);
                break;
//            case VIDEO:
//                outputData = new OutputDataImage(byteBuffer1, byteBuffer2);
//                break;
            default:
                outputData = null;
        }

        if (outputData==null){
            return 1;
        }

        ByteBuffer outputDataByteBuffer = outputData.run();

        File OutputFile = new File(androidFilesDirectory, createOutputFileName());
        FileOutputStream fos = new FileOutputStream(OutputFile);

        WritableByteChannel channel = Channels.newChannel(fos);
        channel.write(outputDataByteBuffer);

        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String createOutputFileName(){
        String extension;

        switch (containerFile.getFileType()){
            case AUDIO:
                extension=".mp3";
                break;
            case IMAGE:
                extension=".jpg";
                break;
            case VIDEO:
                extension=".mp4";
                break;
            default:
                extension="";
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        LocalDateTime localDateTime = LocalDateTime.now();
        return "STENO_" + dateTimeFormatter.format(localDateTime) + extension;
    }


}
