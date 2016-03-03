package com.gmail.maximsmol.CoursePlanner.model;

import java.util.HashMap;

import org.json.JSONWriter;
import org.json.JSONObject;
import org.json.JSONException;

import com.gmail.maximsmol.CoursePlanner.model.Database;

public class Skill implements DescribedElement {
	protected final String id;
	protected String name;
	protected String description;

	public static final HashMap<String, Skill> skills = new HashMap<String, Skill>();

	public Skill(final String id, final String name, final String description) {
		this.id = id;
		this.name = name;
		this.description = description;

		skills.put(id, this);
	}

	//
	// Database IO
	public static Skill read(final JSONObject obj) {
		try {
			if ( !"skill".equals(obj.getString("type")) )
				throw new InvalidModelException("Type expected to be \"skill\"");
		}
		catch (JSONException e) {
			throw new InvalidModelException("Type field not found", e);
		}

		try {
			final String id = obj.getString("id");

			if (skills.containsKey(id))
				return skills.get(id);
			else if (Database.databaseFrozen)
				throw new InvalidModelException("Trying to introduce a new Skill into a frozen database");

			try {
				String name = obj.getString("name");

				try {
					return new Skill(id, name, obj.getString("description"));
				}
				catch (JSONException e) {
					throw new InvalidModelException("Description not found", e);
				}
			}
			catch (JSONException e) {
				throw new InvalidModelException("Name not found", e);
			}
		}
		catch (JSONException e) {
			throw new InvalidModelException("Id not found", e);
		}
	}

	public JSONObject toJSON() {
		final JSONObject res = new JSONObject();

		res.put("type", "skill");
		res.put("id", id());
		res.put("name", name());
		res.put("description", description());

		return res;
	}

	public void write(final JSONWriter writer) {
		writer.object();

		writer.key("type");
		writer.value("skill");

		writer.key("id");
		writer.value(id());

		writer.key("name");
		writer.value(name());

		writer.key("description");
		writer.value(description());

		writer.endObject();
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
		if (!(obj instanceof Skill)) return false;
		final Skill that = (Skill) obj;

		return id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public String toString() {
		final StringBuilder res = new StringBuilder();

		res.append("Skill#\"");
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

		res.append("}");

		return res.toString();
	}
}
