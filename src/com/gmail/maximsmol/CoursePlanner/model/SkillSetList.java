package com.gmail.maximsmol.CoursePlanner.model;

import java.util.ArrayList;

import org.json.JSONWriter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class SkillSetList extends ArrayList<SkillSet> {
	public static final long serialVersionUID = 1L;

	protected Course parent;

	public SkillSetList(final Course parent) {
		super();

		this.parent = parent;
	}

	public Course parent() {
		return parent;
	}

	//
	// Database IO
	public void read(final JSONArray input) {
		try {
			for (int i = 0; i < input.length(); i++) {
				JSONObject obj = input.getJSONObject(i);

				try {
					final String id = obj.getString("id");

					if (SkillSet.skillSets.containsKey(id))
						add(SkillSet.skillSets.get(id));
					else if (Skill.skills.containsKey(id))
					{
						final Skill skill = Skill.skills.get(id);
						final String level = obj.getString("level");

						final SkillSet res = new SkillSet(id+"-"+level, skill.name(), skill.description());
						res.skills.add( new LeveledSkill(skill, SkillLevel.fromString(level)) );

						add(res);
					}
					else throw new InvalidModelException("No such SkillSet/Skill - \""+id+"\"");
				}
				catch (RuntimeException e) {
					throw new InvalidModelException("SkillSet id in Course specification invalid", e);
				}
			}
		} catch (JSONException e) {
			throw new InvalidModelException("SkillSet list couldn't be read", e);
		}
	}

	public JSONArray toJSON() {
		final JSONArray res = new JSONArray();

		for (final SkillSet s : this)
			res.put(s.toIdJSON());

		return res;
	}

	public void write(final JSONWriter writer) {
		writer.array();
		for (final SkillSet set : this) {
			set.writeId(writer);
		}
		writer.endArray();
	}

	//
	// Object as superclass
	@Override
	public boolean equals(final Object obj) {
		if (!(obj instanceof SkillSetList)) return false;
		final SkillSetList that = (SkillSetList) obj;

		return super.equals(that) && that.parent.equals(parent);
	}

	@Override
	public int hashCode() {
		return super.hashCode() + parent.hashCode();
	}

	@Override
	public String toString() {
		final StringBuilder res = new StringBuilder();

		res.append("SkillSetList@\"");
		res.append(parent().id());
		res.append("\"");

		res.append("{");
			res.append("skillsets: ");
			res.append("[");
				for (final SkillSet s : this) {
					res.append(s.toString());
					res.append(",");
				}
			res.append("]");

		res.append("}");

		return res.toString();
	}
}
