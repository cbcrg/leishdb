package conf;

import play.Logger;
import play.Play;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoURI;

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


	@Provides @Singleton
	public DB getDB() { 
		Logger.info("Providing Mongo database instance");
		String conf = Play.configuration.getProperty("mongo.url","localhost");
		try  {
			MongoURI uri = new MongoURI(conf);
			Mongo mongo = new Mongo(uri); 
			return mongo.getDB(uri.getDatabase());
		}
		catch( Throwable e ) { 
			Logger.error(e, "Unable to access to mongodb at the following uri: %s", conf);
			return null;
		}
		
	}
	
	
}
