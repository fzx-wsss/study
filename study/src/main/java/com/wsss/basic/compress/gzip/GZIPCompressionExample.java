package com.wsss.basic.compress.gzip;

import java.io.*;
import java.util.zip.GZIPOutputStream;
import java.util.zip.GZIPInputStream;

public class GZIPCompressionExample {

    public static void main(String[] args) {
        String inputFileName = "file.txt";

        try {
            // 从文件读取内容
            String content = readFromFile(inputFileName);

            // 将内容压缩并获取压缩后的字节数组
            byte[] compressedData = compressString(content);

            // 计算压缩率
            double compressionRatio = calculateCompressionRatio(content, compressedData);

            System.out.println("Original Data: " + content);
            System.out.println("Compressed Data: " + new String(compressedData));
            System.out.println("Compression Ratio: " + compressionRatio);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String readFromFile(String fileName) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader((new InputStreamReader(GZIPCompressionExample.class.getResourceAsStream(fileName)))) ) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line);
            }
        }
        return content.toString();
    }

    private static byte[] compressString(String input) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream)) {
            gzipOutputStream.write(input.getBytes());
        }
        return byteArrayOutputStream.toByteArray();
    }

    private static double calculateCompressionRatio(String original, byte[] compressed) {
        double originalSize = original.length();
        double compressedSize = compressed.length;
        return (originalSize - compressedSize) / originalSize;
    }
}
