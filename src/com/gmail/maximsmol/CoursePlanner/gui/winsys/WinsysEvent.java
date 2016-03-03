package com.gmail.maximsmol.CoursePlanner.gui.winsys;

import java.util.EventObject;

public class WinsysEvent extends EventObject {
	public static final long serialVersionUID = 1L;
	
	public enum CloseCause {
		CLOSE_BUTTON,
		DIALOG_CONFIRM_BUTTON
	};

	public enum Type {
		CLOSE_REQUEST,
		CLOSED,
		DIALOG_RESULT
	};

	public final Type type;
	public final Object data;

	public WinsysEvent(Object source, Type type, Object data) {
		super(source);

		this.type = type;
		this.data = data;
	}
}