package com.example.pracainzv1;

import androidx.annotation.NonNull;

import java.io.File;

//public class ContenerFile extends File {
public class ContenerFile {

    private String FileName;
    private String FilePath;
    private byte AvailableSpace;
    private int AvalibleChars;

    public ContenerFile(String fileName, String filePath, byte availableSpace, int avalibleChars) {
        FileName = fileName;
        FilePath = filePath;
        AvailableSpace = availableSpace;
        AvalibleChars = avalibleChars;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public String getFilePath() {
        return FilePath;
    }

    public void setFilePath(String filePath) {
        FilePath = filePath;
    }

    public byte getAvailableSpace() {
        return AvailableSpace;
    }

    public void setAvailableSpace(byte availableSpace) {
        AvailableSpace = availableSpace;
    }

    public int getAvalibleChars() {
        return AvalibleChars;
    }

    public void setAvalibleChars(int avalibleChars) {
        AvalibleChars = avalibleChars;
    }
}
