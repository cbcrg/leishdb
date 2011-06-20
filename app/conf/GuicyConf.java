package conf;

import play.Logger;
import play.modules.guice.GuiceSupport;

import com.google.inject.Guice;

import db.MongoModule;

/**
 * 
 * Set-up the application configuration based on Guice
 * 
 * @author Paolo Di Tommaso
 *
 */
public class GuicyConf extends GuiceSupport {
        
	protected com.google.inject.Injector configure() {
        Logger.info("Configuring Guice modules");
        
		return Guice.createInjector(
        		   new MongoModule()
           );
        }
}