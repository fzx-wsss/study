package com.wsss.frame.dubbo.adpative;


import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Adaptive;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.validation.Validation;
import org.apache.dubbo.validation.Validator;

@Adaptive
public class Validation$Adpative implements Validation {
	public Validator getValidator(URL arg0) {
		if (arg0 == null) throw new IllegalArgumentException("url == null");
		URL url = arg0;
		String extName = url.getParameter("validation", "jvalidation");
		if (extName == null) throw new IllegalStateException(
				"Fail to get extension(com.alibaba.dubbo.validation.Validation) name from url(" + url.toString()
						+ ") use keys([validation])");
		Validation extension = (Validation) ExtensionLoader
				.getExtensionLoader(Validation.class).getExtension(extName);
		return extension.getValidator(arg0);
	}
}