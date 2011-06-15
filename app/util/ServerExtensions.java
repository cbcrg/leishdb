package util;

import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang.StringUtils;

import play.libs.I18N;
import play.templates.JavaExtensions;

public class ServerExtensions extends JavaExtensions {

	/**
	 * Format a sequence in a human readeable way 
	 * 
	 * @param seq the sequence of residues 
	 * @param nBlocks the number of blocks for columns 
	 * @param sBlock the numbers of residues in each block 
	 * @return the formatted string 
	 */
	public static String seqfmt(String seq, Integer nBlocks, Integer sBlock ) { 
		
		
		if( StringUtils.isEmpty(seq)) return seq;

		
		StringBuffer result = new StringBuffer();
		char[] buffer = new char[sBlock]; // <-- the number of AA in each block
		int i=0;
		StringReader reader = new StringReader(seq);
		int len;
		try { 
			while( (len=reader.read(buffer)) != -1 ) { 
				result.append(buffer,0,len) .append(" ");
				
				if( ++i >= nBlocks ) { 
					result.append("\n");
					i=0;
				}
			}
		} catch( IOException e ) { 
			throw new RuntimeException(e);
		}
		
		/* 3. return the result */
		return result.toString();		
	}
	
	public static String seqfmt(String seq) { 
		return seqfmt(seq,6,10);
	}
	
	public static String seqfmt(String seq, Integer nBlocks ) { 
		return seqfmt(seq,nBlocks,10);
	}
	
	public static String format(XMLGregorianCalendar cal) { 
		return new SimpleDateFormat(I18N.getDateFormat()).format( cal.toGregorianCalendar().getTime() );
	}

	
//	public static String get(DBObject obj, String path) {
//		if( obj == null ) return null;
//		
//		Object result = MongoHelper.select(obj, path);
//		return result != null ? result.toString() : null;
//	}
}
