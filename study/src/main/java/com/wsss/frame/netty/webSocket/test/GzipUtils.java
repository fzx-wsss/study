package com.wsss.frame.netty.webSocket.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GzipUtils {

    private static Logger logger = LoggerFactory.getLogger(GzipUtils.class);

    public GzipUtils() {
    }

    /**
     * @param data
     * @return
     */
    public static byte[] compress(String data) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzipOutputtStream = null;
        try {
            gzipOutputtStream = new GZIPOutputStream(out);
            gzipOutputtStream.write(data.getBytes(Charset.defaultCharset()));
        } catch (Exception e) {
            logger.error("gzipUtils compress data error {}", data);
            return null;
        } finally {
            closeQuietly(gzipOutputtStream);
        }
        return out.toByteArray();
    }

    /**
     * @param data
     * @return
     */
    public static byte[] decompress(byte[] data) {
        ByteArrayOutputStream buffer = null;
        GZIPInputStream gizpInputStream = null;
        try {
            buffer = new ByteArrayOutputStream();
            gizpInputStream = new GZIPInputStream(new ByteArrayInputStream(data));
            int n = -1;
            byte[] bytes = new byte[1024 * 12];
            while (-1 != (n = gizpInputStream.read(bytes))) {
                buffer.write(bytes, 0, n);
            }
            return buffer.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeQuietly(gizpInputStream);
            closeQuietly(buffer);
        }
        return null;
    }

    private static void closeQuietly(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException ioe) {
            // ignore
        }
    }
}
