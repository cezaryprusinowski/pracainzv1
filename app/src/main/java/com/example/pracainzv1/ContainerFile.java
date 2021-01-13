package com.example.pracainzv1;

//public class ContainerFile extends File {
public class ContainerFile {

    private String FileName;
    private String FilePath;
    private long FileBytesCount;
    private int AvailableChars;

    public ContainerFile(String fileName, String filePath, long fileBytesCount, int avalibleChars) {
        FileName = fileName;
        FilePath = filePath;
        FileBytesCount = fileBytesCount;
        AvailableChars = avalibleChars;
    }

    public ContainerFile(String fileName, String filePath) {
        FileName = fileName;
        FilePath = filePath;
    }

    public ContainerFile(String fileName, String filePath, long fileBytesCount) {
        FileName = fileName;
        FilePath = filePath;
        FileBytesCount = fileBytesCount;
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

    public long getFileBytesCount() {
        return FileBytesCount;
    }

    public String getFileBytesCountString() {
        return (FileBytesCount+"");
    }

    public void setFileBytesCount(long fileBytesCount) {
        FileBytesCount = fileBytesCount;
    }

    public int getAvailableChars() {
        return AvailableChars;
    }

    public void setAvailableChars(int availableChars) {
        AvailableChars = availableChars;
    }
}
