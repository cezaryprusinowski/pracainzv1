package com.example.pracainzv1;

import android.util.Log;

import java.nio.ByteBuffer;
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
        Log.v("Pos", String.valueOf(first));

        return "";
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

    private int findSecondFlagIndex(){
        return 0;
    }
}
