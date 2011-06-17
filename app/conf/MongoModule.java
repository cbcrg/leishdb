package conf;

import java.net.UnknownHostException;

import org.apache.commons.lang.math.NumberUtils;

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
		String val;
		
		if( (val=Play.configuration.getProperty("mongo.connectionsPerHost")) != null ) { 
			opt.connectionsPerHost = NumberUtils.toInt(val);
		}

		if( (val=Play.configuration.getProperty("mongo.connectTimeout")) != null ) { 
			opt.connectTimeout = NumberUtils.toInt(val);
		}

		if( (val=Play.configuration.getProperty("mongo.socketTimeout")) != null ) { 
			opt.socketTimeout = NumberUtils.toInt(val);
		}
		
		if( (val=Play.configuration.getProperty("mongo.autoConnectRetry")) != null ) { 
			opt.autoConnectRetry = Boolean.parseBoolean(val);
		}
		
		if( (val=Play.configuration.getProperty("mongo.threadsAllowedToBlockForConnectionMultiplier")) != null ) { 
			opt.threadsAllowedToBlockForConnectionMultiplier =  NumberUtils.toInt(val);;
		}
		
		Logger.debug("Mongo options: %s", opt);
		
		return opt;
	}

	
}
