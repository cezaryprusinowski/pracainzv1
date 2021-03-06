package com.example.pracainzv1;

import android.util.Log;

import java.nio.ByteBuffer;
import java.util.BitSet;

public class OutputDataVideo extends OutputData{
    private final ByteBuffer inputContainerDataByteBuffer;
    private final ByteBuffer inputTextDataByteBuffer;
    private ByteBuffer messageWithFlagsByteBuffer;
    private final byte[] messageFlag = "FLAG".getBytes();
    private int endOfMetadataIndex;


    public OutputDataVideo(ByteBuffer inputContainerDataByteBuffer, ByteBuffer inputTextDataByteBuffer) {
        this.inputContainerDataByteBuffer = inputContainerDataByteBuffer;
        this.inputTextDataByteBuffer = inputTextDataByteBuffer;
    }

    public ByteBuffer run() throws Exception {
        endOfMetadataIndex = findEndOfImageMetadataIndex(inputContainerDataByteBuffer);

        if (endOfMetadataIndex <= 0)
            throw new Exception("This isn't correct file. Correct format is .jpg, .mp3, .mp4");

        messageWithFlagsByteBuffer = addFlagsToMessageData(inputTextDataByteBuffer);

        return hideMessageDataInContainerData();
    }

    private int findEndOfImageMetadataIndex(ByteBuffer byteBuffer){
        byte[] startOfImageDataMarker =new byte[] {(byte) 0x6D, (byte) 0x64, (byte) 0x61, (byte) 0x74};
        byte[] bytes = new byte[byteBuffer.remaining()];
        byteBuffer.get(bytes);

        if (!byteBuffer.hasArray()) {
            return 0;
        }

        int skipFirstMatch = 0;
        for(int i=0; i<bytes.length; i++){
            if (bytes[i] == startOfImageDataMarker[0] && bytes[i+1] == startOfImageDataMarker[1] && bytes[i+2] == startOfImageDataMarker[2] && bytes[i+3] == startOfImageDataMarker[3] ){
                skipFirstMatch=1;
            }
            if (skipFirstMatch==1 && bytes[i] == startOfImageDataMarker[0] && bytes[i+1] == startOfImageDataMarker[1] && bytes[i+2] == startOfImageDataMarker[2] && bytes[i+3] == startOfImageDataMarker[3] ){
                return i+16;
            }
        }
        return -1;
    }

    private ByteBuffer addFlagsToMessageData (ByteBuffer byteBuffer){
        return ByteBuffer.allocate(byteBuffer.remaining() + messageFlag.length + messageFlag.length)
                .put(messageFlag)
                .put(byteBuffer)
                .put(messageFlag);
    }

    private ByteBuffer hideMessageDataInContainerData(){

        byte[] messageBytes = new byte[messageWithFlagsByteBuffer.position(0).remaining()];
        messageWithFlagsByteBuffer
                .get(messageBytes)
                .position(0);
        BitSet messageWithFlagsBitSet = BitSet.valueOf(messageBytes);

        byte[] necessaryContainerBytes = new byte[inputContainerDataByteBuffer
                                                    .position(endOfMetadataIndex)
                                                    .limit(endOfMetadataIndex+messageBytes.length*8)
                                                    .remaining()];
        inputContainerDataByteBuffer
                .get(necessaryContainerBytes)
                .position(endOfMetadataIndex)
                .limit(endOfMetadataIndex+messageBytes.length*8);

        for (int i=0; i<necessaryContainerBytes.length; i++){
            if(messageWithFlagsBitSet.get(i))
                //zamiana ostatniego bitu na 1
                necessaryContainerBytes[i] = (byte) (necessaryContainerBytes[i] | (1));
            else
                //zamiana ostatniego bitu na 0
                necessaryContainerBytes[i] = (byte) (necessaryContainerBytes[i] & ~(1));
        }

        ByteBuffer byteBuffer = ByteBuffer.allocate(inputContainerDataByteBuffer
                                                        .position(0)
                                                        .limit(inputContainerDataByteBuffer.capacity())
                                                        .remaining());
        byteBuffer.put(inputContainerDataByteBuffer);
        byteBuffer.position(endOfMetadataIndex).limit(endOfMetadataIndex+messageBytes.length*8);
        byteBuffer.put(necessaryContainerBytes);
        byteBuffer.position(0).limit(inputContainerDataByteBuffer.capacity());

        return byteBuffer;
    }
}
