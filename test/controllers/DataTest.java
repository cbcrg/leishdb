package controllers;

import java.io.IOException;

import org.junit.Test;

import play.mvc.Http.Response;
import play.test.FunctionalTest;

import com.mongodb.BasicDBList;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

public class DataTest extends FunctionalTest {

	@Test
	public void testSpecies() throws IOException { 
		Response response = GET("/species.json");
		assertEquals( "application/x-json",response.contentType );
		assertEquals( Integer.valueOf(200), response.status );

		DBObject result = (DBObject) JSON.parse( response.out.toString() );
		
		assertTrue( result.containsField("total") );
		assertTrue( result.containsField("species") );
		
		assertEquals( result.get("total").toString(), String.valueOf(((BasicDBList)result.get("species")).size()) );
	}
	
	@Test
	public void testDBtypes() { 

		Response response = GET("/dbtypes.json");
		assertEquals( "application/x-json",response.contentType );
		assertEquals( Integer.valueOf(200), response.status );

		DBObject result = (DBObject) JSON.parse( response.out.toString() );
		
		assertTrue( result.containsField("total") );
		assertTrue( result.containsField("dbtypes") );
		
		assertEquals( result.get("total").toString(), String.valueOf(((BasicDBList)result.get("dbtypes")).size()) );

		
	}

	
	@Test
	public void testFeatureTypes() { 

		Response response = GET("/featuretypes.json");
		assertEquals( "application/x-json",response.contentType );
		assertEquals( Integer.valueOf(200), response.status );

		DBObject result = (DBObject) JSON.parse( response.out.toString() );
		
		assertTrue( result.containsField("total") );
		assertTrue( result.containsField("items") );
		
		assertEquals( result.get("total").toString(), String.valueOf(((BasicDBList)result.get("items")).size()) );
	}	


	@Test
	public void testFeatureDescriptions() { 

		Response response = GET("/featuredescriptions.json");
		assertEquals( "application/x-json",response.contentType );
		assertEquals( Integer.valueOf(200), response.status );

		DBObject result = (DBObject) JSON.parse( response.out.toString() );
		
		assertTrue( result.containsField("total") );
		assertTrue( result.containsField("items") );
		
		assertEquals( result.get("total").toString(), String.valueOf(((BasicDBList)result.get("items")).size()) );
	}	
	
	
	public void testFilteData() { 

		Response response = GET("/filterdata.json");
		assertEquals( "application/x-json",response.contentType );
		assertEquals( Integer.valueOf(200), response.status );
	
		System.out.println(response.out.toString());
	}
}
