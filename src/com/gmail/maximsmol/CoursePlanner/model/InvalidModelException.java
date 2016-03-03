package com.gmail.maximsmol.CoursePlanner.model;

public class InvalidModelException extends RuntimeException {
	public static final long serialVersionUID = 1L;

	public InvalidModelException(final String reason) {
		super(reason);
	}

	public InvalidModelException(final String reason, final Throwable cause) {
		super(reason, cause);
	}
}
