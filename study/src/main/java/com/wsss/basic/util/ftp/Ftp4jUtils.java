//package com.wsss.basic.util.ftp;
//
//import it.sauronsoftware.ftp4j.FTPClient;
//import it.sauronsoftware.ftp4j.FTPException;
//import it.sauronsoftware.ftp4j.FTPFile;
//import it.sauronsoftware.ftp4j.FTPIllegalReplyException;
//import it.sauronsoftware.ftp4j.connectors.HTTPTunnelConnector;
//
//import java.io.File;
//import java.io.IOException;
//
//public class Ftp4jUtils {
//
//
//		/** 10KB的缓存 */
//		private final static int KB10 = 10 * 1024;
//		/** FTP读取数据超时设置(毫秒) 3分钟 */
//		private final static int FTP_DATA_TIMEOUT = 3 * 60 * 1000;
//		/** ftp连接客户端 */
//		private FTPClient ftpClient = new FTPClient();
//
//		public Ftp4jUtils() {
//			// 设置将过程中使用到的命令输出到控制台
//			// this.ftpClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
//		}
//
//		public static void main(String[] args) throws Exception {
//			Ftp4jUtils ftp = new Ftp4jUtils();
//			ftp.connect("121.42.160.2", 21, "ftp123", "123123");
//			ftp.download("/req/eee/333/ImbaMallLog.txt", "C:/Temp/123.txt");
//			ftp.disconnect();
//		}
//
//		/**
//		 * 连接到FTP服务器
//		 * @param hostname 主机名
//		 * @param port 端口
//		 * @param username 用户名
//		 * @param password 密码
//		 * @return 是否连接成功
//		 * @throws IOException
//		 * @throws FTPException
//		 * @throws FTPIllegalReplyException
//		 * @throws IllegalStateException
//		 */
//		public boolean connect(String hostname, int port, String proxyIp, int proxyPort, String username, String password) throws Exception {
//			// 设置FTP读取数据的超时时间
//			// ftpClient.setDataTimeout(FTP_DATA_TIMEOUT);
//			// 设置PassiveMode传输
//			ftpClient.setPassive(true);;
//			// 设置编码格式GBK
//			ftpClient.setCharset("GBK");
//			// Proxy proxy = new Proxy(Type.HTTP, new InetSocketAddress(proxyIp, proxyPort));
//			HTTPTunnelConnector http = new HTTPTunnelConnector(proxyIp, proxyPort);
//			ftpClient.setConnector(http);
//			ftpClient.connect(hostname, port);
//			ftpClient.login(username, password);
//			return true;
//		}
//
//		public boolean connect(String hostname, int port, String username, String password) throws Exception {
//			// 设置FTP读取数据的超时时间
//			// ftpClient.setDataTimeout(FTP_DATA_TIMEOUT);
//			// 设置PassiveMode传输
//			ftpClient.setPassive(true);;
//			// 设置编码格式GBK
//			ftpClient.setCharset("GBK");
//			ftpClient.connect(hostname, port);
//			ftpClient.login(username, password);
//			return true;
//		}
//
//		/**
//		 * 从FTP服务器上下载文件
//		 * @param remote 远程文件路径
//		 * @param local 本地文件路径
//		 * @return 下载的状态(true-下载成功 false-下载失败)
//		 * @throws IOException
//		 */
//		public void download(String remote, String local) throws Exception {
//			// 检查远程文件是否存在
//			FTPFile[] files = ftpClient.list(remote);
//			if (files.length != 1) {
//				// 远程文件不存在
//				throw new IOException("remote file not found");
//			}
//			// 检查保存目录是否存在，不存在则创建
//			File localPath = new File(local.substring(0, local.lastIndexOf("/")));
//			if (!localPath.exists()) {
//				if (!localPath.mkdirs()) {
//					// 远程文件夹创建失败
//					String errorMsg = "remote file mk fail";
//					throw new IOException(errorMsg);
//				}
//			}
//
//			ftpClient.download(remote, new File(local));
//		}
//
//		/**
//		 * 上传文件到FTP服务器
//		 * @param localFile 本地文件
//		 * @param remote 远程文件路径.
//		 *            使用/home/directory1/subdirectory/file.ext或是http://www.guihua.org /subdirectory/file.ext.
//		 *            按照Linux上的路径指定方式，支持多级目录嵌套，支持递归创建不存在的目录结构.
//		 * @return 上传结果
//		 * @throws IOException
//		 */
//		public void upload(File localFile, String remote) throws Exception {
//			// 对远程目录的处理
//			createDirecroty(remote, ftpClient);
//			System.out.println("远程:"+remote);
//			uploadFile(localFile, ftpClient);
//		}
//
//		/**
//		 * 判断远程文件是否存在
//		 * @param remote 远程文件路径
//		 * @return 是否存在（true-存在 false-不存在）
//		 * @throws IOException
//		 */
//		public boolean exists(String remote) throws Exception {
//			// 检查远程文件是否存在
//			FTPFile[] files = ftpClient.list(remote);
//			if (null == files || files.length < 1) {
//				// 远程文件不存在
//				return false;
//			}
//			return true;
//		}
//
//		/**
//		 * 断开与远程服务器的连接
//		 * @throws FTPException
//		 * @throws FTPIllegalReplyException
//		 * @throws Exception
//		 * @throws IOException
//		 */
//		public void disconnect() throws Exception {
//			if (ftpClient.isConnected()) {
//				try {
//					// 退出登录并断开
//					ftpClient.disconnect(true);
//
//				} catch (IOException ioe) {
//					ioe.printStackTrace();
//				} finally {
//
//				}
//			}
//		}
//
//		/**
//		 * 递归创建远程服务器目录
//		 * @param remote 远程服务器文件绝对路径
//		 * @param ftpClient FTPClient对象
//		 * @return 目录创建是否成功
//		 * @throws IOException
//		 */
//		private void createDirecroty(String remote, FTPClient ftpClient) throws Exception {
//			if (!"/".equalsIgnoreCase(remote)) {
//				//ftpClient.changeDirectory(directory);
//				String[] dirs = remote.split("/",-1);
//				StringBuffer sb = new StringBuffer();
//
//				for(String dir : dirs) {
//					if("".equals(dir)) continue;
//					sb.append("/");
//					sb.append(dir);
//					try {
//						if(!exists(sb.toString())) ftpClient.createDirectory(sb.toString());
//					}catch(Exception e) {
//
//					}
//					ftpClient.changeDirectory(sb.toString());
//				}// while
//			}
//		}
//
//		/**
//		 * 上传文件到服务器
//		 * @param localFile 本地文件File句柄，绝对路径
//		 * @param ftpClient FTPClient引用
//		 * @return 是否上传成功
//		 * @throws IOException
//		 */
//		private void uploadFile(File localFile, FTPClient ftpClient) throws Exception {
//			ftpClient.upload(localFile);
//		}
//
//}
