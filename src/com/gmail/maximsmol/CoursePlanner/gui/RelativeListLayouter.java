package com.gmail.maximsmol.CoursePlanner.gui;

import java.util.ArrayList;

import javax.swing.JComponent;
import java.awt.Color;

import com.gmail.maximsmol.CoursePlanner.gui.cards.SkillSetPane;
import com.gmail.maximsmol.CoursePlanner.gui.relativeComponents.RelativelyResizable;
import com.gmail.maximsmol.CoursePlanner.gui.relativeComponents.RelativelyLayouted;
import com.gmail.maximsmol.CoursePlanner.gui.relativeComponents.RelativelyLocated;
import com.gmail.maximsmol.CoursePlanner.gui.relativeComponents.CanSizeSelf;

public class RelativeListLayouter<T extends JComponent & RelativelyLocated & RelativelyResizable> extends ResizablePanel {
	public static final long serialVersionUID = 1L;

	protected ArrayList<T> components = new ArrayList<T>();

	public RelativeListLayouter() {
		super();
	}

	//
	// Container operations
	public void add(T c) {
		components.add(c);
		super.add(c);
	}

	public void remove(T c) {
		components.remove(c);
		super.remove(c);
	}

	public void clear() {
		components.clear();
		super.removeAll();
	}

	public ArrayList<T> components() {
		return components;
	}


	//
	// Relative component system integration
	//

	@Override
	public void sizeSelf() {
		super.sizeSelf();

		int w = 0, h = 0;
		for (final T c : components) {
			if (!c.isVisible()) continue;

			// if (c instanceof CanSizeSelf)
			// 	((CanSizeSelf) c).sizeSelf();

			w = Math.max(w, c.getRelativeWidth());
			h += c.getRelativeHeight();
		}

		setRelativeSize(GUIUtils.horizontalInsetOf(this)+w, GUIUtils.verticalInsetOf(this)+h);
	}

	public void relayout() {
		super.relayout();

		int y = GUIUtils.topInsetOf(this);
		for (final T c : components) {
			c.setRelativeSize(getRelativeWidth(), c.getRelativeHeight());
			c.setRelativeLocation(GUIUtils.leftInsetOf(this), y);

			y += c.getRelativeHeight();
		}

	}

	@Override
	public void resize(final double hK, final double vK) {
		super.resize(hK, vK);

		for (final T c : components) {
			c.resize(hK, vK);
		}
	}

	@Override
	public void relocate(final double hK, final double vK, final int xOffset, final int yOffset) {
		super.relocate(hK, vK, xOffset, yOffset);

		for (final T c : components) {
			c.relocate(hK, vK, 0, 0);
		}
	}
}
