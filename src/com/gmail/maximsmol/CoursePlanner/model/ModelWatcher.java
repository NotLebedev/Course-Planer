package com.gmail.maximsmol.CoursePlanner.model;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.event.EventListenerList;

public final class ModelWatcher {
	private ModelWatcher() {}

	public static final EventListenerList listeners = new EventListenerList();


	public static void courseUpdated(Course c) {
		fireModelChangeEvent(new ModelChangeEvent("courseUpdated@ModelWatcher", ModelChangeEvent.Subject.COURSE, c));
	}


	//
	// ModelChangeEvent handling
	//

	public static void addModelChangeEventListener(ModelChangeEventListener listener) {
		listeners.add(ModelChangeEventListener.class, listener);
	}

	public static void removeModelChangeEventListener(ModelChangeEventListener listener) {
		listeners.remove(ModelChangeEventListener.class, listener);
	}

	protected static void fireModelChangeEvent(ModelChangeEvent e) {
		Object[] arr = listeners.getListenerList();

		for (int i = 0; i < arr.length; i += 2) {
			if (arr[i] != ModelChangeEventListener.class) continue;

			((ModelChangeEventListener) arr[i+1]).modelChanged(e);
		}
	}
}
