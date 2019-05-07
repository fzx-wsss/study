package com.wsss.algorithm.hot.spot;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import com.wsss.algorithm.hot.spot.dao.RequestRecordDao;
import com.wsss.algorithm.hot.spot.dao.TotalLimitFlowDao;
import com.wsss.algorithm.hot.spot.model.RequestRecord;
import com.wsss.algorithm.hot.spot.model.TotalLimitFlow;

public class UpdateTask2 implements Runnable {
	private final static String code = "111111";
	private final static Date date = new Date();
	private RequestRecord request;
	private CountDownLatch count;
	private static CompressUpdateTask task = new UpdateTask2.CompressUpdateTask();
	private static CompressUpdateTask task2 = new UpdateTask2.CompressUpdateTask();
	
	public UpdateTask2(RequestRecord request,CountDownLatch count) {
		super();
		this.request = request;
		this.count = count;
	}

	@Override
	public void run() {
		try {
			if(Math.random() > 0.5) {
				task.submit(request).await();
			} else {
				task2.submit(request).await();
			}
			count.countDown();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("完成");
	}
	
	
	private static class CompressUpdateTask {
		private volatile Map<RequestRecord,CountDownLatch> list = new ConcurrentHashMap<>(50);
		private Thread submitThread = new Thread(new Runnable() {
			@Override
			public void run() {
				Transaction newTransaction = null;
				while(true) {
					try {
						Thread.sleep(30);
						newTransaction = null;
						if(list.isEmpty()) continue;
						long start = System.currentTimeMillis();
						Map<RequestRecord,CountDownLatch> map = list;
						list = new ConcurrentHashMap<>(50);
						//System.out.println("size:" + map.size());
						SqlSession session = HotSpot.getSession().openSession();
						TransactionFactory transactionFactory = new JdbcTransactionFactory();
				        newTransaction=transactionFactory.newTransaction(session.getConnection());
						TotalLimitFlowDao totalLimitFlowDao = session.getMapper(TotalLimitFlowDao.class);
						RequestRecordDao requestRecordDao = session.getMapper(RequestRecordDao.class);
						
						BigDecimal sum = BigDecimal.ZERO;
						for(Entry<RequestRecord,CountDownLatch> entry : map.entrySet()) {
							RequestRecord record = entry.getKey();
							requestRecordDao.insert(record);
							sum = sum.add(record.getTransAmt());
						}
						
						System.out.println(">>>>>>>>>>>" + (System.currentTimeMillis() - start));
						TotalLimitFlow totalLimitFlow = totalLimitFlowDao.selectByLimitCodeAndDateForUpdate(code, date);
						BigDecimal flow = totalLimitFlow.getDayFlow().add(sum);
						totalLimitFlow.setDayFlow(flow);
						totalLimitFlowDao.updateByPrimaryKey(totalLimitFlow);
						for(Entry<RequestRecord,CountDownLatch> entry : map.entrySet()) {
							entry.getValue().countDown();
						}
						System.out.println(">>>>>>>>>>>>>>>>>>>>>>>" + (System.currentTimeMillis() - start));
					} catch(Exception e) {
						e.printStackTrace();
						if(null != newTransaction) {
							try {
								newTransaction.rollback();
								
							} catch (SQLException ex) {
								ex.printStackTrace();
							}
						}
						
					} finally {
						if(null != newTransaction) {
							try {
								newTransaction.commit();
								newTransaction.close();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
				
			}
		});
		
		public CompressUpdateTask() {
			submitThread.setDaemon(true);
			submitThread.start();
		}

		public CountDownLatch submit(RequestRecord record) {
			CountDownLatch count = new CountDownLatch(1);
			list.put(record, count);
			return count;
		}
	}
	
}
