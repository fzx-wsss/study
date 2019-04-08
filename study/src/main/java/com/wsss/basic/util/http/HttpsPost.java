package com.wsss.basic.util.http;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyStore;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

public class HttpsPost {
	/**
	 * 获得KeyStore.
	 * 
	 * @param keyStorePath
	 *            密钥库路径
	 * @param password
	 *            密码
	 * @return 密钥库
	 * @throws Exception
	 */
	public static KeyStore getKeyStore(String password, String keyStorePath)
			throws Exception {
		// 实例化密钥库
		KeyStore ks = KeyStore.getInstance("JKS");
		// 获得密钥库文件流
		FileInputStream is = new FileInputStream(keyStorePath);
		// 加载密钥库
		ks.load(is, password.toCharArray());
		// 关闭密钥库文件流
		is.close();
		return ks;
	}

	/**
	 * 获得SSLSocketFactory.
	 * 
	 * @param password
	 *            密码
	 * @param keyStorePath
	 *            密钥库路径
	 * @param trustStorePath
	 *            信任库路径
	 * @return SSLSocketFactory
	 * @throws Exception
	 */
	public static SSLContext getSSLContext(String password,
			String keyStorePath, String trustStorePath) throws Exception {
		// 实例化密钥库
		KeyManagerFactory keyManagerFactory = KeyManagerFactory
				.getInstance(KeyManagerFactory.getDefaultAlgorithm());
		// 获得密钥库
		KeyStore keyStore = getKeyStore(password, keyStorePath);
		// 初始化密钥工厂
		keyManagerFactory.init(keyStore, password.toCharArray());

		// 实例化信任库
		TrustManagerFactory trustManagerFactory = TrustManagerFactory
				.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		// 获得信任库
		KeyStore trustStore = getKeyStore(password, trustStorePath);
		// 初始化信任库
		trustManagerFactory.init(trustStore);
		// 实例化SSL上下文
		SSLContext ctx = SSLContext.getInstance("TLS");
		// 初始化SSL上下文
		ctx.init(keyManagerFactory.getKeyManagers(),
				trustManagerFactory.getTrustManagers(), null);
		// 获得SSLSocketFactory
		return ctx;
	}

	/**
	 * 初始化HttpsURLConnection.
	 * 
	 * @param password
	 *            密码
	 * @param keyStorePath
	 *            密钥库路径
	 * @param trustStorePath
	 *            信任库路径
	 * @throws Exception
	 */
	public static void initHttpsURLConnection(String password,
			String keyStorePath, String trustStorePath) throws Exception {
		// 声明SSL上下文
		SSLContext sslContext = null;
		// 实例化主机名验证接口
		HostnameVerifier hnv = new MyHostnameVerifier();
		try {
			sslContext = getSSLContext(password, keyStorePath, trustStorePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (sslContext != null) {
			HttpsURLConnection.setDefaultSSLSocketFactory(sslContext
					.getSocketFactory());
		}
		HttpsURLConnection.setDefaultHostnameVerifier(hnv);
	}

	/**
	 * 发送请求.
	 * 
	 * @param httpsUrl
	 *            请求的地址
	 * @param xmlStr
	 *            请求的数据
	 */
	public static String post(String httpsUrl, String xmlStr) {
		HttpsURLConnection urlCon = null;
		try {
			urlCon = (HttpsURLConnection) (new URL(httpsUrl)).openConnection();
			urlCon.setDoInput(true);
			urlCon.setDoOutput(true);
			urlCon.setRequestMethod("POST");
			urlCon.setRequestProperty("Content-Length",
					String.valueOf(xmlStr.getBytes().length));
			urlCon.setUseCaches(false);
			// 设置为gbk可以解决服务器接收时读取的数据中文乱码问题
			urlCon.getOutputStream().write(xmlStr.getBytes());
			urlCon.getOutputStream().flush();
			urlCon.getOutputStream().close();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					urlCon.getInputStream()));
			String line;
			StringBuilder sb = new StringBuilder();
			while ((line = in.readLine()) != null) {
				sb.append(line);
			}
			return sb.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String get(String getUrl) throws Exception {
		URL getURL = new URL(getUrl);
	    HttpURLConnection connection = (HttpURLConnection) getURL.openConnection();
	    connection.connect();
	    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	    StringBuilder sbStr = new StringBuilder();
	    String line;
	    while ((line = bufferedReader.readLine()) != null) {
	      sbStr.append(line);
	    }
	    bufferedReader.close();
	    connection.disconnect();
	    return new String(sbStr.toString().getBytes(), "utf-8");
	}
	/**
	 * 测试方法.
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// 密码
		String password = "123456";
		// 密钥库
		String keyStorePath = "tomcat.keystore";
		// 信任库
		String trustStorePath = "tomcat.keystore";
		// 本地起的https服务
		String httpsUrl = "https://cx.qtopay.cn/PosMerchant/ZfForeignPort?wsdl";
		// 传输文本
		String xmlStr = "";
		HttpsPost
				.initHttpsURLConnection(password, keyStorePath, trustStorePath);
		// 发起请求
		System.out.println(HttpsPost.post(httpsUrl,"<?xml version=\"1.0\" encoding=\"UTF-8\"?><soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"><soapenv:Body><for:addTblForeignSett xmlns:for=\"http://foreign.zf/\"><arg0><merCode>100584048990001</merCode><merName>测试1</merName><merSettAcctName>张三</merSettAcctName><merSettAcct>6225000000000063</merSettAcct><settAcctType>11</settAcctType><merSettAmt>1.00</merSettAmt><userName>ceshidaili</userName><TState>0</TState><tranType>01</tranType><batchNo>LF170703163510000001</batchNo><orderId>LF170703163510000001</orderId><idCardNo>350322199109080557</idCardNo><merCbCode>103584099993</merCbCode><merCbName>中国农业银行</merCbName><md5Date>969F59C7D4F19160A283126F50589E03</md5Date></arg0></for:addTblForeignSett></soapenv:Body></soapenv:Envelope>"));
	}
}
