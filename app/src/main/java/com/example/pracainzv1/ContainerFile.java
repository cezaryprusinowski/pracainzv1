package com.example.pracainzv1;

import android.net.Uri;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

//public class ContainerFile extends File {
public class ContainerFile {

    private final String inFileName;
    private final FileInputStream inContainerFileInputStream;
    private final byte[] inContainerFileByteArray;

    public ContainerFile(String fileName, FileInputStream fileInputStream) throws IOException {
        inFileName = fileName;
        inContainerFileInputStream = fileInputStream;
        inContainerFileByteArray = IOUtils.toByteArray(inContainerFileInputStream);
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
}
