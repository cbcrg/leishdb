package models;

/**
 * A generic filter object for query collections 
 * 
 * @author Paolo Di Tommaso
 * 
 *
 */
public class QueryFilter {

	public int page;
	public int start;
	public int limit;
	public String sort;
	public String dir;
	public String callback;
	public String restriction;

	@Override
	public String toString() {
		return "QueryFilter [page=" + page + ", start=" + start + ", limit="
				+ limit + ", sort=" + sort + ", dir=" + dir + ", callback="
				+ callback + "]";
	}
	
	
}
