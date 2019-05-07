package com.wsss.algorithm.hot.spot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wsss.algorithm.hot.spot.model.RequestRecord;

public interface RequestRecordDao {
	int deleteByPrimaryKey(String code);

	int insert(RequestRecord record);

	RequestRecord selectByPrimaryKey(String code);

	List<RequestRecord> selectAll();

	int updateByPrimaryKey(RequestRecord record);

	/**
	 * 根据请求号，订单号，系统编号，查询唯一记录，加锁
	 */
	RequestRecord selectByUniqueForUpdate(@Param("requestId") String requestId, @Param("transOrder") String transOrder, @Param("sysCode") String sysCode);
}