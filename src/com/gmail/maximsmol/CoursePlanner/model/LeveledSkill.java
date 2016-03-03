package com.gmail.maximsmol.CoursePlanner.model;

import org.json.JSONWriter;
import org.json.JSONObject;
import org.json.JSONException;

public class LeveledSkill implements DescribedElement {
	protected final Skill skill;
	protected SkillLevel level;

	public LeveledSkill(final Skill skill, final SkillLevel level) {
		this.skill = skill;
		this.level = level;
	}

	//
	// Database IO
	public static LeveledSkill read(final JSONObject obj) {
		try {
			String id = obj.getString("id");

			if (!Skill.skills.containsKey(id))
				throw new InvalidModelException("No such Skill: " + id);

			try {
				return new LeveledSkill(Skill.skills.get(id), SkillLevel.fromString(obj.getString("level")) );
			}
			catch (JSONException e) {
				throw new InvalidModelException("Level field not found", e);
			}
		}
		catch (JSONException e) {
			throw new InvalidModelException("Id not found", e);
		}
	}

	public JSONObject toJSON() {
		final JSONObject res = new JSONObject();

		res.put("id", id());
		res.put("level", level().toString());

		return res;
	}

	public void write(final JSONWriter writer) {
		writer.object();

		writer.key("id");
		writer.value(id());

		writer.key("level");
		writer.value(level().toString());

		writer.endObject();
	}

	//
	// Getters
	@Override
	public String id() {
		return skill.id();
	}

	@Override
	public String name() {
		return skill.name();
	}

	@Override
	public String description() {
		return skill.description();
	}

	public SkillLevel level() {
		return level;
	}

	public Skill skill() {
		return skill;
	}

	//
	// Setters
	@Override
	public void changeName(final String name) {
		skill.changeName(name);
	}

	@Override
	public void changeDescription(final String description) {
		skill.changeDescription(description);
	}

	public void changeLevel(final SkillLevel level) {
		this.level = level;
	}

	//
	// Object as superclass
	@Override
	public boolean equals(final Object obj) {
		if (!(obj instanceof LeveledSkill)) return false;
		final LeveledSkill that = (LeveledSkill) obj;

		return skill.equals(that.skill) && level.equals(that.level);
	}

	@Override
	public int hashCode() {
		return skill.hashCode() + level.hashCode();
	}

	@Override
	public String toString() {
		final StringBuilder res = new StringBuilder();
		res.append("LeveledSkill");
		res.append("{");

			res.append("level: ");
			res.append(level.toString());

			res.append(",\n");

			res.append(skill.toString());

		res.append("}");

		return res.toString();
	}
}
