package com.example.pracainzv1;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.BitSet;
import java.util.stream.IntStream;

public class ImageFileDataOperations {
    ByteBuffer InputImageDataByteBuffer;
    ByteBuffer InputTextDataByteBuffer;

    public ImageFileDataOperations(ContainerFile containerFile, TextFile textFile) {
        InputImageDataByteBuffer = ByteBuffer.wrap(containerFile.getInContainerFileByteArray());
        InputTextDataByteBuffer = ByteBuffer.wrap(textFile.getInTextFileByteArray());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void run(){

        //logInputImageDataAsHex();
        //logShowInputDataAsBinaryString(InputImageDataByteBuffer,0,4000);
        //String s = logInputDataAsBitSetString(InputTextDataByteBuffer);

        //Log.v("1", s);
    }

    private int findEndOfImageMetadataIndex(ByteBuffer byteBuffer){
        byte[] startOfImageDataMarker =new byte[] {(byte) 0xFF, (byte) 0xDA};
        byte[] bytes = new byte[byteBuffer.remaining()];
        byteBuffer.get(bytes);

        if (!byteBuffer.hasArray()) {
            return 0;
        }

        for(int i=0; i<bytes.length; i++){
            if (bytes[i] == startOfImageDataMarker[0] && bytes[i+1] == startOfImageDataMarker[1] ){
                return i+16; // 16, ponieważ po ostatnim markerze, jest jeszcze 14 bajtów informacji + 2 bajty markera
            }
        }
        return -1;
    }

    private void logShowInputImageDataAsHex(ByteBuffer byteBuffer, int start, int end){
        byte[] bytes = new byte[end-start];
        byteBuffer.get(bytes,start,bytes.length-1);
        String inputData = logByteArrayToHex(bytes);

        int maxLineSize = 32;
        for(int i = 0; i <= inputData.length() / maxLineSize; i++) {
            int sStart = i * maxLineSize;
            int sEnd = (i+1) * maxLineSize;
            sEnd = Math.min(sEnd, inputData.length());
            Log.v("InputImageData", inputData.substring(sStart, sEnd));
        }

    }

    private void logShowInputDataAsBinaryString(ByteBuffer byteBuffer, int start, int end){
        byte[] bytes = new byte[end-start];
        byteBuffer.get(bytes,start,bytes.length-1);
        StringBuilder inputData= new StringBuilder();

        for (byte b: bytes) {
            inputData.append(Integer.toBinaryString((b & 0xFF) + 0x100).substring(1));
        }

        int maxLineSize = 8;
        for(int i = 0; i <= inputData.length() / maxLineSize; i++) {
            int sStart = i * maxLineSize;
            int sEnd = (i+1) * maxLineSize;
            sEnd = Math.min(sEnd, inputData.length());
            Log.v("ImageBinary", inputData.substring(sStart, sEnd));
        }
//        Log.v("InputImageBinary", inputData);

    }

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    private static String logByteArrayToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private String logInputDataAsBitSetString(ByteBuffer byteBuffer){
        BitSet bitSet = BitSet.valueOf(byteBuffer);
        int nbits= byteBuffer.remaining()*8;
        final StringBuilder buffer = new StringBuilder(nbits);
        IntStream.range(0, nbits).mapToObj(i -> bitSet.get(i) ? '1' : '0').forEach(buffer::append);

        String string = buffer.toString();

        byte[] bytes = string.getBytes();
        byte[] result = new byte[bytes.length];
        byte[] temp;

        for (int i=0; i<bytes.length; i++){
            temp = Arrays.copyOfRange(bytes,i,i+8);

            for (int j=0; j<8 ; j++){
                result[i+j] = temp[temp.length - j -1];
            }

            i = i+7;
        }

        return new String(result);
    }



}
