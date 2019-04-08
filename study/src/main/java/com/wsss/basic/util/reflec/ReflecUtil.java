package com.wsss.basic.util.reflec;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
/**
 * 
 * @author wsss
 *
 * @param <T>
 */
public class ReflecUtil<T> {
	public static void main(String[] args) {
		
	}
	/**
     * 根据类名反射创建对象
     * @param name 类名
     * @return 对象
     * @throws Exception
     */
    public static Object getInstance(String name) throws Exception {
        Class<?> cls = Class.forName(name);
        return cls.newInstance();
    }
     
    /**
     * 反射方法，打印对象的属性，方法，构造器属性
     * @param obj 被反射对象
     */
    public static void reflect(Object obj) {
        Class<?> cls = obj.getClass();
        System.out.println("************构  造  器************");
        Constructor<?>[] constructors = cls.getConstructors();
        for (Constructor<?> constructor : constructors) {
            System.out.println("构造器名称:" + constructor.getName() + "\t"+ "    "
                    + "构造器参数类型:"
                    + Arrays.toString(constructor.getParameterTypes()));
        }
        System.out.println("************属     性************");
        Field[] fields = cls.getDeclaredFields();
        // cls.getFields() 该方法只能访问共有的属性
        // cls.getDeclaredFields()  可以访问私有属性
        for (Field field : fields) {
            System.out.println("属性名称:" + field.getName() + "\t"+ "属性类型:"
                    + field.getType()+"\t");
        }
        System.out.println("************方   法************");
        Method[] methods = cls.getMethods();
        for (Method method : methods) {
            System.out.println("方法名:" + method.getName() + "\t" + "方法返回类型："
                    + method.getReturnType() + "\t"+ "方法参数类型:"
                    + Arrays.toString(method.getParameterTypes()));
        }
    }
     
    /**
     * 
     * @param obj 访问对象
     * @param filedname  对象的属性
     * @return 返回对象的属性值
     * @throws Exception
     */
    public static Object getFieldValue(Object obj,String filedname) throws Exception{
        //反射出类型
        Class<?> cls = obj.getClass();
        //反射出类型字段
        Field field = cls.getDeclaredField(filedname);
        //获取属性时，压制Java对访问修饰符的检查 
        field.setAccessible(true);
        //在对象obj上读取field属性的值
        Object val = field.get(obj);
        return val;
    }
     
    /**
     * 反射调用对象的方法
     * @param obj 对象 
     * @param methodName  方法名称 
     * @param paramType 参数类型    new Class[]{int.class,double.class}
     * @param params 参数值     new Object[]{2,3.5}
     * @return
     * @throws Exception 
     */
    public static Object readObjMethod(Object obj,String methodName,Class<?>[] paramTypes,Object[] params) throws  Exception{
        //发现类型
        Class<?> cls = obj.getClass();
        //发现方法
        Method method = cls.getDeclaredMethod(methodName, paramTypes);
        //访问方法时,压制Java对访问修饰符的检查
        method.setAccessible(true);
        Object val = method.invoke(obj, params);
        return val;
    }
    
    public static Object parseMapToObject(Map<String,Object> map,Class cls) throws Exception {
    	Object obj = cls.newInstance();
    	
    	for(Entry<String, Object> entry: map.entrySet()) {
    		String methodName = getMethodName(entry.getKey());
    		PropertyDescriptor pd = null;
    		try {
    			pd = new PropertyDescriptor(entry.getKey(),cls);
    		}catch(Exception e) {
    			System.out.println("未找到该属性的set方法：" + entry.getKey());
    			continue;
    		}
    		System.out.println(entry.getKey());
    		Method method = pd.getWriteMethod();
    		method.invoke(obj, entry.getValue());
    	}
    	return obj;
    }
    
    /**
     * 将对象中的所有属性转化到map中
     * @param obj 要转化的对象
     * @param fromParents 是否转化父类中的属性
     * @return
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static Map parseObjectToMap(Object obj,boolean fromParents) throws IllegalArgumentException, IllegalAccessException {
    	Map<String,Object> map = new HashMap<>();
    	Class<?> cls = obj.getClass();
    	Field[] fields = cls.getDeclaredFields();
    	for(Field f:fields) {
    		f.setAccessible(true);
            //在对象obj上读取field属性的值
            Object val = f.get(obj);
            if(null == val) {
            	continue;
            }
            map.put(f.getName(), val);
    	}
    	
    	//不转化父类的属性
    	if(!fromParents) {
    		return map;
    	}
    	
    	//获取父类
    	Class<?> parents = cls.getSuperclass();
		while(!Object.class.equals(parents)) {
			Field[] parentFields = parents.getDeclaredFields();
			for(Field f:parentFields) {
				if(map.containsKey(f.getName())) {
					continue;
				}
	    		f.setAccessible(true);
	            //在对象obj上读取field属性的值
	            Object val = f.get(obj);
	            if(null == val) {
	            	continue;
	            }
	            map.put(f.getName(), val);
	    	}
			parents = parents.getSuperclass();
		}
    	
    	return map;
    }
    
    private static String getMethodName(String fieldName) {
    	StringBuilder sb = new StringBuilder();
    	sb.append("set");
    	sb.append(fieldName.substring(0, 1).toUpperCase());
    	sb.append(fieldName.substring(1));
    	return sb.toString();
    	//fieldName = fieldName.substring(0, 1);
    }
}
