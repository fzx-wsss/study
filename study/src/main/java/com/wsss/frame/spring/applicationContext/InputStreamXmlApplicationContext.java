package com.wsss.frame.spring.applicationContext;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;

public class InputStreamXmlApplicationContext extends AbstractXmlApplicationContext {
	private Resource[] configResources;

	public InputStreamXmlApplicationContext(String path) throws IOException {
		this(InputStreamXmlApplicationContext.class.getResourceAsStream(path));
	}
	
	public InputStreamXmlApplicationContext(InputStream is) throws IOException {
		super();
		configResources = new Resource[1];

		byte[] bytes = new byte[is.available()];
		is.read(bytes);
		configResources[0] = new ByteArrayResource(bytes);
		refresh();
	}

	@Override
	protected Resource[] getConfigResources() {
		return this.configResources;
	}
}
