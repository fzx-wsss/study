
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Random;
import java.util.TreeMap;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unionpay.commons.cache.util.CacheUtils;
import com.unionpay.dpay.core.remit.unionpay290004.C;
import com.unionpay.dpay.exception.BusinessException;
import com.wsss.basic.util.json.JsonUtils;

/**
 * Title:民生银行公共帮助类 Description: Copyright: Copyright (c)2015 Company: pay
 * 
 * @author hanlin.gao
 */
public class UnionpayUtils {
	private static Logger logger = LoggerFactory.getLogger(UnionpayUtils.class);
	/** 回车换行符 */
	private static final String LF = "\r\n";
	/** 字段分隔符 , */
	private static final String SEPARATOR_BAR = ",";
	/** 金额转换成字符串,小数点后两位 */
	private static final DecimalFormat DFOR = new DecimalFormat("0.00");
	private static final Random ra = new Random();
	private static final ResponseHandler<String> stringResponseHandler;

	static {
		stringResponseHandler = new ResponseHandler<String>() {
			@Override
			public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
				int statusCode = response.getStatusLine().getStatusCode();
				if (statusCode >= 200 && statusCode < 300) {
					HttpEntity entity = response.getEntity();
					return entity != null ? EntityUtils.toString(entity) : null;
				} else {
					throw new ClientProtocolException("Unexpected response status: " + statusCode);
				}
			}
		};
	}

	/**
	 * 产生编号 27位,流水号
	 * 
	 * @Description 一句话描述方法用法
	 * @return
	 * @see 需要参考的类或方法
	 */
	public static String genRequestSn() {

		String date = "PAY" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");// 3+14
																					// =
																					// 17
		StringBuilder codeBuilder = new StringBuilder(date);
		// 从redis中获取序号,一分钟后过期
		Long codeSeq = CacheUtils.incr(
				StringUtils.concatToSB("CodeSEQ.Payinterface.unionpay290004.", date.substring(3, 15)).toString(), 60);
		codeBuilder.append(String.format("%07d", codeSeq));
		codeBuilder.append(String.format("%03d", ra.nextInt(1000)));
		return codeBuilder.toString();

	}

	/**
	 * 产生编号 4位,批次号
	 * 
	 * @Description 一句话描述方法用法
	 * @return
	 * @see 需要参考的类或方法
	 */
	public static String genBatchCode() {

		String date = DateFormatUtils.format(new Date(), "yyyyMMdd");
		// 从redis中获取序号,一天后过期
		Long codeSeq = CacheUtils
				.incr(StringUtils.concatToSB("CodeSEQ.Payinterface.unionpay290004.batchCode.", date).toString(), 86400);
		return String.format("%04d", codeSeq);

	}

	public static String send(Properties prop, String message, String uriName) throws IOException {
		Map<String, String> submitMap = JsonUtils.toObject(message, HashMap.class);
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		for (Entry<String, String> parms : submitMap.entrySet()) {
			nameValuePairs.add(new BasicNameValuePair(parms.getKey(), parms.getValue()));
		}

		String proxyip = prop.getProperty(C.PROXY_IP);
		int proxyport = Integer.valueOf(prop.getProperty(C.PROXY_PORT));
		HttpHost proxy = new HttpHost(proxyip, proxyport);
		RequestConfig requestConfig = RequestConfig.custom().setProxy(proxy).setConnectionRequestTimeout(20000)
				.setConnectTimeout(20000).setSocketTimeout(20000).build();
		String response = HttpClientUtils.send(Method.POST, prop.getProperty(uriName), null, null, nameValuePairs, true,
				"UTF-8", stringResponseHandler, null, requestConfig);

		return response;
	}

	/**
	 * 功能：后台交易提交请求报文并接收同步应答报文<br>
	 * 
	 * @param reqData
	 *            请求报文<br>
	 * @param rspData
	 *            应答报文<br>
	 * @param reqUrl
	 *            请求地址<br>
	 * @param encoding<br>
	 * @return 应答http 200返回true ,其他false<br>
	 * @throws Exception
	 */
	public static String send(Properties prop, Map<String, String> reqData, String uriName) throws Exception {

		String proxyip = prop.getProperty(C.PROXY_IP);
		String proxyport = prop.getProperty(C.PROXY_PORT);
		boolean useProxy = Boolean.parseBoolean(prop.getProperty(C.USE_PROXY, "true"));
		HttpClient hc = new HttpClient(prop.getProperty(uriName), 30000, 30000, useProxy, proxyip, proxyport);// 连接超时时间，读超时时间（可自行判断，修改）
		int status = hc.send(reqData, "UTF-8");
		if (200 != status) throw new BusinessException("发送异常，status:" + status);
		String resultString = hc.getResult();
		return resultString;
	}

	public static String send(Properties prop, Map<String, String> reqData, String uriName, boolean isUseProxy)
			throws Exception {

		String proxyip = prop.getProperty(C.PROXY_IP);
		String proxyport = prop.getProperty(C.PROXY_PORT);
		HttpClient hc = new HttpClient(prop.getProperty(uriName), 30000, 30000, isUseProxy, proxyip, proxyport);// 连接超时时间，读超时时间（可自行判断，修改）
		int status = hc.send(reqData, "UTF-8");
		if (200 != status) throw new BusinessException("发送异常，status:" + status);
		String resultString = hc.getResult();
		return resultString;
	}

	public static byte[] toHex(byte[] bytes) {
		StringBuilder sha1StrBuff = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			if (Integer.toHexString(0xFF & bytes[i]).length() == 1) {
				sha1StrBuff.append("0").append(Integer.toHexString(0xFF & bytes[i]));
			} else {
				sha1StrBuff.append(Integer.toHexString(0xFF & bytes[i]));
			}
		}
		return sha1StrBuff.toString().getBytes();
	}

	public static String coverMap2String(Map<String, String> data) {
		filter(data);
		TreeMap<String, String> tree = new TreeMap<String, String>();
		Iterator<Map.Entry<String, String>> it = data.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> en = (Map.Entry<String, String>) it.next();
			if ("signature".equals(((String) en.getKey()).trim())) {
				continue;
			}
			tree.put(en.getKey(), en.getValue());
		}
		it = tree.entrySet().iterator();
		StringBuffer sf = new StringBuffer();
		while (it.hasNext()) {
			Map.Entry<String, String> en = (Map.Entry) it.next();
			sf.append(new StringBuilder().append((String) en.getKey()).append("=").append((String) en.getValue())
					.append("&").toString());
		}

		return sf.substring(0, sf.length() - 1);
	}

	public static Map<String, String> filter(Map<String, String> data) {
		Iterator<Entry<String, String>> it = data.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> en = it.next();
			if (null == en.getValue() || StringUtils.isEmpty(en.getValue())) {
				it.remove();
			}
		}
		return data;
	}

	public static Map<String, String> parse(String msg) {
		Map<String, String> map = new HashMap<String, String>();
		if (-1 == msg.indexOf("&")) return null;
		String[] fileds = msg.split("&");
		if (null == fileds || fileds.length == 0) return null;

		for (String s : fileds) {
			int index = s.indexOf("=");
			String key = s.substring(0, index);
			String value = s.substring(index + 1, s.length());
			map.put(key, value);
		}

		return map;

	}

	/**
	 * 压缩.
	 * 
	 * @param inputByte
	 *            需要解压缩的byte[]数组
	 * @return 压缩后的数据
	 * @throws IOException
	 */
	public static byte[] deflater(final byte[] inputByte) throws IOException {
		int compressedDataLength = 0;
		Deflater compresser = new Deflater();
		compresser.setInput(inputByte);
		compresser.finish();
		ByteArrayOutputStream o = new ByteArrayOutputStream(inputByte.length);
		byte[] result = new byte[1024];
		try {
			while (!compresser.finished()) {
				compressedDataLength = compresser.deflate(result);
				o.write(result, 0, compressedDataLength);
			}
		} finally {
			o.close();
		}
		compresser.end();
		return o.toByteArray();
	}

	/**
	 * 解压缩.
	 * 
	 * @param inputByte
	 *            byte[]数组类型的数据
	 * @return 解压缩后的数据
	 * @throws IOException
	 */
	public static byte[] inflater(final byte[] inputByte) throws IOException {
		int compressedDataLength = 0;
		Inflater compresser = new Inflater(false);
		compresser.setInput(inputByte, 0, inputByte.length);
		ByteArrayOutputStream o = new ByteArrayOutputStream(inputByte.length);
		byte[] result = new byte[1024];
		try {
			while (!compresser.finished()) {
				compressedDataLength = compresser.inflate(result);
				if (compressedDataLength == 0) {
					break;
				}
				o.write(result, 0, compressedDataLength);
			}
		} catch (Exception ex) {
			System.err.println("Data format error!\n");
			ex.printStackTrace();
		} finally {
			o.close();
		}
		compresser.end();
		return o.toByteArray();
	}

}
