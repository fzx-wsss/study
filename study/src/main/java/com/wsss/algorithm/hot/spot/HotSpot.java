package com.wsss.algorithm.hot.spot;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.wsss.algorithm.hot.spot.model.RequestRecord;
/**
 * 热点账户
 * @author hasee
 *
 */
public class HotSpot {
	private static SqlSessionFactory sqlSessionFactory;
    private static Reader reader;
    private static ExecutorService executor = new ThreadPoolExecutor(100, 100,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(30),new ThreadPoolExecutor.CallerRunsPolicy());
    static{
        try{
            reader    = Resources.getResourceAsReader("com/wsss/algorithm/hot/spot/conf.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static SqlSessionFactory getSession(){
        return sqlSessionFactory;
    }
   
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch count = new CountDownLatch(10000);
        long start = System.currentTimeMillis();
        try {
        	for(int i=0;i<10000;i++) {
        		//SqlSession session = ;
        		String id = UUID.randomUUID().toString().replaceAll("-", "");
        		RequestRecord request = new RequestRecord();
            	request.setTransAmt(new BigDecimal(10));
            	request.setCreateDate(new Date());
            	request.setRequestDate(new Date());
            	request.setCode(id);
            	request.setRequestId(id);
            	executor.execute(new UpdateTask2(request,count));
            	//executor.execute(new UpdateTask(request,sqlSessionFactory.openSession(),count));
        	}
        } finally {
        	//session.close();
        }
        executor.shutdown();
        count.await();
        long end = System.currentTimeMillis();
        System.out.println(end-start);
    }
}
