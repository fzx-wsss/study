package com.wsss.agent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.security.ProtectionDomain;

public class MyAgent implements ClassFileTransformer {

	/**
	 * 该方法在main方法之前运行，与main方法运行在同一个JVM中 并被同一个System ClassLoader装载
	 * 被统一的安全策略(security policy)和上下文(context)管理
	 *
	 * @param agentOps
	 * @param inst
	 * @author SHANHY
	 * @create 2016年3月30日
	 */
	public static void premain(String agentOps, Instrumentation inst) {
		System.out.println("=========premain方法执行========");
		inst.addTransformer(new MyAgent(), true);
		try {
			inst.retransformClasses(MyAgent.class);
		} catch (UnmodifiableClassException e) {
			e.printStackTrace();
		}
		System.out.println(agentOps);
	}

	public static void agentmain(String agentArgs, Instrumentation inst) throws UnmodifiableClassException {
		System.out.println("Agent Main Done");
	}

	/**
	 * 如果不存在 premain(String agentOps, Instrumentation inst) 则会执行 premain(String
	 * agentOps)
	 *
	 * @param agentOps
	 * @author SHANHY
	 * @create 2016年3月30日
	 */
	public static void premain(String agentOps) {
		System.out.println("=========premain方法执行2========");
		System.out.println(agentOps);
	}

	@Override
	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
			ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
		System.out.println("transform");
		System.out.println(className);
		System.out.println(classBeingRedefined);
		return classfileBuffer;
	}
}