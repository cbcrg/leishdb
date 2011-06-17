package jobs;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import play.Logger;
import play.Play;
import play.jobs.Every;
import play.jobs.Job;
import play.libs.Mail;

import com.mongodb.DB;
import com.mongodb.DBObject;

/**
 * Keep active the mongo DB connection and monitor for failure sending an email 
 * when something goes wrong 
 * 
 * @author Paolo Di Tommaso
 *
 */

@Every("2min")
public class MongoWatchdog extends Job {

	@Inject
	static DB db;
	
	enum Status { OK, FAIL } 
	
	static Status status = Status.OK;
	
	@Override
	public void doJob() throws Exception {
		
		String connection = Play.configuration.getProperty("mongo.url");
		boolean hasError = false;
		Exception exception=null;
		DBObject result = null;
		
		try { 
			result = db.command("ping");
			Object val;
			hasError = result == null || (val=result.get("ok"))==null || !"1.0".equals(val.toString()) ;
		}
		catch( Exception e ) { 
			exception = e;
			hasError = true;
		}

		/* 
		 * sent an email only on transition status 
		 */
		if( status == Status.OK && hasError ) { 
			status = Status.FAIL; 
			String message = String.format("Failing PINGing Mongo DB server at '%s' %s", connection, result != null ? "["+result+"]" : "");
			if( exception == null ) { 
				Logger.error(message);
				sendMail("Leish DB - Status ERROR", message);
			}
			else { 
				Logger.error(exception,message);
				sendMail("Leish DB - Status ERROR", message + "\nCaused by: \n%s", ExceptionUtils.getStackTrace(exception));
			}

		
		}
		else if( status == Status.FAIL && !hasError ) { 
			status = Status.OK;
			String message = String.format("Mongo DB server back to sanity [%s]", result);
			Logger.info(message);
			sendMail("Leish DB - Status OK", message);
		}
		
				
	}
	
	
	static void sendMail(String subject, String body, Object... args ) { 
		String webmaster = Play.configuration.getProperty("settings.webmaster");
		if( StringUtils.isEmpty(webmaster)) { 
			return;
		}

		
		try { 
			Email email = new SimpleEmail();
			email.setFrom(webmaster);
			email.addTo(webmaster);
			email.setSubject(subject);
			email.setContent(String.format(body,args), "text/plain");
			
			Mail.send(email);
		}
		catch( EmailException e ) { 
			Logger.warn(e, "Unable to send watchdog notification email");
		}
		
	}
	
}
