package com.wsss.algorithm.hot.spot;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import com.wsss.algorithm.hot.spot.dao.RequestRecordDao;
import com.wsss.algorithm.hot.spot.dao.TotalLimitFlowDao;
import com.wsss.algorithm.hot.spot.model.RequestRecord;
import com.wsss.algorithm.hot.spot.model.TotalLimitFlow;

public class UpdateTask implements Runnable {
	private final static String code = "111111";
	private final static Date date = new Date();
	private TotalLimitFlowDao totalLimitFlowDao;
	private RequestRecord request;
	private RequestRecordDao requestRecordDao;
	private SqlSession session;
	private CountDownLatch count;
	
	public UpdateTask(RequestRecord request, SqlSession session,CountDownLatch count) {
		super();
		this.request = request;
		this.session = session;
		this.count = count;
		this.totalLimitFlowDao = session.getMapper(TotalLimitFlowDao.class);
		this.requestRecordDao = session.getMapper(RequestRecordDao.class);
	}

	@Override
	public void run() {
		TransactionFactory transactionFactory = new JdbcTransactionFactory();
		  
        Transaction newTransaction=transactionFactory.newTransaction(session.getConnection());
		try {
			requestRecordDao.insert(request);
			TotalLimitFlow totalLimitFlow = totalLimitFlowDao.selectByLimitCodeAndDateForUpdate(code, date);
			BigDecimal flow = totalLimitFlow.getDayFlow().add(request.getTransAmt());
			totalLimitFlow.setDayFlow(flow);
			totalLimitFlowDao.updateByPrimaryKey(totalLimitFlow);
		} catch(Exception e) {
			e.printStackTrace();
			try {
				newTransaction.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			try {
				newTransaction.close();
				//session.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			count.countDown();
		}
		System.out.println("完成");
	}
	
}
