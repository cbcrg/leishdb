package controllers;

import java.lang.reflect.Type;
import java.util.Iterator;

import javax.inject.Inject;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.bson.types.ObjectId;
import org.uniprot.uniprot.Entry;
import org.uniprot.uniprot.GeneNameType;

import play.CorePlugin;
import play.mvc.Before;
import play.mvc.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class Application extends Controller {

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
    	render();
    }

    
    public static void description() { 
    	render();
    }

    public static void contacts() { 
    	render();
    }
 
    /**
     * Just a sample page for testing purpose 
     */
    public static void layout() { 
    	render();
    }

    public static void db() { 
    	render();
    }
    
    /**
     * Render the page contains details data for the selected item 
     */
    public static void item(String id) { 
    	DBCollection data = db.getCollection("leishdata");
    	DBObject obj = data.findOne( new BasicDBObject("_id", new ObjectId(id)));
    	GsonBuilder builder = new GsonBuilder();
    	builder.registerTypeAdapter(XMLGregorianCalendar.class, new XMLGregorianCalendarDeserializer());
    	Gson gson = builder.create();
    	Entry entry = gson.fromJson( obj.toString(), Entry.class);

    	/*
    	 * extract all 'gene.name.value' from the unitprot entry 
    	 */
    	BasicDBList refs = new BasicDBList();
    	Iterator<Entry.Gene> geneIt = entry != null && entry.getGene() != null ? entry.getGene().iterator() : null;
    	Iterator<GeneNameType> nameIt;
    	while( geneIt != null && geneIt.hasNext()  ) { 
    		Entry.Gene gene = geneIt.next();
    		nameIt = gene!=null && gene.getName() != null ? gene.getName().iterator() : null;
    		while( nameIt != null && (nameIt).hasNext() ) { 
    			GeneNameType name = nameIt.next();
    			refs.add( name.getValue() );
    		}
    	}
    	
    	/*
    	 * lookup all leishdrub features for the found 'refid'
    	 */
    	DBCursor features=null;
    	if( refs.size()>0 ) { 
        	DBCollection leish = db.getCollection("leishcoll");
        	DBObject filter = new BasicDBObject("refid", new BasicDBObject("$in", refs));
        	features = leish.find(filter);
    	}

    	render(entry, features);
    }   
    
    
    /**
     * Deserializer for date time object 
     * 
     * @author Paolo Di Tommaso
     *
     */
    
    static class XMLGregorianCalendarDeserializer implements JsonDeserializer<XMLGregorianCalendar> {

    	static DatatypeFactory factory;
    	
    	static { 
    		try {
				factory =  DatatypeFactory.newInstance();
			} catch (DatatypeConfigurationException e) {
				throw new RuntimeException(e);
			}
    	}
    	
    	public XMLGregorianCalendar deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    		
    		int year = json.getAsJsonObject().get("year").getAsInt();
    		int month = json.getAsJsonObject().get("month").getAsInt();
    		int day = json.getAsJsonObject().get("day").getAsInt();
    		int timezone = json.getAsJsonObject().get("timezone").getAsInt();
    		int hour = json.getAsJsonObject().get("hour").getAsInt();
    		int minute = json.getAsJsonObject().get("minute").getAsInt();
    		int second = json.getAsJsonObject().get("second").getAsInt();

    		return factory.newXMLGregorianCalendar(year, month, day, hour, minute, second, 0, timezone);
    	  }

    }
    
	/**
	 * Renders a page containing Play! runtime informantion 
	 */
	public static void playinfo() {
		String info = CorePlugin.computeApplicationStatus(false);
		render(info);
	} 
	
	public static void tab() { 
		render();
	}
    
}