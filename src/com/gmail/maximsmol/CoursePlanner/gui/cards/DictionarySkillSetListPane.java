package com.gmail.maximsmol.CoursePlanner.gui.cards;

import java.util.ArrayList;

import java.awt.Color;
import javax.swing.BorderFactory;

import com.gmail.maximsmol.CoursePlanner.model.SkillSet;

public class DictionarySkillSetListPane extends BasicDataListPane<SkillSet, DictionarySkillSetPane> {
	public static final long serialVersionUID = 1L;


	//
	// Class basics
	//

	//
	// Constructors
	public DictionarySkillSetListPane() {
		super();
		setupFrom();
	}

	public DictionarySkillSetListPane(final ArrayList<SkillSet> data) {
		this();
		setupFrom(data);
	}

	//
	// `setupFrom`s
	public void setupFrom() {
		super.setupFrom();
	}

	public void setupFrom(final ArrayList<SkillSet> data) {
		super.setupFrom(data);

		sizeSelf();
		relayout();
	}


	//
	// Subclass routines implementation
	//

	protected void redecorateAccordingToNumber(final DictionarySkillSetPane pane, final int i) {
		pane.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
	}

	protected DictionarySkillSetPane getSkillPaneFor(final SkillSet s) {
		return new DictionarySkillSetPane(s, this);
	}
}
