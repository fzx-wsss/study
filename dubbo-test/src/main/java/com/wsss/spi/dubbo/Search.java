package com.wsss.spi.dubbo;


import org.apache.dubbo.common.extension.SPI;

@SPI
public interface Search {
	void search(String keyword);
}