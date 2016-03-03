package com.gmail.maximsmol.CoursePlanner.model;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONWriter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import com.gmail.maximsmol.CoursePlanner.model.Database;

public class SkillSet implements DescribedElement {
	protected final String id;
	protected String name;
	protected String description;

	public final ArrayList<LeveledSkill> skills = new ArrayList<LeveledSkill>();
	public static final HashMap<String, SkillSet> skillSets = new HashMap<String, SkillSet>();

	public SkillSet(final String id, final String name, final String description) {
		this.id = id;
		this.name = name;
		this.description = description;

		skillSets.put(id, this);
	}

	//
	// Database IO
	public static SkillSet read(final JSONObject obj) {
		try {
			if ( !"skillset".equals(obj.getString("type")) )
				throw new InvalidModelException("Type expected to be \"skillset\"");
		}
		catch (JSONException e) {
			throw new InvalidModelException("Type field not found", e);
		}

		try {
			final String id = obj.getString("id");

			try {
				if (skillSets.containsKey(id))
					return skillSets.get(id);
				else if (Database.databaseFrozen)
					throw new InvalidModelException("Trying to introduce a new SkillSet into a frozen database");

				String name = obj.getString("name");

				try {
					final SkillSet res = new SkillSet(id, name, obj.getString("description"));

					try {
						final JSONArray skillsData = obj.getJSONArray("skills");
						for (int i = 0; i < skillsData.length(); i++) {
							res.skills.add( LeveledSkill.read(skillsData.getJSONObject(i)) );
						}

						return res;
					}
					catch (JSONException e) {
						throw new InvalidModelException("Skills array not found", e);
					}
					catch (InvalidModelException e) {
						throw new InvalidModelException("A LeveledSkill object is invalid");
					}
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

		res.put("type", "skillset");
		res.put("id", id());
		res.put("name", name());
		res.put("description", description());

		for (final LeveledSkill skill : skills) {
			res.append("skills", skill.toJSON());
		}

		return res;
	}

	public JSONObject toIdJSON() {
		final JSONObject res = new JSONObject();

		res.put("id", id());
		res.put("level", level().toString());

		return res;
	}

	public void write(final JSONWriter writer) {
		writer.object();

		writer.key("type");
		writer.value("skillset");

		writer.key("id");
		writer.value(id());

		writer.key("name");
		writer.value(name());

		writer.key("description");
		writer.value(description());

		writer.key("skills");
		writer.array();
		for (final LeveledSkill skill : skills) {
			skill.write(writer);
		}
		writer.endArray();

		writer.endObject();
	}

	public void writeId(final JSONWriter writer) {
		writer.object();

		writer.key("id");
		writer.value(id());

		writer.key("level");
		writer.value(level().toString());

		writer.endObject();
	}

	//
	// Analysis
	public SkillLevel level() {
		if (skills.size() == 1) return skills.get(0).level();
		return SkillLevel.NONE;
	}

	public boolean canPartiallyFulfill(final SkillSet that) {
		for (final LeveledSkill skill : that.skills()) {
			for (final LeveledSkill mySkill : skills) {
				if (mySkill.skill() == skill.skill()) return true;
			}
		}

		return false;
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

	public ArrayList<LeveledSkill> skills() {
		return skills;
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
		if (!(obj instanceof SkillSet)) return false;
		final SkillSet that = (SkillSet) obj;

		return id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public String toString() {
		final StringBuilder res = new StringBuilder();

		res.append("SkillSet#\"");
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

			res.append("skills: ");
			res.append("[");
				for (final LeveledSkill skill : skills) {
					res.append(skill.toString());
					res.append(",");
				}
			res.append("]");

		res.append("}");

		return res.toString();
	}
}
