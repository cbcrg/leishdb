package controllers;

import static util.Dsl.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.bson.types.ObjectId;

import play.Logger;
import play.mvc.Before;
import play.mvc.Controller;
import util.MongoHelper;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class Application extends Controller {

	
	static final Map<String,String> mapping = 	
			map( entry("accession","accession"),
				 entry("name", "name"),
				 entry("protein", "protein.submittedName.fullName.value"),
				 entry("gene","gene.name.value"),
				 entry("organism","organism.name.value"),
				 entry("length", "sequence.length")
				 );
	
	@Inject
	static DB db;
	
	@Before
	public static void before() { 
		renderArgs.put("_app_title", "Leish DB");
	}
	
	
	/**
	 * Render the project index page 
	 */
    public static void index() {
        
    	List<String> collections = new ArrayList<String>(db.getCollectionNames());
    	
    	render(collections);
    }
    
    
    /**
     * Just a sample page for testing purpose 
     */
    public static void layout() { 
    	render();
    }

    public static void query() { 
    	render();
    }
    
    /**
     * Render the page contains details data for the selected item 
     */
    public static void item() { 
    	render();
    }   
    
    /**
     * The main 'data' extraction method. The following HTTP parameters are supported 
     * <li>start: the record index number from which start the visualization</li>
     * <li>limit</li>
     * <li>dir</li>
     * <li>sort</li>
     * <li>filter</li>
     * <li>group</li>
     * <li>page</li>
     * 
     */
    public static void data() {

    	final String callback = request.params.get("callback");
    	final boolean jsonp = StringUtils.isNotEmpty(callback);

        String directionParam = request.params.get("dir");
        String filterParam = request.params.get("filter");
        String groupParam = request.params.get("group");
        String limitParam = request.params.get("limit");
        String pageParam = request.params.get("page");
        String sortParam = request.params.get("sort");
        String startParam = request.params.get("start");
   	
    	
    	DBCollection coll = db.getCollection("leishdata");
    	if( coll == null ) { 
    		error("Missing collection 'leishdata'");
    	}
    	
    	int skip = NumberUtils.toInt(startParam);
    	int limit = NumberUtils.toInt(limitParam, 200);
    	
    	/* 
    	 * count the total documents in the collections 
    	 */
    	long total = coll.count();
    	
    	/* 
    	 * get the 'general' cursor 
    	 */
    	DBCursor cursor = coll.find();
    	
    	/* 
    	 * check if a sorting is defined 
    	 */
    	DBObject orderBy = null;
    	if( StringUtils.isNotEmpty(sortParam) ) { 
    		sortParam = mapping.get(sortParam);
    	}
    	if( StringUtils.isNotEmpty(sortParam) ) { 
    		orderBy = new BasicDBObject();
    		int direction = "DESC".equals(directionParam.toUpperCase()) ? -1 : 1;
    		orderBy.put(sortParam, direction);
        	cursor.sort(orderBy);
    	}

		Logger.debug("Data Start: %s; Limit: %s; %s ", skip, limit, (orderBy != null ? "Sort by: "+orderBy : ""));
		Logger.debug("Data Filter: %s", filterParam);

		/* 
		 * impose 'skip' and 'limit' to the cursor
		 */
    	cursor.skip(skip).limit(limit);
    	
    	/*
    	 * create the JSON result data
    	 */
    	StringBuffer result = new StringBuffer();
    	
    	result.append("{");
    	result.append("\"total\":") .append( total ) .append(", ");
    	
    	result.append("\"entries\":");
    	
    	result.append("[");
    	int i=0;
		DBObject target = new BasicDBObject();
    	while( cursor.hasNext() ) { 
    		// fetch the next document 
    		DBObject item = cursor.next();
    		
    		// add a comma to json result after the first record 
    		if( i++ > 0 ) { result.append(","); }

    		// extract the document id
    		target.put("_id", ((ObjectId)item.get("_id")).toString() );
        	
    		// remap the document to plain representation for table presentation
        	for( java.util.Map.Entry<String, String> map : mapping.entrySet() ) { 
        		target.put( map.getKey(), MongoHelper.select(item, map.getValue()) );
        	}
		
    		result.append( target.toString() );
    	}
    	result.append("]}");
    	
    	/* 
    	 * render back the JSON result 
    	 */
    	if( jsonp ) { 
        	response.contentType = "text/javascript";
        	renderText( callback + "(" + result.toString() + ")");
    	}
    	else { 
        	response.contentType = "application/x-json";
        	renderText(result.toString());
    	}
    } 
     

        
    
}