package com.gmail.maximsmol.CoursePlanner.model;

import java.util.Locale;

public enum SkillLevel {
	NONE(0),

	BEGINNER(1),
	INTERMEDIATE(2),
	ADVANCED(3);

	public final int value;

	SkillLevel(final int value) {
		this.value = value;
	}

	public static SkillLevel fromString(final String str) {
		return SkillLevel.valueOf(str.toUpperCase(Locale.ENGLISH));
	}
}
