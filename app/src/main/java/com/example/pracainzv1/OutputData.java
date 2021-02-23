package com.example.pracainzv1;

import android.util.Log;

import java.nio.ByteBuffer;

public class OutputData {
    private final ByteBuffer inputContainerDataByteBuffer;
    private final ByteBuffer inputTextDataByteBuffer;
    private ByteBuffer messageWithFlagsByteBuffer;
    private ByteBuffer outputFileDataByteBuffer;
    private final byte[] messageFlag = "FLAG".getBytes();
    private int endOfMetadataIndex;


    public OutputData(ByteBuffer inputContainerDataByteBuffer, ByteBuffer inputTextDataByteBuffer) {
        this.inputContainerDataByteBuffer = inputContainerDataByteBuffer;
        this.inputTextDataByteBuffer = inputTextDataByteBuffer;
    }

    public void run() throws Exception {
        endOfMetadataIndex = findEndOfImageMetadataIndex(inputContainerDataByteBuffer);

        if (endOfMetadataIndex <= 0)
            throw new Exception("This isn't image file or format is incorrect. Correct format is .jpg");

        messageWithFlagsByteBuffer = addFlagsToMessageData(inputTextDataByteBuffer);

        hideMessageDataInContainerData();

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

    private ByteBuffer addFlagsToMessageData (ByteBuffer byteBuffer){
//        Log.v("Byte", String.valueOf(byteBuffer.remaining()));
        return ByteBuffer.allocate(byteBuffer.remaining() + messageFlag.length + messageFlag.length)
                .put(messageFlag)
                .put(byteBuffer)
                .put(messageFlag);
    }

    private void hideMessageDataInContainerData(){
        byte[] messageBytes = new byte[messageWithFlagsByteBuffer.position(0).remaining()];
        messageWithFlagsByteBuffer
                .get(messageBytes)
                .position(0);
        byte[] necessaryContainerBytes = new byte[inputContainerDataByteBuffer
                                                    .position(endOfMetadataIndex)
                                                    .limit(endOfMetadataIndex+messageBytes.length*8)
                                                    .remaining()];
        inputContainerDataByteBuffer
                .get(necessaryContainerBytes)
                .position(endOfMetadataIndex)
                .limit(endOfMetadataIndex+messageBytes.length*8*8);


        Log.v("Byte", Integer.toBinaryString((messageBytes[3] & 0xFF) + 0x100).substring(1));
//        messageBytes[1] = (byte) (messageBytes[0] | (1)); //zamiana ostatniego bitu na 1
        messageBytes[3] = (byte) (messageBytes[0] & ~(1)); //zamiana ostatniego bitu na 0
        Log.v("Byte", Integer.toBinaryString((messageBytes[3] & 0xFF) + 0x100).substring(1));
        //TODO pętla na necessaryContainerBytes i pozmieniać lsb
        //TODO necessaryContainerBytes wsadzić spowrotem do inputContainerDataByteBuffer

    }
}
