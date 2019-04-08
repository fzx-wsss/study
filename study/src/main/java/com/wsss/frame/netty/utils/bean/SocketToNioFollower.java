package com.wsss.frame.netty.utils.bean;

/**
 * @Description: 异步发送后续处理接口
 * @see: SocketToNioFollower 此处填写需要参考的类
 * @version 2017年9月14日 下午2:07:22
 * @author zhongxuan.fan
 */
public interface SocketToNioFollower {
	void parseBytes(byte[] bytes);
}
