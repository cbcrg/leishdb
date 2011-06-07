package controllers;

import javax.inject.Inject;

import org.junit.Test;

import play.modules.guice.InjectSupport;
import play.test.UnitTest;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;

@InjectSupport
public class ApplicationTest extends UnitTest {

	@Inject 
	static DB db; 
	
	
	@Test 
	public void testConverter() { 
		DBObject restriction = new BasicDBObject();
		restriction.put("accession","D9YJ01");
		
		DBObject item = db.getCollection("trembl").findOne(restriction);
		
	}
	
	
}
