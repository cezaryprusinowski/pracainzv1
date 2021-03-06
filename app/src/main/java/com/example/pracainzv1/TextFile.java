package com.example.pracainzv1;

import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;

//public class ContainerFile extends File {
public class TextFile {

    private final String inFileName;
    private final FileInputStream inTextFileInputStream;
    private final byte[] inTextFileByteArray;

    public TextFile(String fileName, FileInputStream fileInputStream) throws IOException {
        inFileName = fileName;
        inTextFileInputStream = fileInputStream;
        inTextFileByteArray = IOUtils.toByteArray(inTextFileInputStream);
    }

    public TextFile(String text) {
        inFileName = "temp";
        inTextFileInputStream = null;
        inTextFileByteArray = text.getBytes();
    }

    public String getInFileName() {
        return inFileName;
    }

    public FileInputStream getInTextFileInputStream() {
        return inTextFileInputStream;
    }

    public byte[] getInTextFileByteArray() {
        return inTextFileByteArray;
    }
}
