package com.wsss.basic.util.json;


import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.PropertyNamingStrategy.PropertyNamingStrategyBase;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.type.JavaType;


/**
 * json解析工具类
 * 
 * @author jhe
 * 
 */
public class JsonUtil {
	
	/**
	 * object对象转换为 json格式字符串
	 * 
	 * @param object
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings( { "unchecked" })
	public static String object2Json(Object object) throws IOException {
		if (null == object) {
			return "";
		}
		ObjectMapper mapper = JacksonMapper.getInstance();
		
		// 字段和值都加引号  
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true); 
        
		//数字加引号
		mapper.configure(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS,true);
		mapper.configure(JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS, true);
	        
		
		mapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>()  
        {  
            public void serialize(  
                    Object value,  
                    JsonGenerator jg,  
                    SerializerProvider sp) throws IOException, JsonProcessingException  
            {  
                jg.writeString("");  
            }
        });  
		return mapper.writeValueAsString(object);
	}

	/**
	 * json格式字符串转换为object对象
	 * 
	 * @param json
	 * @param cls
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings( { "unchecked", "rawtypes" })
	public static <T> T json2Object(String json, Class<T> cls) throws IOException {
		if (null == json || "".equals(json)) {
			return null;
		}
		ObjectMapper mapper = JacksonMapper.getInstance();
		
		mapper.configure(DeserializationConfig.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		return (T) mapper.readValue(json, cls);
	}
	
	public static List<Object> json2ObjectList(String json, Class cls) throws JsonParseException, JsonMappingException, IOException{  
        /*JSONArray jsonobj = JSONArray.fromObject(json);  
        return (List<Object>) JSONArray.toList(jsonobj,cls);  */
		if (null == json || "".equals(json)) {
			return null;
		}
		ObjectMapper mapper = JacksonMapper.getInstance();
		JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, cls);
		return (List<Object>)mapper.readValue(json, javaType); 
	        
    }
	
	
	public static String object2Json1(Object object) throws IOException {
		if (null == object) {
			return "";
		}
		ObjectMapper mapper = JacksonMapper.getInstance();
		
		mapper.setPropertyNamingStrategy(new A());
		
		StringWriter sw = new StringWriter();
		JsonGenerator gen = new JsonFactory().createJsonGenerator(sw);
		mapper.writeValue(gen, object);
		gen.close();
		String retVal = sw.toString();
		if (retVal == null) {
			return retVal;
		}
		return retVal.replaceAll("null", "\"\"");
	}
	
	static class A extends PropertyNamingStrategyBase {
		
		public String translate(String name) {
			return name.toLowerCase();
		}
		
	}
	
	
	/**
	 * object对象转换为 json格式字符串 去掉空值属性
	 * 
	 * @param object
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings( { "unchecked" })
	public static String object2JsonExcludeNullProperty(Object object) throws IOException {

		ObjectMapper mapper = JacksonMapper.getInstance();
		
		//忽略值为null的属性
		mapper.setSerializationInclusion(Inclusion.NON_NULL);  
		
		//数字加引号
		mapper.configure(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS,false);
		return mapper.writeValueAsString(object);
	}
	
}
