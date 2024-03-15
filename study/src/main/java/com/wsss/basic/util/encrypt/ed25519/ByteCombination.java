package com.wsss.basic.util.encrypt.ed25519;

public class ByteCombination {
    public static void main(String[] args) {
        byte smallNumber = 12;  // 0-16之间的数字
        byte largeNumber = -30; // 正负50以内的数字

        byte combined = combineBytes(smallNumber, largeNumber);
        System.out.println("Combined Byte: " + combined);

        byte[] separated = separateBytes(combined);
        System.out.println("Separated Bytes: " + separated[0] + ", " + separated[1]);
    }

    // 将两个byte合成一个byte
    public static byte combineBytes(byte smallNumber, byte largeNumber) {
        return (byte) ((smallNumber << 4) | (largeNumber & 0x0F));
    }

    // 从合成的byte中分离出两个原始byte
    public static byte[] separateBytes(byte combined) {
        byte smallNumber = (byte) ((combined >> 4) & 0x0F);
        byte largeNumber = (byte) (combined & 0x0F);
        return new byte[]{smallNumber, largeNumber};
    }
}
