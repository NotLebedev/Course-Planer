package com.gmail.maximsmol.CoursePlanner.gui.cards;

import java.util.Arrays;
import java.util.ArrayList;
import java.lang.ref.WeakReference;
import javax.swing.BorderFactory;
import java.awt.Point;
import java.awt.Color;
import java.awt.Dimension;

import com.gmail.maximsmol.CoursePlanner.model.ModelWatcher;
import com.gmail.maximsmol.CoursePlanner.model.Course;
import com.gmail.maximsmol.CoursePlanner.model.SkillSet;

public class SkillSetListPane extends DropAcceptingSkillSetListPane<SkillSet, SkillSetPane> {
	public static final long serialVersionUID = 1L;

	protected Course course;
	protected OutputState isOutput;

	public enum OutputState {
		INPUT,
		OUTPUT
	}

	public SkillSetListPane() {
		super();
		setupFrom();
	}

	public SkillSetListPane(final Course course, final OutputState isOutput) {
		this();
		setupFrom(course, isOutput);
	}

	public void setupFrom() {
		super.setupFrom();
	}

	public void setupFrom(final Course course, final OutputState isOutput) {
		this.course = course;
		this.isOutput = isOutput;

		super.setupFrom(isOutput == OutputState.INPUT ? course.input : course.output);
	}

	public void disconnectAll() {
		for (SkillSetPane pane : panes.components()) {
			pane.disconnect();
		}
	}

	//
	// Subclass work
	@Override
	protected void redecorateAccordingToNumber(final SkillSetPane pane, final int i) {
		if (isOutput == OutputState.OUTPUT)
			pane.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.black));
		else
			pane.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 0, Color.black));
	}

	@Override
	protected SkillSetPane getSkillPaneFor(final SkillSet s) {
		return new SkillSetPane(s, isOutput, course);
	}

	//
	// Drag'n'drop handling
	public void dropped(final DraggedSkillSetPane pane) {
		if (data.contains(pane.data)) return;

		super.dropped(pane);

		if (isOutput == OutputState.OUTPUT)
			Course.update(course.output, pane.data());
		else
			Course.update(course.input, pane.data());

		ModelWatcher.courseUpdated(course);
	}
}
