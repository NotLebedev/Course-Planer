package com.gmail.maximsmol.CoursePlanner.model;

import java.util.EventObject;

public class ModelChangeEvent extends EventObject {
	public static final long serialVersionUID = 1L;

	public enum Subject {
		COURSE
	};

	public final Subject subject;
	public final Object data;

	public ModelChangeEvent(Object source, Subject subject, Object data) {
		super(source);

		this.subject = subject;
		this.data = data;
	}
}
