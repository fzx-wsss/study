package com.wsss.basic.util.serialize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;

public class ObjectSerializeUtil {
	public static void main(String[] args) throws IOException,
			ClassNotFoundException {
		Customer customer = new Customer("阿蜜果", 24);
		byte[] serStr = getStrFromObj(customer);

		customer = (Customer) getObjFromStr(serStr);
		System.out.println(customer);
	}
	
	
	/**
	 * @param serStr
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @作者 王建明
	 * @创建日期 2012-10-12
	 * @创建时间 上午10:56:27
	 * @描述 —— 将字符串反序列化成对象
	 */
	public static Object getObjFromStr(byte[] serStr)
			throws UnsupportedEncodingException, IOException,
			ClassNotFoundException {
		if(null == serStr) {
			return null;
		}
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
				serStr);
		ObjectInputStream objectInputStream = new ObjectInputStream(
				byteArrayInputStream);
		Object result = objectInputStream.readObject();
		objectInputStream.close();
		byteArrayInputStream.close();

		return result;
	}

	/**
	 * @return
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 * @作者 王建明
	 * @创建日期 2012-10-12
	 * @创建时间 上午10:51:07
	 * @描述 —— 将对象序列化成字符串
	 */
	public static byte[] getStrFromObj(Object obj) throws IOException,
			UnsupportedEncodingException {
		if(null == obj) {
			return null;
		}
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(
				byteArrayOutputStream);
		objectOutputStream.writeObject(obj);
		byte[] serStr = byteArrayOutputStream.toByteArray();

		objectOutputStream.close();
		byteArrayOutputStream.close();
		return serStr;
	}
}

/**
 * @作者 王建明
 * @创建日期 2012-10-12
 * @创建时间 上午10:59:07
 * @描述 —— 测试对象封装
 */
class Customer implements Serializable {
	private String name;
	private int age;

	public Customer(String name, int age) {
		this.name = name;
		this.age = age;
	}

	public String toString() {
		return "name=" + name + ", age=" + age;
	}
}
