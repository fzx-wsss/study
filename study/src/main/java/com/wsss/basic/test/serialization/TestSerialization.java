package com.wsss.basic.test.serialization;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.wsss.basic.util.serialize.ObjectSerializeUtil;

public class TestSerialization {
	public static void main(String[] args) throws UnsupportedEncodingException, IOException {
		
		String h = "hello world";
		byte[] b1 = ObjectSerializeUtil.getStrFromObj(h);
		byte[] b2 = h.getBytes();
		System.out.println(b1.length);
		System.out.println(b2.length);
		
	}
}
