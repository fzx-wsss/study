package com.wsss.spi.dubbo;

import com.alibaba.dubbo.common.extension.SPI;

@SPI
public interface Search {
	void search(String keyword);
}