package com.wsss.basic.generic.get.type;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 运行时获取泛型类型的实际类型
 * @author hasee
 *
 */
public class Main {
	public static void main(String[] args) {
		Book<String> book = new Book<String>();
		System.out.println(book.getClass().getTypeParameters()[0].getBounds()[0]);
		//获取父类的类型
		Type superClass = book.getClass().getGenericSuperclass();
		//获取所有接口的类型
		Type[] interfaces = Book.class.getGenericInterfaces();
		for(Type type : interfaces) {
			if(type instanceof ParameterizedType){ //如果该泛型类型是参数化类型
				System.out.println(type);
				System.out.println(((ParameterizedType) type).getOwnerType());
				System.out.println(((ParameterizedType) type).getRawType());
	              Type[] parameterizedType = ((ParameterizedType)type).getActualTypeArguments();//获取泛型类型的实际类型参数集
	              Class<?> entityClass = (Class<?>) parameterizedType[0]; //取出第一个(下标为0)参数的值
	              System.out.println(entityClass.getName());
			}
		}
		
		Type[] parameterizedType = ((ParameterizedType)superClass).getActualTypeArguments();//获取泛型类型的实际类型参数集
        Class<?> entityClass = (Class<?>) parameterizedType[0]; //取出第一个(下标为0)参数的值
		System.out.println(entityClass);
		
	}
}
