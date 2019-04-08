package com.wsss.basic.socket.study3;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Ccb360101 {
	
	public static void main(String[] args) throws Exception {
		//Thread.sleep(10000);
		String index = "1608031033120002";
		//String msg = "<?xml version=\"1.0\" encoding=\"GB2312\"?>" +"<TX><REQUEST_SN>1607251945590000</REQUEST_SN><CUST_ID>JX36000009161789701</CUST_ID><USER_ID>WLPT01</USER_ID><PASSWORD>1234qwer</PASSWORD><TX_CODE>6W8040</TX_CODE><LANGUAGE>CN</LANGUAGE><TX_INFO><AMOUNT>0.01</AMOUNT><COUNT>1</COUNT><CHK_RECVNAME>1</CHK_RECVNAME><FILE_CTX>003|乐富支付有限公司（客户备付金）|36050153017200000018|360000000|625012548990|张三0|收款账号开发一级分行行号|工商银行珠市口支行|100021231455||0|0.01|01|POS打款|||</FILE_CTX></TX_INFO><SIGN_INFO/><SIGNCERT/></TX>";
		//String msg = "<?xml version=\"1.0\" encoding=\"GB2312\"?><TX><REQUEST_SN>1607251945490000</REQUEST_SN><CUST_ID>JX36000009161789701</CUST_ID><USER_ID>WLPT01</USER_ID><PASSWORD>1234qwer</PASSWORD><TX_CODE>6W8040</TX_CODE><LANGUAGE>CN</LANGUAGE><TX_INFO><AMOUNT>0.01</AMOUNT><COUNT>1</COUNT><CHK_RECVNAME>1</CHK_RECVNAME><FILE_CTX>002|江西XXXX公司|36050153017200000018|360000000|2020189988800088888|李XX| | | | |1|0.01 |01|转建行个人账户示例| | |</FILE_CTX></TX_INFO><SIGN_INFO/><SIGNCERT/></TX>";
		//String msg = "002|江西XXXX公司|36001050600050888888|360000000|2020189988800088888|李XX| | | | |1|0.01 |01|转建行个人账户示例| | |";
		//String msg = "<?xml version=\"1.0\" encoding=\"GB2312\"?>"+"<TX><REQUEST_SN>"+index+"</REQUEST_SN><CUST_ID>JX36000009161789701</CUST_ID><USER_ID>WLPT01</USER_ID><PASSWORD>1234qwer</PASSWORD><TX_CODE>6W8040</TX_CODE><LANGUAGE>CN</LANGUAGE><TX_INFO><AMOUNT>0.01</AMOUNT><COUNT>1</COUNT><CHK_RECVNAME>1</CHK_RECVNAME><FILE_CTX>0|乐富支付有限公司|36050153017200000018|fukuan fenhang hanghao|6217000010051641390|樊仲轩|收款账号开发一级分行行号|中国建设银行|100021231455||1|0.01|01|POS打款|||</FILE_CTX></TX_INFO><SIGN_INFO/><SIGNCERT/></TX>";
		
		//String msg = "<?xml version=\"1.0\" encoding=\"GB2312\"?>"+"<TX><REQUEST_SN>1608031100260000</REQUEST_SN><CUST_ID>JX36000009161789701</CUST_ID><USER_ID>WLPT01</USER_ID><PASSWORD>1234qwer</PASSWORD><TX_CODE>6W0800</TX_CODE><LANGUAGE>CN</LANGUAGE><TX_INFO><ACC_NO></ACC_NO><START_DATE></START_DATE><END_DATE></END_DATE><CREDIT_NO></CREDIT_NO><PAGE>1</PAGE><BATCH_NAME>"+index+"</BATCH_NAME></TX_INFO></TX>";
		
		//单笔
		String msg = "<?xml version=\"1.0\" encoding=\"GB2312\"?><TX><REQUEST_SN>1608031522290026</REQUEST_SN><CUST_ID>JX36000009161789701</CUST_ID><USER_ID>WLPT01</USER_ID><PASSWORD>1234qwer</PASSWORD><TX_CODE>6W1303</TX_CODE><LANGUAGE>CN</LANGUAGE><TX_INFO><ACC_NO1>36050153017200000018</ACC_NO1><BILL_CODE>160801085</BILL_CODE><ACC_NO2>6217000010051641390</ACC_NO2><OTHER_NAME>樊仲轩</OTHER_NAME><AMOUNT>0.01</AMOUNT><USEOF_CODE>0000053</USEOF_CODE><FLOW_FLAG>0</FLOW_FLAG><UBANK_NO>105100000017</UBANK_NO></TX_INFO><SIGN_INFO/><SIGNCERT/></TX>";
		//String msg = "<?xml version=\"1.0\" encoding=\"GB2312\"?><TX><REQUEST_SN>1608031703220000</REQUEST_SN><CUST_ID>JX36000009161789701</CUST_ID><USER_ID>WLPT01</USER_ID><PASSWORD>1234qwer</PASSWORD><TX_CODE>6W1303</TX_CODE><LANGUAGE>CN</LANGUAGE><TX_INFO><ACC_NO1>36050153017200000018</ACC_NO1><BILL_CODE>160801085</BILL_CODE><ACC_NO2>6217000120003976768</ACC_NO2><OTHER_NAME>樊仲轩</OTHER_NAME><AMOUNT>0.01</AMOUNT><USEOF_CODE>00000008</USEOF_CODE><FLOW_FLAG>0</FLOW_FLAG><UBANK_NO>105100000017</UBANK_NO></TX_INFO><SIGN_INFO/><SIGNCERT/></TX>";
		//String msg = "<?xml version=\"1.0\" encoding=\"GB2312\"?><TX><REQUEST_SN>1609141025570000</REQUEST_SN><CUST_ID>JX36000009161789701</CUST_ID><USER_ID>WLPT01</USER_ID><PASSWORD>1234qwer</PASSWORD><TX_CODE>6W1303</TX_CODE><LANGUAGE>CN</LANGUAGE><TX_INFO><ACC_NO1>36050153017200000018</ACC_NO1><BILL_CODE>160801085</BILL_CODE><ACC_NO2>6222600910077196549</ACC_NO2><OTHER_NAME>王瑞</OTHER_NAME><AMOUNT>0.01</AMOUNT><USEOF_CODE>0000053</USEOF_CODE><FLOW_FLAG>0</FLOW_FLAG><UBANK_NO>301290000007</UBANK_NO></TX_INFO><SIGN_INFO/><SIGNCERT/></TX>";
		
		//单笔查询
		//String msg = "<?xml version=\"1.0\" encoding=\"GB2312\"?><TX><REQUEST_SN>1608031808540001</REQUEST_SN><CUST_ID>JX36000009161789701</CUST_ID><USER_ID>WLPT01</USER_ID><PASSWORD>1234qwer</PASSWORD><TX_CODE>6W1503</TX_CODE><LANGUAGE>CN</LANGUAGE><TX_INFO><REQUEST_SN1>1608031522290016</REQUEST_SN1></TX_INFO></TX>";
		String ip = "127.0.0.1";
		String port = "12345";
		InetSocketAddress socketAddress = new InetSocketAddress(InetAddress.getByName(ip), Integer.parseInt(port));
		// 发送报文
		byte[] outByte = send(socketAddress, msg.getBytes("GB18030"));
		String result = new String(outByte, "GB18030");
		System.out.println(result);
	}
	
	
	/**
	 * Socket发送报文
	 * @param socketAddress 发送地址
	 * @param data 请求报文数据
	 * @return 响应报文数据
	 * @throws IOException
	 */
	private static byte[] send(InetSocketAddress socketAddress, byte[] data) throws IOException {
		Socket client = null;
		OutputStream os = null;
		InputStream is = null;
		ByteArrayOutputStream baos = null;
		byte[] outByte = null;
		try {
			client = new Socket();
			client.connect(socketAddress, 5 * 60 * 1000);
			client.setSoTimeout(3 * 60 * 1000);
			// 由Socket对象得到输出流，并构造PrintWriter对象
			os = new BufferedOutputStream(client.getOutputStream());
			// 由系统标准输入设备构造BufferedReader对象
			is = new BufferedInputStream(client.getInputStream());

			// 发送报文
			os.write(data);
			os.flush();

			// 接收服务器的响应
			baos = new ByteArrayOutputStream();
			int len = 0;
			byte[] b = new byte[1024];
			while ((len = is.read(b)) != -1) {
				baos.write(b, 0, len);
			}
			outByte = baos.toByteArray();
		} finally {
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (client != null) {
				try {
					client.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		// 返回
		return outByte;
	}
}
