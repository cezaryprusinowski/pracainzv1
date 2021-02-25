package com.example.pracainzv1;

import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.BitSet;

public class InputData {
    private final ByteBuffer inputContainerDataByteBuffer;
    private final BitSet flagBitSet = BitSet.valueOf("FLAG".getBytes());
    private int firstFlagIndex;
    private int secondFlagIndex;

    private WriteLog writeLog = new WriteLog();

    public InputData(ByteBuffer inputContainerDataByteBuffer) {
        this.inputContainerDataByteBuffer = inputContainerDataByteBuffer;
    }

    public String getMessageFromFile(){

        int first = findFirstFlagIndex();
        if (first==0)
            return "";
        int second = findSecondFlagIndex(first);
//        Log.v("Pos", String.valueOf(first));
//        Log.v("Pos", String.valueOf(second));

        String message = findMessageInFile(first,second);
//        Log.v("Mess", message);



        return message;
    }

    private int findFirstFlagIndex(){
        boolean result = false;
        byte[] bytes = new byte[32];

        while(!result){
            BitSet bytesLSBBitSet = new BitSet();
            inputContainerDataByteBuffer.get(bytes);


            for (int i=0; i < bytes.length; i++){
                if (((bytes[i]) & 1) == 1)
                    bytesLSBBitSet.set(i);
            }

            if ((inputContainerDataByteBuffer.position() == inputContainerDataByteBuffer.capacity())
                    || inputContainerDataByteBuffer.position() > inputContainerDataByteBuffer.capacity()-bytes.length) {
                break;
            }

            if (bytesLSBBitSet.equals(flagBitSet)) {
                result = true;
                return inputContainerDataByteBuffer.position();
            }

            inputContainerDataByteBuffer.position(inputContainerDataByteBuffer.position()-31);
        }
        return 0;
    }

    private int findSecondFlagIndex(int firstFlagIndex){
        boolean result = false;
        byte[] bytes = new byte[32];
        inputContainerDataByteBuffer.position(firstFlagIndex).limit(inputContainerDataByteBuffer.capacity());

        while(!result){
            BitSet bytesLSBBitSet = new BitSet();
            inputContainerDataByteBuffer.get(bytes);


            for (int i=0; i < bytes.length; i++){
                if (((bytes[i]) & 1) == 1)
                    bytesLSBBitSet.set(i);
            }

            if ((inputContainerDataByteBuffer.position() == inputContainerDataByteBuffer.capacity())
                    || inputContainerDataByteBuffer.position() > inputContainerDataByteBuffer.capacity()-bytes.length) {
                break;
            }

            if (bytesLSBBitSet.equals(flagBitSet)) {
                result = true;
                return inputContainerDataByteBuffer.position()-32;
            }

            inputContainerDataByteBuffer.position(inputContainerDataByteBuffer.position()-31);
        }
        return 0;
    }

    private String findMessageInFile(int first, int second){
        inputContainerDataByteBuffer.position(first).limit(second);
        byte[] bytes = new byte[inputContainerDataByteBuffer.limit()-inputContainerDataByteBuffer.position()];
        inputContainerDataByteBuffer.get(bytes);
        BitSet bitSet = new BitSet();

        for (int i=0; i<bytes.length; i++){
            if (((bytes[i]) & 1) == 1)
                bitSet.set(i);
        }
        byte[] messageBytes = bitSet.toByteArray();
        return new String(messageBytes);
    }
}
