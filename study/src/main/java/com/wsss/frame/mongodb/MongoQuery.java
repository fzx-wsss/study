package com.wsss.frame.mongodb;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bson.Document;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.wsss.frame.mongodb.bean.Person;

public class MongoQuery {
//	@Test
//	public void testCollect() {
//		try {
//			// 连接到 mongodb 服务
//			MongoCredential credential = MongoCredential.createCredential("admin", "test", "admin".toCharArray());
//			MongoClient mongo = new MongoClient(new ServerAddress("39.105.35.145", 37018), Arrays.asList(credential));
//			// 根据mongodb数据库的名称获取mongodb对象 ,
//			MongoDatabase db = mongo.getDatabase("test");
//			MongoCollection<Document> it = db.getCollection("test");
//			System.out.println(it.find().first());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	@Test
	public void testSpringDataMongodb() {
		try {
			AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
			context.register(MongoQuery.class);
			context.refresh();
			MongoTemplate mongoTemplate = context.getBean("mongoTemplate", MongoTemplate.class);
			System.out.println(mongoTemplate.getCollectionNames());
			Query query = new Query(Criteria.where("name").is("123"));
			Document map = mongoTemplate.findOne(query, Document.class,"test");
			System.out.println(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void insert() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(MongoQuery.class);
		context.refresh();
		MongoTemplate mongoTemplate = context.getBean("mongoTemplate", MongoTemplate.class);
		
		for(int i=0;i<1000;i++) {
			Person person = new Person();
			person.setName("fan" + i);
			person.setAge(i);
			person.setAddress("beijing");
			person.setPhone(Arrays.asList("17090050107"));
			mongoTemplate.insert(person);
			System.out.println(i);
		}
	}
	
	@Bean
	public MongoTemplate mongoTemplate() {
		MongoCredential credential = MongoCredential.createCredential("test", "admin", "test".toCharArray());
		MongoClient mongo = new MongoClient(new ServerAddress("39.105.35.145", 37018), Arrays.asList(credential));
		return new MongoTemplate(mongo, "test", null);
	}
}
