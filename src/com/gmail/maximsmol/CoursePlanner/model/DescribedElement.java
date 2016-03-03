package com.gmail.maximsmol.CoursePlanner.model;

public interface DescribedElement {
	String id();
	String name();
	String description();

	void changeName(final String name);
	void changeDescription(final String description);
}
