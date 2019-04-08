package com.wsss.basic.util.ftp;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class SFTPUtils {

	/**
	 * 初始化日志引擎
	 */

	/** Sftp */
	ChannelSftp sftp = null;
	Session session = null;
	/** 主机 */
	private String host = "";
	/** 端口 */
	private int port = 0;
	/** 用户名 */
	private String username = "";
	/** 密码 */
	private String password = "";
	
	public static void main(String[] args) throws Exception {
		SFTPUtils util = new SFTPUtils("121.42.160.2", 22, "root", "WOSHISHENfans123");
		try {
			util.connect();
			ByteArrayInputStream bis = new ByteArrayInputStream("123".getBytes());
			util.upload("/www/w w w", bis,"268.txt");
		}finally {
			util.disconnect();
		}
		
		
	}
	/**
	 * 构造函数
	 * 
	 * @param host
	 *            主机
	 * @param port
	 *            端口
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * 
	 */
	public SFTPUtils(String host, int port, String username, String password) {

		this.host = host;
		this.port = port;
		this.username = username;
		this.password = password;
	}

	/**
	 * 连接sftp服务器
	 * 
	 * @throws Exception
	 */
	public void connect() throws Exception {

		JSch jsch = new JSch();
		this.session = jsch.getSession(this.username, this.host,
				this.port);
		System.out.println(SFTPUtils.class + "Session created.");

		session.setPassword(password);
		Properties sshConfig = new Properties();
		sshConfig.put("StrictHostKeyChecking", "no");
		session.setConfig(sshConfig);
		session.connect(200);
		System.out.println(SFTPUtils.class + " Session connected.");

		System.out.println(SFTPUtils.class + " Opening Channel.");
		Channel channel = session.openChannel("sftp");
		channel.connect();
		this.sftp = (ChannelSftp) channel;
		System.out.println(SFTPUtils.class + " Connected to " + this.host + ".");
	}

	/**
	 * Disconnect with server
	 * 
	 * @throws Exception
	 */
	public void disconnect() throws Exception {
		if (this.sftp != null && this.sftp.isConnected()) {
			this.sftp.disconnect();
		}
		if(this.session != null && this.session.isConnected()) {
			this.session.disconnect();
		}
	}

	/**
	 * 上传单个文件
	 * 
	 * @param directory
	 *            上传的目录
	 * @param uploadFile
	 *            要上传的文件
	 * 
	 * @throws Exception
	 */
	public void upload(String directory, String uploadFile) throws Exception {
		try {
			this.sftp.cd(directory);
		}catch(Exception e) {
			this.sftp.mkdir(directory);
			this.sftp.cd(directory);
		}
		File file = new File(uploadFile);
		this.sftp.put(new FileInputStream(file), file.getName());
	}
	
	public SFTPUtils upload(String directory, InputStream is, String fileName) throws Exception {
		try {
			this.sftp.cd(directory);
		}catch(Exception e) {
			this.sftp.mkdir(directory);
			this.sftp.cd(directory);
		}
		this.sftp.cd(directory);
		this.sftp.put(is, fileName);
		return this;
	}

	/**
	 * 上传目录下全部文件
	 * 
	 * @param directory
	 *            上传的目录
	 * 
	 * @throws Exception
	 */
	public void uploadByDirectory(String directory) throws Exception {

		String uploadFile = "";
		List<String> uploadFileList = this.listFiles(directory);
		Iterator<String> it = uploadFileList.iterator();

		while (it.hasNext()) {
			uploadFile = it.next().toString();
			this.upload(directory, uploadFile);
		}
	}

	/**
	 * 下载单个文件
	 * 
	 * @param directory
	 *            下载目录
	 * @param downloadFile
	 *            下载的文件
	 * @param saveDirectory
	 *            存在本地的路径
	 * 
	 * @throws Exception
	 */
	public void download(String directory, String downloadFile,
			String saveDirectory) throws Exception {
		String saveFile = saveDirectory + "//" + downloadFile;

		this.sftp.cd(directory);
		File file = new File(saveFile);
		this.sftp.get(downloadFile, new FileOutputStream(file));
	}

	/**
	 * 下载目录下全部文件
	 * 
	 * @param directory
	 *            下载目录
	 * 
	 * @param saveDirectory
	 *            存在本地的路径
	 * 
	 * @throws Exception
	 */
	public void downloadByDirectory(String directory, String saveDirectory)
			throws Exception {
		String downloadFile = "";
		List<String> downloadFileList = this.listFiles(directory);
		Iterator<String> it = downloadFileList.iterator();

		while (it.hasNext()) {
			downloadFile = it.next().toString();
			if (downloadFile.toString().indexOf(".") < 0) {
				continue;
			}
			this.download(directory, downloadFile, saveDirectory);
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param directory
	 *            要删除文件所在目录
	 * @param deleteFile
	 *            要删除的文件
	 * 
	 * @throws Exception
	 */
	public void delete(String directory, String deleteFile) throws Exception {
		this.sftp.cd(directory);
		this.sftp.rm(deleteFile);
	}

	/**
	 * 列出目录下的文件
	 * 
	 * @param directory
	 *            要列出的目录
	 * 
	 * @return list 文件名列表
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<String> listFiles(String directory) throws Exception {

		Vector fileList;
		List<String> fileNameList = new ArrayList<String>();

		fileList = this.sftp.ls(directory);
		Iterator it = fileList.iterator();

		while (it.hasNext()) {
			String fileName = ((LsEntry) it.next()).getFilename();
			if (".".equals(fileName) || "..".equals(fileName)) {
				continue;
			}
			fileNameList.add(fileName);

		}

		return fileNameList;
	}

	/**
	 * 更改文件名
	 * 
	 * @param directory
	 *            文件所在目录
	 * @param oldFileNm
	 *            原文件名
	 * @param newFileNm
	 *            新文件名
	 * 
	 * @throws Exception
	 */
	public void rename(String directory, String oldFileNm, String newFileNm)
			throws Exception {
		this.sftp.cd(directory);
		this.sftp.rename(oldFileNm, newFileNm);
	}

	public void cd(String directory) throws Exception {
		this.sftp.cd(directory);
	}

	public InputStream get(String directory) throws Exception {
		InputStream streatm = this.sftp.get(directory);
		return streatm;
	}

}