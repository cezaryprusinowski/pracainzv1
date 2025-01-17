package com.example.pracainzv1;

import java.nio.ByteBuffer;
import java.util.BitSet;

public class InputData {
    private final ByteBuffer inputContainerDataByteBuffer;
    private final BitSet flagBitSet = BitSet.valueOf("FLAG".getBytes());
    private int firstFlagIndex;
    private int secondFlagIndex;

    private final WriteLog writeLog = new WriteLog();

    public InputData(ByteBuffer inputContainerDataByteBuffer) {
        this.inputContainerDataByteBuffer = inputContainerDataByteBuffer;
    }

    public String getMessageFromFile() {

        int first = findFirstFlagIndex();
        if (first == 0)
            return "";
        int second = findSecondFlagIndex(first);
//        Log.v("Pos", String.valueOf(first));
//        Log.v("Pos", String.valueOf(second));

        String message = findMessageInFile(first, second);
//        Log.v("Mess", message);


        return message;
    }

    private int findFirstFlagIndex() {
//        boolean result = false;
        byte[] bytes = new byte[32];

        while (true) {
            BitSet bytesLSBBitSet = new BitSet();
            inputContainerDataByteBuffer.get(bytes);

            //pobieranie LSB z kolejnych bajtów
            for (int i = 0; i < bytes.length; i++) {
                if (((bytes[i]) & 1) == 1)
                    bytesLSBBitSet.set(i);
            }

            //jeśli połowa pliku została sprawdzona i nie znaleziono flagi to wyjdź
            if (inputContainerDataByteBuffer.position() >= inputContainerDataByteBuffer.capacity() / 2) {
                return 0;
            }

//            if ((inputContainerDataByteBuffer.position() == inputContainerDataByteBuffer.capacity())
//                    || inputContainerDataByteBuffer.position() > inputContainerDataByteBuffer.capacity()-bytes.length) {
//                //break;
//                result=true;
//            }

            //jeśli flaga została znaleziona to zwróć index bajta
            if (bytesLSBBitSet.equals(flagBitSet)) {
                return inputContainerDataByteBuffer.position();
            }

            inputContainerDataByteBuffer.position(inputContainerDataByteBuffer.position() - 31);
        }

        //return 0;
    }

    private int findSecondFlagIndex(int firstFlagIndex) {
        boolean result = false;
        byte[] bytes = new byte[32];
        inputContainerDataByteBuffer.position(firstFlagIndex).limit(inputContainerDataByteBuffer.capacity());

        while (!result) {
            BitSet bytesLSBBitSet = new BitSet();
            inputContainerDataByteBuffer.get(bytes);


            for (int i = 0; i < bytes.length; i++) {
                if (((bytes[i]) & 1) == 1)
                    bytesLSBBitSet.set(i);
            }

            if ((inputContainerDataByteBuffer.position() == inputContainerDataByteBuffer.capacity())
                    || inputContainerDataByteBuffer.position() > inputContainerDataByteBuffer.capacity() - bytes.length) {
                break;
            }

            if (bytesLSBBitSet.equals(flagBitSet)) {
                result = true;
                return inputContainerDataByteBuffer.position() - 32;
            }

            inputContainerDataByteBuffer.position(inputContainerDataByteBuffer.position() - 31);
        }
        return 0;
    }

    private String findMessageInFile(int first, int second) {
        inputContainerDataByteBuffer.position(first).limit(second);
        byte[] bytes = new byte[inputContainerDataByteBuffer.limit() - inputContainerDataByteBuffer.position()];
        inputContainerDataByteBuffer.get(bytes);
        BitSet bitSet = new BitSet();

        for (int i = 0; i < bytes.length; i++) {
            if (((bytes[i]) & 1) == 1)
                bitSet.set(i);
        }
        byte[] messageBytes = bitSet.toByteArray();
        return new String(messageBytes);
    }
}
