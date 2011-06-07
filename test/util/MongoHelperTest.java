package util;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import play.libs.IO;
import play.test.UnitTest;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

public class MongoHelperTest extends UnitTest {

	private BasicDBObject obj;

	@Before
	public void before() { 
		obj = new BasicDBObject();
	}
	
	@Test
	public void testSelectBasic() { 
		
		obj = new BasicDBObject();
		obj.put("alpha", 1);
		obj.put("beta", "Hola");

		/*
		 * tests 
		 */
		assertEquals( 1, MongoHelper.select(obj, "alpha") );
		assertEquals( "Hola", MongoHelper.select(obj, "beta") );
	}
	
	@Test 
	public void testSelectNested() { 

		/* 
		 * nested value 
		 */
		DBObject nested = new BasicDBObject();
		nested.put("x", 1);
		nested.put("y", 2);
		nested.put("z", 3);
		obj.put("delta", nested);
		
		
		assertEquals( 3, MongoHelper.select(obj, "delta.z") );
		assertEquals( null, MongoHelper.select(obj, "a.b.c") );
		
	}
	
	@Test
	public void testSelectArrayOfString() { 
		
		/* 
		 * list of string 
		 */
		BasicDBList listStrings = new BasicDBList();
		listStrings.add( "Jaja" );
		listStrings.add( "Ehehe" );
		listStrings.add( "Uhuh" );
	
		obj.put("array", listStrings );
		
		assertEquals( "Jaja", MongoHelper.select(obj,"array"));

	}
	
	@Test 
	public void testSelectListOfObjects() { 
		BasicDBList listObjs = new BasicDBList();
		
		DBObject simple = new BasicDBObject();
		simple.put("x1", "Value 1");
		simple.put("x2", "Value 2");
		listObjs.add(simple);

		simple = new BasicDBObject();
		simple.put("x1", "Value 3");
		simple.put("x2", "Value 4");
		listObjs.add(simple);
		
		obj.put("simple", listObjs);

		assertEquals( "Value 2", MongoHelper.select(obj,"simple.x2"));
	
	}
	
	@Test 
	public void testSelectListEmpty() { 
		/* empty list */
		BasicDBList listEmpty = new BasicDBList();	
		obj.put("empty", listEmpty);
		
		assertEquals( null, MongoHelper.select(obj,"empty.x.y"));
	}
	
	
	@Test 
	public void testBigObject() { 
		String entry = IO.readContentAsString(new File("entry.json"));
		DBObject dbo = (DBObject) JSON.parse(entry);
		
		assertEquals( "D9YJ01", MongoHelper.select(dbo, "accession"));
		assertEquals( "D9YJ01_AMPQU", MongoHelper.select(dbo, "name"));
		assertEquals( "Actin", MongoHelper.select(dbo, "protein.submittedName.fullName.value"));
		assertEquals( "act1", MongoHelper.select(dbo, "gene.name.value"));
		assertEquals( "Ampelomyces quisqualis", MongoHelper.select(dbo, "organism.name.value"));
}
}
