package com.wsss.algorithm.hot.spot.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wsss.algorithm.hot.spot.model.TotalLimitFlow;

public interface TotalLimitFlowDao {
	int deleteByPrimaryKey(String code);

	int insert(TotalLimitFlow record);

	TotalLimitFlow selectByPrimaryKey(String code);

	List<TotalLimitFlow> selectAll();

	int updateByPrimaryKey(TotalLimitFlow record);

	/** 根据共有限额code和日期查询当日流量,不加锁 */
	TotalLimitFlow selectByLimitCodeAndDate(@Param("limitCode") String limitCode, @Param("date") Date date);

	/** 根据共有限额code和日期查询当日流量,加锁 */
	TotalLimitFlow selectByLimitCodeAndDateForUpdate(@Param("limitCode") String limitCode, @Param("date") Date date);
}