package util;

import java.net.UnknownHostException;

import org.junit.Before;
import org.junit.Test;

import play.Play;
import play.test.UnitTest;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBAddress;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class MongoTest extends UnitTest {


	private DB db;

	@Before
	public void before() throws UnknownHostException { 
		db = Mongo.connect(new DBAddress(Play.configuration.getProperty("mongo.url")));
	}
	
	@Test
	public void testRegEx() { 
		DBCollection index = db.getCollection("bigindex");
		
		DBObject ref = new BasicDBObject("key", new BasicDBObject("$regex", "^H1A"));
		DBCursor c = index.find(ref);
		
		assertTrue( c.count()>0 );
	}
}
