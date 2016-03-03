package com.gmail.maximsmol.CoursePlanner;

import java.io.Reader;
import java.io.Writer;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONTokener;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONWriter;

public final class ConfigData {
	private static final HashMap<String, String> strData = new HashMap<String, String>();
	private static final HashMap<String, Integer> intData = new HashMap<String, Integer>();
	private static final HashMap<String, Double> doubleData = new HashMap<String, Double>();

	private ConfigData() {}

	public static void clear() {
		strData.clear();
	}

	public static void read(final Reader reader) {
		clear();

		final JSONTokener tokenizer = new JSONTokener(reader);
		final JSONObject obj = new JSONObject(tokenizer);

		Iterator<String> keys = obj.keys();
		while (keys.hasNext()) {
			String key = keys.next();

			Object val = obj.get(key);

			if (val instanceof String)
				strData.put(key, (String) val);
			else if (val instanceof Integer)
				intData.put(key, (int) val);
			else if (val instanceof Double)
				doubleData.put(key, (double) val);
		}
	}

	public static String str(String key) {
		return strData.get(key);
	}

	public static int integer(String key) {
		return intData.get(key);
	}

	public static double floating(String key) {
		return doubleData.get(key);
	}
}