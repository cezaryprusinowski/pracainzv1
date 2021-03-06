package com.example.pracainzv1;

import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;

public class ContainerFile {

    private final String inFileName;
    private final FileInputStream inContainerFileInputStream;
    private final byte[] inContainerFileByteArray;
    private FileType fileType;


    public ContainerFile(String fileName, FileInputStream fileInputStream) throws IOException {
        inFileName = fileName;
        inContainerFileInputStream = fileInputStream;
        inContainerFileByteArray = IOUtils.toByteArray(inContainerFileInputStream);
        setFileType();
    }

    public String getInFileName() {
        return inFileName;
    }

    public FileInputStream getInContainerFileInputStream() {
        return inContainerFileInputStream;
    }

    public byte[] getInContainerFileByteArray() {
        return inContainerFileByteArray;
    }

    public enum FileType {AUDIO, VIDEO, IMAGE, OTHER}

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType() {
        String extension = inFileName.substring(inFileName.lastIndexOf("."));
        switch (extension) {
            case ".jpg":
                fileType = FileType.IMAGE;
                break;
            case ".mp3":
                fileType = FileType.AUDIO;
                break;
            case ".mp4":
                fileType = FileType.VIDEO;
                break;
            default:
                fileType = FileType.OTHER;
        }
    }

}
