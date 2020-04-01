
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

	public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
		System.out.println(maximalRectangle(new char[][] {
		}));
	}

	public static int maximalRectangle(char[][] matrix) {
		if(matrix.length < 1) return 0;
		int max = 0;
		return max;
	}

}
