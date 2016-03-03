package com.gmail.maximsmol.CoursePlanner.model;

import java.io.Reader;
import java.io.Writer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONTokener;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONWriter;

public final class Database {
	private Database() {}

	public static boolean databaseFrozen = false;

	public static void clear() {
		databaseFrozen = false;

		Skill.skills.clear();
		SkillSet.skillSets.clear();
		Course.courses.clear();
	}

	private static void readDatabaseElement(final JSONArray arr, final int i) {
		if (i == arr.length()) return;

		final JSONObject obj = arr.getJSONObject(i);
		final String type = obj.getString("type");

		if ("skill".equals(type)) Skill.read(obj);
		else if ("skillset".equals(type)) SkillSet.read(obj);
		else if ("course".equals(type)) Course.read(obj);

		readDatabaseElement(arr, i+1);
	}

	public static void read(final File f) {
		clear();

		readDatabaseElement(importFileAsJSONArray(f), 0);
		databaseFrozen = true;
	}

	public static void prettyPrint(final Writer writer) throws IOException {
		final JSONArray res = new JSONArray();

		for (final Skill skill : Skill.skills.values())
			res.put(skill.toJSON());
		for (final SkillSet set : SkillSet.skillSets.values())
			res.put(set.toJSON());
		for (final Course course : Course.courses.values())
			res.put(course.toJSON());

		writer.write(res.toString(4));
		writer.flush();
	}

	public static void write(final Writer ioWriter) throws IOException {
		final JSONWriter writer = new JSONWriter(ioWriter);

		writer.array();

		for (final Skill skill : Skill.skills.values())
			skill.write(writer);
		for (final SkillSet set : SkillSet.skillSets.values())
			set.write(writer);
		for (final Course course : Course.courses.values())
			course.write(writer);

		writer.endArray();

		ioWriter.flush();
	}

	public static JSONTokener importFileAsJSON(final File f) {
		try {
			return new JSONTokener(new FileReader(f));
		}
		catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public static JSONObject importFileAsJSONObject(final File f) {
		return new JSONObject(importFileAsJSON(f));
	}

	public static JSONArray importFileAsJSONArray(final File f) {
		return new JSONArray(importFileAsJSON(f));
	}
}
