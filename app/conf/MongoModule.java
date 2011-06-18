package conf;

import java.lang.reflect.Field;
import java.net.UnknownHostException;
import java.util.Map.Entry;

import org.slf4j.bridge.SLF4JBridgeHandler;

import play.Logger;
import play.Play;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.mongodb.DB;
import com.mongodb.DBAddress;
import com.mongodb.Mongo;
import com.mongodb.MongoOptions;

/**
 * Define the Mongo database connection instance
 * 
 * @author Paolo Di Tommaso
 *
 */
public class MongoModule extends AbstractModule {

	
	@Override
	protected void configure() {
		Logger.info("Configuring MongoDatabase");
	}

	
	static Mongo mongo;
	
	static String dbName; 

	/*
	 * create the single instance 
	 */
	static { 
		
		java.util.logging.Logger.getLogger("com.mongodb").setLevel(java.util.logging.Level.ALL);
		SLF4JBridgeHandler.install();

		
		String conf = Play.configuration.getProperty("mongo.url","localhost");
		Logger.info("Creating Mongo Driver instance to host: '%s'", conf);
		
		try {
			DBAddress address = new DBAddress(conf);
			dbName = address.getDBName();
			mongo = new Mongo(address, options());
		} 
		catch (UnknownHostException e) {
			Logger.fatal(e, "Unable to access to mongodb at the following uri: %s", conf);
		}
	}

	
	@Provides 
	public DB getDB() { 
		return mongo.getDB(dbName);
	}


	private static MongoOptions options() {
		MongoOptions opt = new MongoOptions();
		
		for( Entry<Object, Object> item : Play.configuration.entrySet() ) { 
			String key = (String) item.getKey();
			String val = (String) item.getValue();
			if( key != null && key.startsWith("mongo.options.")) { 
				setOption(opt, key, val);
			}
		}

		
		Logger.info("Mongo options: %s", opt);
		
		return opt;
	}

	
	private static void setOption(MongoOptions opt, String key, String val) {
		if( key == null ) { 
			Logger.warn("Mongo option key cannot be null");
			return; 
		}
		
		if( key.startsWith("mongo.options.") ) { 
			key = key.substring("mongo.options.".length());
		}
		
		try {
			Field field = MongoOptions.class.getField(key);
			field.set(opt, parseValue(val));
		} catch (Exception e) {
			Logger.warn(e, "Error setting Mongo options %s=%s", key, val);
		}
		
	}


	/**
	 * Parse a string vakeu returing a typed object. Supported types are:
	 * <li><code>Boolean</code></li>
	 * <li><code>Integer</code></li>
	 * <li><code>Double</code></li>
	 * <li><code>String</code></li>
	 *
	 * @param value the string value to be converted
	 * @return a typed object for not <i>null</i> input value or <code>null</code> otherwise
	 */
	public static Object parseValue( String value ) {
        if( value == null ) { return null; }
        Object result;
        result = boolValue(value);
        if( result != null ) return result;

        result = intValue(value);
        if( result != null ) return result;

        result = doubleValue(value);
        if( result != null ) return result;

        return value;
	}

	private static Boolean boolValue( String value ) {
        return "true".equals(value) ? Boolean.TRUE : ("false".equals(value) ? Boolean.FALSE: null );
	}

	private static Integer intValue( String value ) {
        try {
                return Integer.parseInt(value);
        } catch( NumberFormatException e ) {
                return null;
        }
	}

	private static Double doubleValue( String value ) {
        try {
                return Double.parseDouble(value);
        } catch( NumberFormatException e ) {
                return null;
        }
	}
	
	
}
