package com.wsss.basic.util.json;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

/**
 * Json工具类
 * @author rui.wang
 * @since 2013年10月30日
 */
public class JsonUtils {
	private static volatile ObjectMapper mapper = new ObjectMapper();

	private JsonUtils() {};
	public static void main(String[] args) {
		String s = "{\"@version\":\"1\",\"@timestamp\":\"2017-03-17T01:41:05.000Z\",\"source\":\"/opt/tomcat_settle/logs/settle-core.log-2017-03-17\",\"offset\":977452938,\"type\":\"businesstp-settle-core\",\"input_type\":\"log\",\"beat\":{\"name\":\"192.168.101.136\",\"version\":\"5.0.1\"},\"host\":\"LEI-Settle-136\",\"tags\":[\"beats_input_codec_plain_applied\"],\"LogLevel\":\"INFO\",\"Module\":\"com.lefu.settle.api.interfaces.querySettleCardByOwner\",\"KeyWord\":\"8627465767\",\"Customer1\":\"-\",\"Sign\":\"end\",\"Result\":\"success\",\"AmountOfTime\":\"3\",\"Message\":\"8627465767,查询结算卡\"}";
		Map<String,String> map = toObject(s, HashMap.class);
		System.out.println(map);
	}
	public static ObjectMapper getInstance() {
		return mapper;
	}

	public static byte[] toJsonBytes(Object obj) {
		try {
			return mapper.writeValueAsBytes(obj);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static String toJsonString(Object obj) {
		try {
			return mapper.writeValueAsString(obj);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T toObject(String json, Class<T> clazz) {
		if (json == null) return null;
		try {
			return mapper.readValue(json, clazz);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T toObject(String json, TypeReference<T> typeReference) {
		if (json == null) return null;
		try {
			return mapper.readValue(json, typeReference);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T toObject(byte[] json, Class<T> clazz) {
		if (json == null) return null;
		try {
			return mapper.readValue(json, clazz);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T toObject(byte[] json, TypeReference<T> typeReference) {
		if (json == null) return null;
		try {
			return mapper.readValue(json, typeReference);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T toJsonToObject(Object from, Class<T> clazz) {
		return JsonUtils.toObject(JsonUtils.toJsonString(from), clazz);
	}

}
