package com.wsss.basic.util.ftp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Title: FTP帮助类
 * Description:
 * Copyright: Copyright (c)2013
 * Company: pay
 * @author chunyang.wang
 */
public class FTPUtils {
	/** 日志管理 */
	private static Logger log = LoggerFactory.getLogger(FTPUtils.class);
	/** 10KB的缓存 */
	private final static int KB10 = 10 * 1024;
	/** FTP读取数据超时设置(毫秒) 3分钟 */
	private final static int FTP_DATA_TIMEOUT = 3 * 60 * 1000;
	/** ftp连接客户端 */
	private FTPClient ftpClient = new FTPClient();

	public FTPUtils() {
		// 设置将过程中使用到的命令输出到控制台
		// this.ftpClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
	}
	public static void main(String[] args) throws IOException {
		System.out.println(new FTPUtils().connect("121.42.160.2", 21, "root", "WOSHISHENfans123"));
	}

	/**
	 * 连接到FTP服务器
	 * @param hostname 主机名
	 * @param port 端口
	 * @param username 用户名
	 * @param password 密码
	 * @return 是否连接成功
	 * @throws IOException
	 */
	public boolean connect(String hostname, int port, String username, String password) throws IOException {
		// 设置FTP读取数据的超时时间
		ftpClient.setDataTimeout(FTP_DATA_TIMEOUT);
		// 设置PassiveMode传输
		ftpClient.enterLocalPassiveMode();
		// 设置编码格式GBK
		ftpClient.setControlEncoding("GBK");

		ftpClient.connect(hostname, port);
		if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode()) && ftpClient.login(username, password)) {
			return true;
		}
		disconnect();
		return false;
	}

	/**
	 * 从FTP服务器上下载文件
	 * @param remote 远程文件路径
	 * @param local 本地文件路径
	 * @return 下载的状态(true-下载成功 false-下载失败)
	 * @throws IOException
	 */
	public boolean download(String remote, String local) throws IOException {
		// 检查远程文件是否存在
		FTPFile[] files = ftpClient.listFiles(remote);
		if (files.length != 1) {
			// 远程文件不存在
			log.warn("remote file not found");
			throw new IOException("remote file not found");
		}
		// 检查保存目录是否存在，不存在则创建
		File localPath = new File(local.substring(0, local.lastIndexOf("/")));
		if (!localPath.exists()) {
			if (!localPath.mkdirs()) {
				// 远程文件夹创建失败
				String errorMsg = "remote file mk fail";
				log.warn(errorMsg);
				throw new IOException(errorMsg);
			}
		}

		OutputStream out = null;
		InputStream in = null;
		byte[] buff = new byte[KB10];
		int len;
		try {
			out = new BufferedOutputStream(new FileOutputStream(new File(local)));
			in = ftpClient.retrieveFileStream(remote);
			while ((len = in.read(buff)) != -1) {
				out.write(buff, 0, len);
			}
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException ioe) {
					log.info("uploadFile Close InputStream IOException:" + ioe.getMessage(), ioe);
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException ioe) {
					log.info("uploadFile Close OutputStream IOException:" + ioe.getMessage(), ioe);
				}
			}
		}
		return ftpClient.completePendingCommand();
	}

	/**
	 * 上传文件到FTP服务器
	 * @param localFile 本地文件
	 * @param remote 远程文件路径.
	 *            使用/home/directory1/subdirectory/file.ext或是http://www.guihua.org /subdirectory/file.ext.
	 *            按照Linux上的路径指定方式，支持多级目录嵌套，支持递归创建不存在的目录结构.
	 * @return 上传结果
	 * @throws IOException
	 */
	public boolean upload(File localFile, String remote) throws IOException {
		// 对远程目录的处理
		String remoteFileName = remote;
		if (remote.contains("/")) {
			remoteFileName = remote.substring(remote.lastIndexOf("/") + 1);
			// 创建服务器远程目录结构，创建失败直接返回
			createDirecroty(remote, ftpClient);
		}
		return uploadFile(remoteFileName, localFile, ftpClient);
	}

	/**
	 * 获取指定目录下的FTP文件
	 * @param remote 远程目录
	 * @return FTP文件
	 * @throws IOException
	 */
	public FTPFile[] listFiles(String remote) throws IOException {
		return ftpClient.listFiles(remote);
	}

	/**
	 * 判断远程文件是否存在
	 * @param remote 远程文件路径
	 * @return 是否存在（true-存在 false-不存在）
	 * @throws IOException
	 */
	public boolean exists(String remote) throws IOException {
		// 检查远程文件是否存在
		FTPFile[] files = ftpClient.listFiles(remote);
		if (files.length != 1) {
			// 远程文件不存在
			return false;
		}
		return true;
	}

	/**
	 * 断开与远程服务器的连接
	 * @throws IOException
	 */
	public void disconnect() {
		if (ftpClient.isConnected()) {
			try {
				// 退出登录并断开
				ftpClient.logout();
				ftpClient.disconnect();
			} catch (IOException ioe) {
				log.error("Disconnect Ftp IOException:" + ioe.getMessage(), ioe);
			}
		}
	}

	/**
	 * 递归创建远程服务器目录
	 * @param remote 远程服务器文件绝对路径
	 * @param ftpClient FTPClient对象
	 * @return 目录创建是否成功
	 * @throws IOException
	 */
	private void createDirecroty(String remote, FTPClient ftpClient) throws IOException {
		String directory = remote.substring(0, remote.lastIndexOf("/") + 1);
		if (!"/".equalsIgnoreCase(directory) && !ftpClient.changeWorkingDirectory(directory)) {
			// 如果远程目录不存在，则递归创建远程服务器目录
			int start = 0;
			int end = 0;
			if (directory.startsWith("/")) {
				start = 1;
			} else {
				start = 0;
			}
			end = directory.indexOf("/", start);
			while (true) {
				String subDirectory = remote.substring(start, end);
				if (!ftpClient.changeWorkingDirectory(subDirectory)) {
					if (ftpClient.makeDirectory(subDirectory)) {
						ftpClient.changeWorkingDirectory(subDirectory);
					} else {
						// 创建目录失败
						String errorMsg = "Create Direcroty[" + remote + "]Fail";
						log.error(errorMsg);
						throw new IOException(errorMsg);
					}
				}

				start = end + 1;
				end = directory.indexOf("/", start);
				// 检查所有目录是否创建完毕
				if (end <= start) {
					break;
				}
			}// while
		}
	}

	/**
	 * 上传文件到服务器
	 * @param remoteFile 远程文件名，在上传之前已经将服务器工作目录做了改变
	 * @param localFile 本地文件File句柄，绝对路径
	 * @param ftpClient FTPClient引用
	 * @return 是否上传成功
	 * @throws IOException
	 */
	private boolean uploadFile(String remoteFile, File localFile, FTPClient ftpClient) throws IOException {
		InputStream in = null;
		OutputStream out = null;
		byte[] buff = new byte[KB10];
		int len;
		try {
			in = new BufferedInputStream(new FileInputStream(localFile));
			out = ftpClient.storeFileStream(remoteFile);
			while ((len = in.read(buff)) != -1) {
				out.write(buff, 0, len);
			}
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException ioe) {
					log.info("uploadFile Close InputStream IOException:" + ioe.getMessage(), ioe);
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException ioe) {
					log.info("uploadFile Close OutputStream IOException:" + ioe.getMessage(), ioe);
				}
			}
		}
		return ftpClient.completePendingCommand();
	}
}