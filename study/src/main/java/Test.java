
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.CodeSource;
import java.security.NoSuchAlgorithmException;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.wsss.basic.util.http.HttpClientUtils;
import com.wsss.basic.util.http.HttpClientUtils.Method;
import com.wsss.basic.util.json.JsonUtils;

/**
 * @Description: 这里用一句话描述这个类的作用
 * @see: CIB520001Utils 此处填写需要参考的类
 * @version 2016年11月18日 下午1:40:50
 * @author zhongxuan.fan
 */
public class Test implements Serializable {
	private static Random random = new Random();

	private static ResponseHandler<String> stringResponseHandler = new ResponseHandler<String>() {
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

	public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
		List<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair("access_token", "3be22a0c02103115ab9cd847a2168871"));
		params.add(new BasicNameValuePair("id", "1"));
		String s = HttpClientUtils.send(Method.GET, "https://oapi.dingtalk.com/department/list", null, null, params,
				true, "UTF-8", stringResponseHandler, null, null);
		DepartmentList list = JsonUtils.toObject(s, DepartmentList.class);
		System.out.println(list);
	}

	public static byte getByte(String s) {
		return hexStringToBytes(s)[0];
	}

	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	public static void listPackage(String packageName, File file) {
		if (!file.exists()) return;
		if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				String next = packageName + "." + f.getName();
				listPackage(next, f);
			}
		}
		if (file.isFile()) {
			System.out.println(packageName);
		}
	}

	public static void listPackage(String packageName, JarFile jarFile) {
		Enumeration<JarEntry> files = jarFile.entries();
		String path = packageName;
		while (files.hasMoreElements()) {
			JarEntry entry = files.nextElement();
			if (entry.getName().startsWith(path) && entry.getName().endsWith(".class")
					&& !entry.getName().contains("$")) {
				String className = entry.getName().replaceAll("/", ".");
				className = className.substring(0, className.length() - 6);
				System.out.println(className);
			}

		}
	}

	public static List<String> toList(String ips) {
		int[][] all = new int[4][];
		String[] temp = ips.split("\\.");
		for (int i = 0; i < temp.length; i++) {
			String[] ip = temp[i].split("-");
			if (ip.length == 1) {
				all[i] = new int[1];
				all[i][0] = Integer.parseInt(ip[0]);
				continue;
			}

			int start = Integer.parseInt(ip[0]);
			int end = Integer.parseInt(ip[1]);
			all[i] = new int[end - start + 1];
			for (int j = start; j <= end; j++) {
				all[i][j - start] = j;
			}
		}

		List<String> res = new LinkedList<>();
		// res.add("");
		for (int[] index : all) {
			if (res.isEmpty()) {
				for (int num : index) {
					res.add(String.valueOf(num));
				}
				continue;
			}
			int len = res.size();
			for (int i = 0; i < len; i++) {
				String s = res.remove(0);
				for (int num : index) {
					res.add(StringUtils.join(new Object[] { s, num }, "."));
				}
			}
		}
		return res;
	}

	public static String where(final Class cls) {
		if (cls == null) throw new IllegalArgumentException("null input: cls");
		URL result = null;
		final String clsAsResource = cls.getName().replace('.', '/').concat(".class");
		final ProtectionDomain pd = cls.getProtectionDomain();
		if (pd != null) {
			final CodeSource cs = pd.getCodeSource();
			if (cs != null) result = cs.getLocation();
			if (result != null) {
				if ("file".equals(result.getProtocol())) {
					try {
						if (result.toExternalForm().endsWith(".jar") || result.toExternalForm().endsWith(".zip"))
							result = new URL("jar:".concat(result.toExternalForm()).concat("!/").concat(clsAsResource));
						else if (new File(result.getFile()).isDirectory()) result = new URL(result, clsAsResource);
					} catch (MalformedURLException ignore) {}
				}
			}
		}
		if (result == null) {
			final ClassLoader clsLoader = cls.getClassLoader();
			result = clsLoader != null ? clsLoader.getResource(clsAsResource)
					: ClassLoader.getSystemResource(clsAsResource);
		}
		System.out.println(result.toString());
		return result.toString();
	}
}
