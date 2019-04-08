package com.wsss.basic.common;


import java.io.ByteArrayOutputStream;
//import java.util.Base64;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.apache.commons.codec.binary.Base64;
/**
 * 文件压缩与解压
 * 使用deflate算法和base64来压缩数据
 * @author hasee
 *
 */
public final class CompressionUtils {

	/**
	 * 文件压缩
	 */
	public static String deflatAndBase64Encode(byte[] inputByte) {

		ByteArrayOutputStream o = new ByteArrayOutputStream(inputByte.length);

		final byte[] out = new byte[1024];
		Deflater compressor = new Deflater();
		compressor.setInput(inputByte);

		compressor.finish(); // 调用时，指示压缩应当以输入缓冲区的当前内容结尾
		int compressedDataLength = 0;
		while (!compressor.finished()) {// boolean finished();如果已到达压缩数据输出流的结尾，则返回 true。
			compressedDataLength = compressor.deflate(out);// 使用压缩数据填充指定缓冲区。
			o.write(out, 0, compressedDataLength);
		}

		compressor.end();// 关闭解压缩器并放弃所有未处理的输入

		// return new String(Base64.getEncoder().encode(o.toByteArray()));
		return new String(Base64.encodeBase64(o.toByteArray()));
	}

	/**
	 * 文件解压
	 */
	public static String base64DecodeAndInflat(String base64String) throws Exception {
		// byte[] inputByte = Base64.getDecoder().decode(base64String);
		byte[] inputByte = Base64.decodeBase64(base64String);
		ByteArrayOutputStream bos = new ByteArrayOutputStream(inputByte.length);

		Inflater decompressor = new Inflater();
		try {
			decompressor.setInput(inputByte);
			final byte[] out = new byte[1024];
			while (!decompressor.finished()) {
				int count = decompressor.inflate(out);
				bos.write(out, 0, count);
			}
		} finally {
			decompressor.end();
		}
		return new String(bos.toByteArray(), "UTF-8");
	}
}
