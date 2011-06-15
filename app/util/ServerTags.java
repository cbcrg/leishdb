package util;

import groovy.lang.Closure;

import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import play.templates.FastTags;
import play.templates.GroovyTemplate.ExecutableTemplate;

public class ServerTags extends FastTags {


	/**
	 * another try to create a simple "each" tag as in #{each cities} .... ${_}
	 * #{/each}
	 * 
	 * @author bran
	 * 
	 * @param args
	 * @param body
	 * @param out
	 * @param template
	 * @param fromLine
	 */
	public static void _each(Map<?, ?> args, Closure body, PrintWriter out, ExecutableTemplate template, int fromLine) {
		Object x = args.get("arg");
		if (x instanceof Collection) {
			Collection items = (Collection) x;
			Iterator it = items.iterator();
			iteratorOnBody(body, it);
		} else if (x instanceof Iterator) {
			Iterator it = (Iterator) x;
			iteratorOnBody(body, it);
		} else if (x instanceof Iterable) {
			Iterator it = ((Iterable) x).iterator();
			iteratorOnBody(body, it);
		} else if (x instanceof Map) {
			int start = 0, end = 0;
			Set<Entry> entrySet = ((Map) x).entrySet();
			Iterator<Entry> it = entrySet.iterator();
			end = entrySet.size();
			int i = 0;
			while (it.hasNext()) {
				Entry o = it.next();
				// body.setProperty("it", o); // 'it' is reserved somewhere
				body.setProperty("_", o);
				body.setProperty("_key", o.getKey());
				body.setProperty("_value", o.getValue());
				body.setProperty("_index", i + 1);
				body.setProperty("_isLast", (i + 1) == end);
				body.setProperty("_isFirst", i == start);
				body.setProperty("_parity", (i + 1) % 2 == 0 ? "even" : "odd");
				body.call();
				i++;
			}
		} else if (x instanceof Object[]) {
			Object[] oa = ((Object[]) x);
			arrayOnBody(body, oa);
		} else if (x instanceof boolean[]) {
			boolean[] ba = ((boolean[]) x);
			Object[] oa = new Object[ba.length];
			for (int i = 0; i < ba.length; i++) {
				oa[i] = ba[i];
			}
			arrayOnBody(body, oa);
		} else if (x instanceof char[]) {
			char[] ba = ((char[]) x);
			Object[] oa = new Object[ba.length];
			for (int i = 0; i < ba.length; i++) {
				oa[i] = ba[i];
			}
			arrayOnBody(body, oa);
		} else if (x instanceof int[]) {
			int[] ba = ((int[]) x);
			Object[] oa = new Object[ba.length];
			for (int i = 0; i < ba.length; i++) {
				oa[i] = ba[i];
			}
			arrayOnBody(body, oa);
		} else if (x instanceof long[]) {
			long[] ba = ((long[]) x);
			Object[] oa = new Object[ba.length];
			for (int i = 0; i < ba.length; i++) {
				oa[i] = ba[i];
			}
			arrayOnBody(body, oa);
		} else if (x instanceof float[]) {
			float[] ba = ((float[]) x);
			Object[] oa = new Object[ba.length];
			for (int i = 0; i < ba.length; i++) {
				oa[i] = ba[i];
			}
			arrayOnBody(body, oa);
		} else if (x instanceof double[]) {
			double[] ba = ((double[]) x);
			Object[] oa = new Object[ba.length];
			for (int i = 0; i < ba.length; i++) {
				oa[i] = ba[i];
			}
			arrayOnBody(body, oa);
		} else {
			// perhaps we need to handle array of primitive types such as [C,
			// [I, [L, [F, etc
			throw new play.exceptions.TagInternalException(
					"the each tag requires a Collection, Map, or an array object.");
		}
	}

	/**
	 * @param body
	 * @param it
	 */
	private static void iteratorOnBody(Closure body, Iterator it) {
		int start = 0;
		int i = 0;
		while (it.hasNext()) {
			Object o = it.next();
			// body.setProperty("it", o); // 'it' is reserved somewhere
			body.setProperty("_", o);
			body.setProperty("_index", i + 1);
			if (!it.hasNext())
				body.setProperty("_isLast", true);
			body.setProperty("_isFirst", i == start);
			body.setProperty("_parity", (i + 1) % 2 == 0 ? "even" : "odd");
			body.call();
			i++;
		}
	}

	/**
	 * @param body
	 * @param oa
	 */
	private static <T> void arrayOnBody(Closure body, T[] oa) {
		int start = 0, end = oa.length;
		int i = 0;
		for (Object o : oa) {
			// body.setProperty("it", o); // 'it' is reserved somewhere
			body.setProperty("_", o);
			body.setProperty("_index", i + 1);
			body.setProperty("_isLast", (i + 1) == end);
			body.setProperty("_isFirst", i == start);
			body.setProperty("_parity", (i + 1) % 2 == 0 ? "even" : "odd");
			body.call();
			i++;
		}
	}

	

	
}
