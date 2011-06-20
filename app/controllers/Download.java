package controllers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.inject.Inject;

import play.Logger;
import play.Play;
import play.cache.Cache;
import play.libs.Time;
import play.mvc.Controller;
import play.mvc.Scope.Session;
import play.mvc.Util;
import util.MongoHelper;
import util.ServerExtensions;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

/**
 * Handle file downloads 
 * 
 * @author Paolo Di Tommaso
 * 
 *
 */
public class Download extends Controller {

	@Inject
	static DB db;
	

	@Util
    static DBObject cachedFilter( ) { 
    	final String key = "data_filter_" + Session.current().getId();
    	return (DBObject) Cache.get(key);
    }

	
	@Util
	static File getFileName(String suffix, DBObject filter) { 

		String key = filter == null ? "all" : Integer.toHexString(filter.toString().hashCode());
		
		File file = new File( Play.tmpDir, String.format("leishdb-%s.%s", key, suffix));
		if( !file.exists() ) { 
			return file;
		}
		
		int maxSecs = Time.parseDuration(Play.configuration.getProperty("settings.download.ttl","7d"));
		if( (System.currentTimeMillis()-file.lastModified())/1000 > maxSecs ) { 
			Logger.debug("Evicting old temp file: %s", file);
			file.delete();
		}
		
		return file;
		
	}
	
    
    /**
     * Download the selected sequences in FASTA format
     */
    public static void fasta(String filter) throws IOException { 
    	DBObject filterObj = Data.decodeFilter(filter);
    	Logger.debug("Download FASTA filter: %s", filterObj);
	
    	DBCollection data = db.getCollection("leishdata");
    	DBCursor cursor = data.find(filterObj);
    	
    	// set the response header 
    	response.contentType = "text/plain";
		response.setHeader("Content-Disposition", "attachment; filename=\"leishdb.fa\"");
	
		// check if a cached version exists 
    	File file = getFileName("fasta",filterObj);
		if( file.exists() ) { 
			Logger.debug("Returning cached response: '%s'", file);
			renderBinary(file);
			return;
		}

		/* 
		 * create the response file 
		 */
		BufferedWriter chunk = new BufferedWriter( new FileWriter(file) );
    
		// Append the data 
        while( cursor.hasNext() ) {
        	DBObject item = cursor.next();
        	
        	chunk.append(">") .append( String.valueOf(MongoHelper.select(item, "accession")) );
        	chunk.append("\n");
        	
        	String seq = MongoHelper.select(item, "sequence.value");
        	chunk.append( ServerExtensions.seqfmt(seq, 1, 60) );
        	chunk.append("\n");

        }  	

        chunk.close();
        
        renderBinary(file);
    }
    
    
    /**
     * Download the selected sequences in FASTA format
     */
    public static void csv(String filter) throws IOException { 
    	DBObject filterObj = Data.decodeFilter(filter);
    	Logger.debug("Download XXX filter: %s", filterObj);
	
    	DBCollection data = db.getCollection("leishdata");
    	DBCursor cursor = data.find(filterObj);
    	
    	// set the response header 
    	response.contentType = "text/csv";
		response.setHeader("Content-Disposition", "attachment; filename=\"leishdb.csv\"");
	
		// check if a cached version exists 
    	File file = getFileName("csv",filterObj);
		if( file.exists() ) { 
			Logger.debug("Returning cached response: '%s'", file);
			renderBinary(file);
			return;
		}

		/* 
		 * create the response file 
		 */
		BufferedWriter chunk = new BufferedWriter( new FileWriter(file) );

    	// write the header 
    	long c=0;
    	for( java.util.Map.Entry<String, String> map : Data.mapping.entrySet() ) { 
    		if( c++> 0 ) chunk.append("\t"); // <-- note: use a tab character as separator
    		chunk.append( map.getKey() );
    	}
    	chunk.append("\n");		
		

    	// Append the data 
        while( cursor.hasNext() ) {
        	DBObject item = cursor.next();
        	
        	c=0;
        	for( java.util.Map.Entry<String, String> map : Data.mapping.entrySet() ) { 
        		if( c++>0 ) chunk.append("\t");
        		Object value = MongoHelper.select(item, map.getValue()) ;
        		chunk.append( value != null ? value.toString() : "" );
        	}
        	chunk.append("\n");
    	
        }  		

        chunk.close();
        
        renderBinary(file);
    }  
}
