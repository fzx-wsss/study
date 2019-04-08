

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

public class HttpsTest {
	public static void main(String[] args) throws Exception{
		String res = HttpsTest.post("<?xml version=\"1.0\" encoding=\"UTF-8\"?><soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"><soapenv:Body><for:addTblForeignSett xmlns:for=\"http://foreign.zf/\"><arg0><merCode>100584048990001</merCode><merName>测试1</merName><merSettAcctName>张三</merSettAcctName><merSettAcct>6225000000000063</merSettAcct><settAcctType>11</settAcctType><merSettAmt>1.00</merSettAmt><userName>ceshidaili</userName><TState>0</TState><tranType>01</tranType><batchNo>LF170703163510000001</batchNo><orderId>LF170703163510000001</orderId><idCardNo>350322199109080557</idCardNo><merCbCode>103584099993</merCbCode><merCbName>中国农业银行</merCbName><md5Date>969F59C7D4F19160A283126F50589E03</md5Date></arg0></for:addTblForeignSett></soapenv:Body></soapenv:Envelope>");
		System.out.println(res);
	}
	public static String post(String msg) throws Exception {
		HostnameVerifier hv = new HostnameVerifier() {
	        public boolean verify(String urlHostName, SSLSession session) {
	        	System.out.println("Warning: URL Host: " + urlHostName + " vs. "
	                               + session.getPeerHost());
	            return true;
	        }
		};
		trustAllHttpsCertificates();
		HttpsURLConnection.setDefaultHostnameVerifier(hv);

		String proxyip = "192.168.16.157";
		int proxyport =9535;
		String urlStr ="https://cx.qtopay.cn:8444/PosMerchant/ZfForeignPort?wsdl";

		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyip, proxyport));
		URL url = new URL(urlStr);
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection(proxy);
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");



		PrintWriter out = new PrintWriter(conn.getOutputStream());
		out.print(msg);
		out.flush();

		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));// getInputStream getErrorStream

		String line;
		String result = "";
		while ((line = in.readLine()) != null) {
			result += line;
		}

		out.close();
		in.close();

		return result;
	}
	 private static void trustAllHttpsCertificates() throws Exception {
		 javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];
		 javax.net.ssl.TrustManager tm = new miTM();
		 trustAllCerts[0] = tm;
		 javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext
		 .getInstance("SSL");
		 sc.init(null, trustAllCerts, null);
		 javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc
		 .getSocketFactory());
	 }

	 static class miTM implements javax.net.ssl.TrustManager,
	 javax.net.ssl.X509TrustManager {
	 public java.security.cert.X509Certificate[] getAcceptedIssuers() {
		 return null;
	 }

	 public boolean isServerTrusted(
		 java.security.cert.X509Certificate[] certs) {
		 return true;
	 }

	 public boolean isClientTrusted(
		 java.security.cert.X509Certificate[] certs) {
		 return true;
	 }

	 public void checkServerTrusted(
		 java.security.cert.X509Certificate[] certs, String authType)
		 throws java.security.cert.CertificateException {
		 return;
	 }

	 public void checkClientTrusted(
		 java.security.cert.X509Certificate[] certs, String authType)
		 throws java.security.cert.CertificateException {
		 return;
	 }
	 }
}
