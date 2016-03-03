package com.gmail.maximsmol.CoursePlanner.model;

import java.util.EventListener;

public interface ModelChangeEventListener extends EventListener {
	void modelChanged(final ModelChangeEvent e);
}
