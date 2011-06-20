package db;

import play.Logger;
import play.PlayPlugin;

import com.google.gson.JsonObject;

public class MongoPlugin extends PlayPlugin {
	
	@Override
	public void onApplicationStop() {
		if( MongoModule.mongo != null ) { 
			Logger.info("Stopping Mongo Driver");
			MongoModule.mongo.close(); 
		}
	}
	
	
	@Override
	public String getStatus() {
		if( MongoModule.mongo == null ) { 
			return null;
		}

		StringBuilder result = new StringBuilder();
		result.append("MongoDB\n");
		result.append("~~~~~~~\n");
		
		result	.append("Driver version: ") 
				.append( MongoModule.mongo.getVersion()  )
				.append("\n")
		
				.append("Debug string: ")
				.append( MongoModule.mongo.debugString() )
				.append("\n")
				
				.append("Connect point: ")
				.append( MongoModule.mongo.getConnectPoint())
				.append("\n")

				.append("Options: ")
				.append( MongoModule.mongo.getMongoOptions().toString() ) 
				.append("\n")
		
				;
		
		if( MongoModule.db != null ) { 
			result
				.append("Server status: ")
				.append( MongoModule.db.command("serverStatus") ) 
				.append("\n")
	
				.append("Server stats: ")
				.append( MongoModule.db.command("dbstats") ) 
				.append("\n")
			
				.append("Last error: ")
				.append( MongoModule.db.getLastError().toString() ) 
				.append("\n")
		
			;
			
		}
		
		
		return result.toString();
	}
	
	@Override
	public JsonObject getJsonStatus() {
		// TODO Auto-generated method stub
		return super.getJsonStatus();
	}
	
}
