package com.example.pracainzv1;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.BitSet;

public class EncodeVM_copy extends ViewModel {
    public MutableLiveData<ContainerFile> containerFileMutableLiveData;
    public MutableLiveData<TextFile> textFileMutableLiveData;
    ContainerFile containerFile;
    TextFile textFile;
//    private String filePath="/data/data/com.example.pracainzv1/files/foo.txt";
//    private ContainerFile containerFile;


    public EncodeVM_copy() {
        containerFileMutableLiveData = new MutableLiveData<>();
        textFileMutableLiveData = new MutableLiveData<>();
    }

    public static int indexOf(byte[] array, byte[] target) {

        if (target.length == 0) {
            return 0;
        }

        outer:
        for (int i = 0; i < array.length - target.length + 1; i++) {
            for (int j = 0; j < target.length; j++) {
                if (array[i + j] != target[j]) {
                    continue outer;
                }
            }
            return i;
        }
        return -1;
    }

    public static int indexOfBytePictureMetadataStart(byte[] array) {
        byte[] StartOfScan = new byte[]{(byte) 0xFF, (byte) 0xDA};

        if (array.length == 0) {
            return 0;
        }

        for (int i = 0; i < array.length; i++) {
            if (array[i] == StartOfScan[0] && array[i + 1] == StartOfScan[1]) {
                return i + 2 + 12;
            }
        }

        return -1;
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
    public void hideMessageAndGenerateFile(File androidFilesDir) throws IOException {
        /** MAIN */
        byte[] TextMessageBytes = textFile.getInTextFileByteArray();
        byte[] ContainerFileBytes = containerFile.getInContainerFileByteArray();
        byte[] MessageFlagBytes = "FLAG".getBytes();
        byte[] MessageWithFlagsBytes = ByteBuffer.allocate(TextMessageBytes.length + MessageFlagBytes.length + MessageFlagBytes.length)
                .put(MessageFlagBytes)
                .put(TextMessageBytes)
                .put(MessageFlagBytes)
                .array();
        BitSet containerByteBitSet = BitSet.valueOf(ContainerFileBytes);
        BitSet messageWithFlagsBitSet = BitSet.valueOf(MessageWithFlagsBytes);

        int indexOfBitStart = indexOfBytePictureMetadataStart(ContainerFileBytes) * 8;

        /** CHECK */
        int lengthBefore = containerByteBitSet.length();
        /** CHECK */

        //TODO zrobić pętlę na byte in ContainerFileBytes, każdy byte zapisać w byte[0], byte[0] zapisać do BiteSet, zmienić LSB, odwrotyn proces
        for (int i = 0; i < messageWithFlagsBitSet.length() + 1; i++) {
            byte[] oneByte = new byte[1];
            oneByte[0] = ContainerFileBytes[i + indexOfBitStart];
            BitSet oneBitSet = BitSet.valueOf(oneByte);
            oneBitSet.set(0, messageWithFlagsBitSet.get(i));
            ContainerFileBytes[i + indexOfBitStart] = oneBitSet.toByteArray()[0];
        }

        //stare rozwiązanie ukrywania wiadomosci w pliku
//        for(int i=0; i<messageWithFlagsBitSet.length()+1; i++){
//            containerByteBitSet.set(i+indexOfBitStart,messageWithFlagsBitSet.get(i));
//        }
//        ContainerFileBytes = containerByteBitSet.toByteArray();

        ByteArrayInputStream bis = new ByteArrayInputStream(ContainerFileBytes);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        LocalDateTime localDateTime = LocalDateTime.now();
        String outputFileName = "STENO_" + dateTimeFormatter.format(localDateTime) + ".jpg";

        File outFile = new File(androidFilesDir, outputFileName);
        FileOutputStream fos = new FileOutputStream(outFile);
        IOUtils.copy(bis, fos);
        /** MAIN */

        /** CHECK */
        byte[] tempBytesArray = Arrays.copyOfRange(ContainerFileBytes, indexOfBytePictureMetadataStart(ContainerFileBytes), indexOfBytePictureMetadataStart(ContainerFileBytes) + MessageWithFlagsBytes.length);
        String hidenMessage = new String(tempBytesArray);
        Log.v("Test", "Ukryta wiadomość: " + hidenMessage);

        int lengthAfter = containerByteBitSet.length();
        Log.v("Test", "Długosć przed: " + lengthBefore);
        Log.v("Test", "Długość po: " + lengthAfter);

        /** CHECK */

//        BitSet temp;
//        boolean find = false;
//        int startPossition = 0;
//        for (int i=0; i<containerByteBitSet.length(); i++){
//            temp = containerByteBitSet.get(i,i+StartOfDataBitSet.length());
//            find = StartOfDataBitSet.equals(temp);
//            startPossition=i;
//
//            if (find){
//                Log.v("Test","Start position break: " + startPossition);
//                break;
//            }
//        }
//        temp = containerByteBitSet.get(1,1+StartOfDataBitSet.length());
//        Log.v("Test","Temp: " + temp.length());
//        Log.v("Test","Start: " + StartOfDataBitSet.length());
//        Log.v("Test","Start position: " + startPossition);
//        Log.v("Test","Start position + length: " + (startPossition + StartOfDataBitSet.length()));


        //        String messageByteString = "";
//        String containerByteString = "";
//        int progress = 0;
//        for (byte b : MessageWithFlagsBytes){
//            messageByteString = Integer.toBinaryString(b);
////            Log.v("Test", "MessageFirstByteBinary: " + messageByteString);
////            Log.v("Test", "MessageFirstByteString: " + String.valueOf(b));
////            Log.v("Test", "ContainerFirstByteString: " + String.valueOf(ContainerFileBytes[progress]));
////            Log.v("Test", "ContainerFirstByteBinary: " + Integer.toBinaryString(-1));
////            b = (byte) (b | (1));
////            Log.v("Test", "MessageLSBTo1: " + Integer.toBinaryString(b));
////            b = (byte) (b & ~(1));
////            b = (byte) (b & ~(1));
////            Log.v("Test", "MessageLSBTo0: " + Integer.toBinaryString(b));
////            break;
//            for (char ch : messageByteString.toCharArray()){
//                containerByteString = Integer.toBinaryString(ContainerFileBytes[progress]);
//                containerByteString = containerByteString.substring(0, containerByteString.length()-1) + ch;
//                ContainerFileBytes[progress] = Byte.parseByte(containerByteString,2);
//                progress++;
//            }
//        }
    }


}
