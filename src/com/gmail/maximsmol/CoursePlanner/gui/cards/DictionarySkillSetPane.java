package com.gmail.maximsmol.CoursePlanner.gui.cards;

import java.awt.Color;
import java.awt.Point;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.SwingUtilities;

import com.gmail.maximsmol.CoursePlanner.ConfigData;
import com.gmail.maximsmol.CoursePlanner.gui.GUIUtils;
import com.gmail.maximsmol.CoursePlanner.gui.ResizableLabel;
import com.gmail.maximsmol.CoursePlanner.model.SkillSet;
import com.gmail.maximsmol.CoursePlanner.gui.Window;

public class DictionarySkillSetPane extends BasicDataPane<SkillSet> {
	public static final long serialVersionUID = 1L;

	protected static final int LABEL_SPACE_RATIO = ConfigData.integer("DictionarySkillSetPane_description_width_part");

	protected DictionarySkillSetListPane parent;
	protected ResizableLabel description;
	protected DraggedSkillSetPane dragPane;


	//
	// Class basics
	//

	//
	// Constructors
	public DictionarySkillSetPane() {
		super();
		setupFrom();
	}

	public DictionarySkillSetPane(final SkillSet data, final DictionarySkillSetListPane parent) {
		this();
		setupFrom(data, parent);
	}

	//
	// `setupFrom`s
	public void setupFrom() {
		super.setupFrom();

		label.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.black));
		GUIUtils.addInsets(label, 2, 2, 2, 2);

		description = new ResizableLabel("", JLabel.CENTER);
		GUIUtils.addInsets(description, 2, 2, 2, 2);
		add(description);

		dragPane = new DraggedSkillSetPane();
		Window.win.getContentPane().add(dragPane);
		Window.win.getContentPane().setComponentZOrder(dragPane, 0);
	}

	public void setupFrom(final SkillSet data, final DictionarySkillSetListPane parent) {
		super.setupFrom(data);

		this.parent = parent;

		description.setText(data.description());
		dragPane.setupFrom(this);

		sizeSelf();
		relayout();
	}


	//
	// Relative component system integration
	//

	public void putDragPaneInPlace() {
		if (dragPane.isDragged()) return;

		if (!label.isShowing()) return;
		Point loc = SwingUtilities.convertPoint(label, label.getLocation(), Window.win.getContentPane());

		dragPane.setLocation(loc.x, loc.y);
	}

	@Override
	public final void sizeSelf() {
		super.sizeSelf();

		description.sizeSelf();

		setRelativeSize(GUIUtils.horizontalInsetOf(this)+label.getRelativeWidth()+description.getRelativeWidth(), GUIUtils.verticalInsetOf(this) + Math.max(label.getRelativeHeight(), description.getRelativeHeight()) );
	}

	@Override
	public final void relayout() {
		super.relayout();

		label.setRelativeSize(GUIUtils.insetedWidthOf(this)/LABEL_SPACE_RATIO, getRelativeHeight());
		GUIUtils.putInTopleft(label, this);

		description.setRelativeSize(GUIUtils.insetedWidthOf(this)-label.getRelativeWidth(), getRelativeHeight());
		GUIUtils.putInTopright(description, this);

		dragPane.setRelativeSize(label.getRelativeWidth(), label.getRelativeHeight());
		putDragPaneInPlace();
	}

	@Override
	public void relocate(final double hK, final double vK, final int xOffset, final int yOffset) {
		super.relocate(hK, vK, xOffset, yOffset);

		putDragPaneInPlace();

		description.relocate(hK, vK, 0, 0);
	}

	@Override
	public void resize(final double hK, final double vK) {
		super.resize(hK, vK);

		dragPane.resize(hK, vK);
		description.resize(hK, vK);
	}
}
