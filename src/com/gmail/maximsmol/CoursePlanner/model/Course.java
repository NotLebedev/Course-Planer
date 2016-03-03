package com.gmail.maximsmol.CoursePlanner.model;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONWriter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class Course implements DescribedElement {
	public static final HashMap<String, Course> courses = new HashMap<String, Course>();

	protected final String id;
	protected String name;
	protected String description;

	public SkillSetList input;
	public SkillSetList output;

	public Course(final String id, final String name, final String description) {
		this.id = id;
		this.name = name;
		this.description = description;

		input = new SkillSetList(this);
		output = new SkillSetList(this);

		courses.put(id, this);
	}

	public static void update(final ArrayList<SkillSet> skills, final SkillSet set) {
		boolean replaced = false;
		for (int i = 0; i < skills.size(); i++) {
			if (!skills.get(i).equals(set)) continue;

			skills.set(i, set);
			replaced = true;

			break;
		}

		if (!replaced)
			skills.add(set);
	}

	//
	// Database IO
	public static Course read(final JSONObject obj) {
		try{
			if ( !"course".equals(obj.getString("type")) )
				throw new InvalidModelException("Type expected to be \"course\"");
		}
		catch (JSONException e) {
			throw new InvalidModelException("Type field not found", e);
		}

		try {
			final String id = obj.getString("id");

			if (courses.containsKey(id))
				return courses.get(id);

			final Course res = new Course(id, obj.getString("name"), obj.getString("description"));

			final JSONArray inputData  = obj.getJSONArray("input");
			final JSONArray outputData = obj.getJSONArray("output");

			res.input.read(inputData);
			res.output.read(outputData);

			return res;
		}
		catch (RuntimeException e) {
			throw new InvalidModelException("Course object invalid", e);
		}
	}

	public JSONObject toJSON() {
		final JSONObject res = new JSONObject();

		res.put("type", "course");
		res.put("id", id());
		res.put("name", name());
		res.put("description", description());

		res.put("input", input.toJSON());
		res.put("output", output.toJSON());

		return res;
	}

	public void write(final JSONWriter writer) {
		writer.object();

		writer.key("type");
		writer.value("course");

		writer.key("id");
		writer.value(id());

		writer.key("name");
		writer.value(name());

		writer.key("description");
		writer.value(description());

		writer.key("input");
		input.write(writer);

		writer.key("output");
		output.write(writer);

		writer.endObject();
	}

	//
	// Data analysis
	public boolean isSkillTransfered(final SkillSet set) {
		return input.contains(set) && output.contains(set);
	}

	//
	// Getters
	@Override
	public String id() {
		return id;
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public String description() {
		return description;
	}

	//
	// Setters
	@Override
	public void changeName(final String name) {
		this.name = name;
	}

	@Override
	public void changeDescription(final String description) {
		this.description = description;
	}

	//
	// Object as superclass
	@Override
	public boolean equals(final Object obj) {
		if (!(obj instanceof Course)) return false;
		final Course that = (Course) obj;

		return id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public String toString() {
		final StringBuilder res = new StringBuilder();

		res.append("Course#\"");
		res.append(id());
		res.append("\"");

		res.append("{");
			res.append("name: \"");
			res.append(name());
			res.append("\"");

			res.append(", ");

			res.append("description: \"");
			res.append(description());
			res.append("\"");

			res.append(",\n");

			res.append("input: ");
			res.append("[");
				for (final SkillSet set : input) {
					res.append(set.toString());
					res.append(",");
				}
			res.append("]");

			res.append(",]\n");

			res.append("output: ");
			res.append("[");
				for (final SkillSet set : output) {
					res.append(set.toString());
					res.append(",");
				}
			res.append("]");

		res.append("}");

		return res.toString();
	}
}
