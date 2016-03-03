package com.gmail.maximsmol.CoursePlanner.gui.cards;

import java.awt.Color;
import java.awt.Point;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.gmail.maximsmol.CoursePlanner.gui.GUIUtils;
import com.gmail.maximsmol.CoursePlanner.gui.ResizablePanel;
import com.gmail.maximsmol.CoursePlanner.gui.ResizableTextArea;
import com.gmail.maximsmol.CoursePlanner.gui.ResizableScrollPane;
import com.gmail.maximsmol.CoursePlanner.gui.relativeComponents.RelativeContainer;

public class CourseInfoCardPart extends ResizablePanel {
	public static final long serialVersionUID = 1L;

	protected ResizableScrollPane scrollPane;
	protected ResizableTextArea description;

	protected static final int HORIZONTAL_PADDING = 3;
	protected static final int VERTICAL_PADDING = 2;


	//
	// Class basics
	//

	//
	// Constructors
	public CourseInfoCardPart() {
		super();
		setupFrom();
	}

	public CourseInfoCardPart(final String text) {
		this();
		setupFrom(text);
	}

	//
	// `setupFrom`s
	public void setupFrom() {
		GUIUtils.decorateAsBasicPane(this);

		description = new ResizableTextArea();
		description.setLineWrap(true);
		description.setWrapStyleWord(true);

		scrollPane = new ResizableScrollPane(description);
		scrollPane.setBorder(new EmptyBorder(HORIZONTAL_PADDING, VERTICAL_PADDING, HORIZONTAL_PADDING, VERTICAL_PADDING));
		add(scrollPane);
	}

	public void setupFrom(String text) {
		description.setText(text);

		relayout();
	}

	public void useWidth(int w) {
		description.setRelativeSize(w, 0);
		description.useCurrentWidth();
	}


	//
	// Relative component system integration
	//

	@Override
	public void sizeSelf() {
		super.sizeSelf();

		description.sizeSelf();

		scrollPane.setRelativeSize(GUIUtils.horizontalInsetOf(scrollPane)+description.getRelativeWidth(), GUIUtils.verticalInsetOf(scrollPane)+description.getRelativeHeight());

		setRelativeSize(GUIUtils.horizontalInsetOf(this)+scrollPane.getRelativeWidth(), GUIUtils.verticalInsetOf(this)+scrollPane.getRelativeHeight());
	}

	@Override
	public void relayout() {
		super.relayout();

		GUIUtils.fillWith(scrollPane, this);
		GUIUtils.putInTopleft(scrollPane, this);

		GUIUtils.fillWith(description, scrollPane);
		GUIUtils.putInTopleft(description, scrollPane);

		revalidate();
	}

	public void resize(final double hK, final double vK) {
		super.resize(hK, vK);

		scrollPane.resize(hK, vK);
	}

	public void relocate(final double hK, final double vK, final int xOffset, final int yOffset) {
		super.relocate(hK, vK, xOffset, yOffset);

		scrollPane.relocate(hK, vK, 0, 0);
	}
}
