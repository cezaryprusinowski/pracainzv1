package com.example.pracainzv1;

import android.util.Log;
import android.util.TimingLogger;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.LongBuffer;
import java.util.Arrays;
import java.util.BitSet;

public class UnhideVM extends ViewModel {
    public MutableLiveData<ContainerFile> containerFileMutableLiveData;
    public MutableLiveData<String> messageFromFileMutableLiveData;
    ContainerFile containerFile;
    String messageFromFile;



    public UnhideVM() {
        containerFileMutableLiveData = new MutableLiveData<>();
        messageFromFileMutableLiveData = new MutableLiveData<>();
    }


    public void setContainerFile(String fileName, FileInputStream fileInputStream) throws IOException {
        containerFile = new ContainerFile(fileName, fileInputStream);
        containerFileMutableLiveData.setValue(containerFile);
    }

    public void readMessage() {
        byte[] messageFLAGByteArray = "FLAG".getBytes();
        byte[] temp = new byte[0];
        byte oneByte;
        boolean result1 = false;
        boolean result2 = false;
        int positionStart = 0;
        int positionStop = 0;
        byte[] containerFileByteArray = containerFile.getInContainerFileByteArray();
        byte[] oneByteFromContainerFile = new byte[1];
        BitSet BitSetFromOneByteFromContainerFile;
        BitSet dataToProcessBitSet = new BitSet();;
        byte[] dataToProcessByteArray;


////        for (int i = 0; i < containerFileByteArray.length; i++) {
//        for (int i = 0; i < containerFileByteArray.length; i++) {
//            //oneByteFromContainerFile[0] = containerFileByteArray[i];
//
//            oneByte = containerFileByteArray[i];
//
//            BitSetFromOneByteFromContainerFile = BitSet.valueOf(new byte[] { oneByte });
//
//            dataToProcessBitSet.set(i, BitSetFromOneByteFromContainerFile.get(0));
//
//        }

        dataToProcessByteArray = dataToProcessBitSet.toByteArray();

        for (int i = 0; i < dataToProcessByteArray.length - messageFLAGByteArray.length; i++) {
            temp = Arrays.copyOfRange(dataToProcessByteArray, i, i + messageFLAGByteArray.length);

            if (!result1) {
                result1 = Arrays.equals(temp, messageFLAGByteArray);
                continue;
            }
            if (result1 && !result2) {
                result2 = Arrays.equals(temp, messageFLAGByteArray);
            }
            if (result1 && (positionStart == 0)) {
                positionStart = i + messageFLAGByteArray.length - 1;
            }
            if (result2 && (positionStop == 0)) {
                positionStop = i;
                break;
            }
        }

        if (result1 && result2) {
            temp = Arrays.copyOfRange(containerFileByteArray, positionStart, positionStop);
            messageFromFileMutableLiveData.setValue(new String(temp));
        } else {
            messageFromFileMutableLiveData.setValue("Plik nie zawiera ukrytej wiadomości.");
        }

    }

    //stary odczyt
//    public void readMessage(){
//        byte[] messageFLAGByteArray = "FLAG".getBytes();
//        byte[] containerFileByteArray = containerFile.getInContainerFileByteArray();
//        byte[] temp = new byte[0];
//        boolean result1 = false;
//        boolean result2 = false;
//        int possitionStart = 0;
//        int possitionStop = 0;
//
//        for (int i=0; i<containerFileByteArray.length - messageFLAGByteArray.length; i++){
//            temp = Arrays.copyOfRange(containerFileByteArray,i,i+messageFLAGByteArray.length);
//
//            if (!result1){
//                result1 = Arrays.equals(temp, messageFLAGByteArray);
//                continue;
//            }
//            if(result1 && !result2){
//                result2 = Arrays.equals(temp, messageFLAGByteArray);
//            }
//            if (result1 && (possitionStart==0)){
//                possitionStart = i + messageFLAGByteArray.length-1;
//            }
//            if (result2 && (possitionStop==0)){
//                possitionStop = i;
//                break;
//            }
//        }
//
//        if (result1 && result2){
//            temp = Arrays.copyOfRange(containerFileByteArray,possitionStart,possitionStop);
//            messageFromFileMutableLiveData.setValue(new String(temp));
//        }
//        else{
//            messageFromFileMutableLiveData.setValue("Plik nie zawiera ukrytej wiadomości.");
//        }
//    }


    public static int indexOfBytePictureMetadataStart(byte[] array){
        byte[] StartOfScan =new byte[] {(byte) 0xFF, (byte) 0xDA};

        if (array.length == 0) {
            return 0;
        }

        for(int i=0; i<array.length; i++){
            if (array[i] == StartOfScan[0] && array[i+1] == StartOfScan[1] ){
                return i+2+12;
            }
        }

        return -1;
    }


}
