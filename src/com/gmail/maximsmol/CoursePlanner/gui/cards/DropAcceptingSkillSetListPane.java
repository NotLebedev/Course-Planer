package com.gmail.maximsmol.CoursePlanner.gui.cards;

import java.util.ArrayList;
import java.lang.ref.WeakReference;
import javax.swing.BorderFactory;
import java.awt.Point;
import java.awt.Color;
import java.awt.Dimension;

import com.gmail.maximsmol.CoursePlanner.model.DescribedElement;
import com.gmail.maximsmol.CoursePlanner.model.Course;
import com.gmail.maximsmol.CoursePlanner.model.SkillSet;

public abstract class DropAcceptingSkillSetListPane<D extends DescribedElement, T extends BasicDataPane<D>> extends BasicDataListPane<D, T> {
	public static final long serialVersionUID = 1L;

	@SuppressWarnings("rawtypes")
	protected static ArrayList<WeakReference<DropAcceptingSkillSetListPane>> instances = new ArrayList<WeakReference<DropAcceptingSkillSetListPane>>();


	//
	// Class basics
	//

	//
	// Constructors
	public DropAcceptingSkillSetListPane() {
		super();
		setupFrom();
	}

	public DropAcceptingSkillSetListPane(final ArrayList<D> data) {
		this();
		setupFrom(data);
	}

	//
	// `setupFrom`s
	@SuppressWarnings("rawtypes")
	public void setupFrom() {
		super.setupFrom();

		instances.add(new WeakReference<DropAcceptingSkillSetListPane>(this));
	}

	public void setupFrom(final ArrayList<D> data) {
		super.setupFrom(data);
	}


	//
	// Drag'n'drop handling
	//

	public void dropped(final DraggedSkillSetPane pane) {
		if (data.contains(pane.data)) return;

		data.add((D) pane.data);

		final T newPane = getSkillPaneFor((D) pane.data);
		panes.add(newPane);
		add(newPane);
	}

	@SuppressWarnings("rawtypes")
	public static void droppedSomewhere(final DraggedSkillSetPane pane) {
		for (final WeakReference<DropAcceptingSkillSetListPane> ref : instances) {
			final DropAcceptingSkillSetListPane one = ref.get();

			if (one == null) {
				instances.remove(ref);
				continue;
			}

			if (!one.isDropTarget(pane)) continue;

			one.dropped(pane);
			return;
		}
	}

	protected boolean isDropTarget(final DraggedSkillSetPane pane) {
		final Point loc = getLocationOnScreen();
		final Dimension size = getSize();

		final Point dropLoc = pane.dropLocation();

		return loc.x            < dropLoc.x &&
		       loc.x+size.width > dropLoc.x &&

		       loc.y             < dropLoc.y &&
		       loc.y+size.height > dropLoc.y;
	}
}
