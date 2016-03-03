package com.gmail.maximsmol.CoursePlanner.gui.cards;

import java.awt.Color;
import java.awt.Point;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.BorderFactory;

import java.util.ArrayList;

import com.gmail.maximsmol.CoursePlanner.model.DescribedElement;
import com.gmail.maximsmol.CoursePlanner.gui.GUIUtils;
import com.gmail.maximsmol.CoursePlanner.gui.RelativeListLayouter;
import com.gmail.maximsmol.CoursePlanner.gui.ResizablePanel;

public abstract class BasicDataListPane<D extends DescribedElement, T extends BasicDataPane<D>> extends ResizablePanel {
	public static final long serialVersionUID = 1L;

	protected ArrayList<D> data;
	protected RelativeListLayouter<T> panes;


	//
	// Class basics
	//

	//
	// Constructors
	public BasicDataListPane() {
		setupFrom();
	}

	public BasicDataListPane(final ArrayList<D> data) {
		this();
		setupFrom(data);
	}

	//
	// `setupFrom`s
	public void setupFrom() {
		super.setupFrom();

		panes = new RelativeListLayouter<T>();
		add(panes);
	}

	public void setupFrom(final ArrayList<D> data) {
		this.data = data;

		panes.clear();
		panes.components().ensureCapacity(data.size());

		for (D d : data) {
			T pane = getSkillPaneFor(d);
			panes.add(pane);
		}
		panes.components().trimToSize();

		sizeSelf();
		relayout();
	}

	//
	// Getters
	public ArrayList<D> data() {
		return data;
	}

	public RelativeListLayouter<T> panes() {
		return panes;
	}


	//
	// Subclasses' routines prototypes
	//

	protected void redecorateAccordingToNumber(final T pane, final int i) {
		// Do nothing
	}

	protected abstract T getSkillPaneFor(D d); // Cannot create a generic `T` instance myself


	//
	// Relative component system integration
	//

	public void sizeSelf() {
		super.sizeSelf();

		//
		// `panes`
		int i = 0;
		for (final T pane : panes.components()) {
			if (!pane.isVisible()) continue;

			redecorateAccordingToNumber(pane, i++);
		}

		panes.sizeSelf();

		setRelativeSize(GUIUtils.horizontalInsetOf(this)+panes.getRelativeWidth(), GUIUtils.verticalInsetOf(this)+panes.getRelativeHeight());
	}

	@Override
	public void relayout() {
		super.relayout();

		//
		// `panes`
		int i = 0;
		for (final T pane : panes.components()) {
			if (!pane.isVisible()) continue;

			redecorateAccordingToNumber(pane, i++);
		}

		GUIUtils.fillWith(panes, this);
		GUIUtils.putInTopleft(panes, this);
		panes.relayout();
	}

	@Override
	public void resize(final double hK, final double vK) {
		super.resize(hK, vK);

		panes.resize(hK, vK);
		revalidate();
	}

	@Override
	public void relocate(final double hK, final double vK, final int xOffset, final int yOffset) {
		super.relocate(hK, vK, xOffset, yOffset);

		panes.relocate(hK, vK, 0, 0);
	}
}
