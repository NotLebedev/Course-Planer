package com.gmail.maximsmol.CoursePlanner.gui.cards;

import java.awt.Point;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import com.gmail.maximsmol.CoursePlanner.ConfigData;
import com.gmail.maximsmol.CoursePlanner.model.Course;
import com.gmail.maximsmol.CoursePlanner.model.ModelChangeEventListener;
import com.gmail.maximsmol.CoursePlanner.model.ModelChangeEvent;
import com.gmail.maximsmol.CoursePlanner.model.ModelWatcher;
import com.gmail.maximsmol.CoursePlanner.gui.GUIUtils;
import com.gmail.maximsmol.CoursePlanner.gui.winsys.BasicWindow;
import com.gmail.maximsmol.CoursePlanner.gui.winsys.WinsysEvent;
import com.gmail.maximsmol.CoursePlanner.gui.winsys.WinsysEventListener;
import com.gmail.maximsmol.CoursePlanner.gui.RootGlassPane;
import com.gmail.maximsmol.CoursePlanner.gui.ResizableLabel;

public class CourseCard extends BasicWindow implements ModelChangeEventListener {
	public static final long serialVersionUID = 1L;

	private static final int SKILL_SET_LIST_PANE_SIZE_FRACTION = ConfigData.integer("CourseCard_SkillSetListPane_width_part");
	private static final double INFO_PANE_SIZE_FRACTION = (SKILL_SET_LIST_PANE_SIZE_FRACTION-2)/((double) SKILL_SET_LIST_PANE_SIZE_FRACTION);
	private static final int DEFAULT_WIDTH = ConfigData.integer("CourseCard_width");
	private static final double HEIGHT_COEFFICIENT = ConfigData.floating("CourseCard_height_coefficient");

	protected SkillSetListPane inputPane;
	protected CourseInfoCardPart infoPane;
	protected SkillSetListPane outputPane;

	protected Course course;


	//
	// Class basics
	//

	//
	// Constructors
	public CourseCard() {
		setupFrom();
	}

	public CourseCard(final Course course) {
		this();
		setupFrom(course);

		sizeSelf();
		relayout();
	}

	//
	// `setupFrom`s
	@Override
	public void setupFrom() {
		super.setupFrom();

		ModelWatcher.addModelChangeEventListener(this);

		addWindowEventListener(new WinsysEventListener() {
			public void windowActionPerformed(WinsysEvent e) {
				if (e.type == WinsysEvent.Type.CLOSE_REQUEST) {
					inputPane.disconnectAll();
					outputPane.disconnectAll();

					close();
				}
			}
		});

		//
		// Cannot call `setRelativeSize` as it causes `relayout` to be called
		w = DEFAULT_WIDTH;
		h = (int) Math.round(w*HEIGHT_COEFFICIENT);

		inputPane = new SkillSetListPane();
		inputPane.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.black));

		infoPane = new CourseInfoCardPart();

		outputPane = new SkillSetListPane();
		outputPane.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.black));

		mainPane.add(inputPane);
		mainPane.add(infoPane);
		mainPane.add(outputPane);
	}

	public void setupFrom(final Course course) {
		super.setupFrom(course.name());

		this.course = course;

		inputPane.setupFrom(course, SkillSetListPane.OutputState.INPUT);
		infoPane.setupFrom(course.description());
		outputPane.setupFrom(course, SkillSetListPane.OutputState.OUTPUT);
	}


	public void modelChanged(final ModelChangeEvent e) {
		if (e.subject != ModelChangeEvent.Subject.COURSE) return;

		Course c = (Course) e.data;
		if (!c.equals(course)) return;

		setupFrom(c);

		sizeSelf();
		revalidate();
	}


	//
	// Relative component system integration
	//

	@Override
	public void sizeSelf() {
		super.sizeSelf();

		layoutTitleBar();

		inputPane.sizeSelf();
		inputPane.setRelativeSize(Math.max(inputPane.getRelativeWidth(), 20), inputPane.getRelativeHeight());

		outputPane.sizeSelf();
		outputPane.setRelativeSize(Math.max(outputPane.getRelativeWidth(), 20), outputPane.getRelativeHeight());

		int h = Math.max(inputPane.getRelativeHeight(), outputPane.getRelativeHeight());
		h = Math.max(h, infoPane.getRelativeHeight());
		mainPane.setRelativeSize(GUIUtils.horizontalInsetOf(mainPane)+inputPane.getRelativeWidth()+outputPane.getRelativeWidth()+200, GUIUtils.verticalInsetOf(mainPane)+h);

		setRelativeSize(GUIUtils.horizontalInsetOf(this)+mainPane.getRelativeWidth(), GUIUtils.verticalInsetOf(this)+titleBar.getRelativeHeight()+mainPane.getRelativeHeight());
	}

	@Override
	public final void relayout() {
		super.relayout();

		GUIUtils.putInTopleft(infoPane, mainPane);
		inputPane.relayout();

		infoPane.setRelativeSize(GUIUtils.insetedWidthOf(mainPane)-inputPane.getRelativeWidth()-outputPane.getRelativeWidth(), GUIUtils.insetedHeightOf(mainPane));
		infoPane.setRelativeLocation(GUIUtils.xAfter(inputPane), GUIUtils.topInsetOf(mainPane));
		infoPane.relayout();

		outputPane.setRelativeLocation(GUIUtils.xAfter(infoPane), GUIUtils.topInsetOf(mainPane));
		outputPane.relayout();
	}

	@Override
	public void resize(final double hK, final double vK) {
		super.resize(hK, vK);

		inputPane.resize(hK, vK);
		infoPane.resize(hK, vK);
		outputPane.resize(hK, vK);

		RootGlassPane.pane.repaint();
	}

	@Override
	public void relocate(final double hK, final double vK, final int xOffset, final int yOffset) {
		super.relocate(hK, vK, xOffset, yOffset);

		inputPane.relocate(hK, vK, 0, 0);
		infoPane.relocate(hK, vK, 0, 0);
		outputPane.relocate(hK, vK, 0, 0);

		RootGlassPane.pane.repaint();
	}
}
