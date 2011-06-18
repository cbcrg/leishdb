package conf;

import play.Logger;
import play.PlayPlugin;

public class MongoPlugin extends PlayPlugin {
	
	@Override
	public void onApplicationStop() {
		if( MongoModule.mongo != null ) { 
			Logger.info("Stopping Mongo Driver");
			MongoModule.mongo.close(); 
		}
	}
	
}
