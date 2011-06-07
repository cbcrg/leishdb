package util;

import org.apache.commons.lang.StringUtils;

import com.mongodb.BasicDBList;
import com.mongodb.DBObject;

public class MongoHelper {

    public static <T> T select( DBObject object, String path ) { 
    	
    	/* if the object is null, just return null */
    	if( object == null ) { return null; }
    	
    	if( StringUtils.isEmpty(path) ) { 
    		throw new IllegalArgumentException("Argument 'path' cannot be empty");
    	}
    	
    	/* split the path */
    	String field=null;
    	String remain=null;
    	
    	int p = path.indexOf('.');
    	if( p != -1 ) { 
    		field = path.substring(0,p);
    		remain = path.substring(p+1);
    	}
    	else { 
    		field = path;
    	}

    	/* if the request field is empty, just return the object itself */
    	if( StringUtils.isEmpty(field) ) { 
    		return (T) object;
    	}
	
    	/* get the field value */
    	Object result = object.get(field);
    	
    	/* normalize lists to its first value */
    	if( result instanceof BasicDBList ) { 
    		result = ( (BasicDBList)result).size()>0 ? ((BasicDBList)result).get(0) : null;
    	}

    	/* go deep into the remaining path of the 'navigation' path */
    	if( StringUtils.isNotEmpty(remain) ) { 
    		return select( (DBObject) result, remain );
    	}
    	
    	return (T) result;

    }
	
	
}
