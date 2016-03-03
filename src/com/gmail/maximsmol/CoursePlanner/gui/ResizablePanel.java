package com.gmail.maximsmol.CoursePlanner.gui;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Point;

import com.gmail.maximsmol.CoursePlanner.gui.GUIUtils;
import com.gmail.maximsmol.CoursePlanner.gui.relativeComponents.FullyRelativeComponent;

public class ResizablePanel extends JPanel implements FullyRelativeComponent {
	public static final long serialVersionUID = 1L;

	protected int w, h;
	protected int x, y;

	//
	// Class basics
	public ResizablePanel() {
		super();
		GUIUtils.decorateAsBasicPane(this);
	}

	public void setupFrom() {
		// Do nothing
	}

	//
	// Implement methods expected by subclasses
	@Override
	public void sizeSelf() {
		// Do nothing
	}

	@Override
	public void relayout() {
		// Do nothing
	}

	//
	// RelativelyResizable as superclass
	@Override
	public int getRelativeWidth() {
		return w;
	}

	@Override
	public int getRelativeHeight() {
		return h;
	}

	@Override
	public void setRelativeSize(final int w, final int h) {
		this.w = w;
		this.h = h;

		relayout();
	}

	@Override
	public void resize(final double hK, final double vK) {
		final Dimension size = new Dimension();
		size.setSize(w*hK, h*vK);
		setSize(size);
	}

	//
	// RelativelyLocated as superclass
	@Override
	public int getRelativeX() {
		return x;
	}

	@Override
	public int getRelativeY() {
		return y;
	}

	@Override
	public void setRelativeLocation(final int x, final int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public void relocate(final double hK, final double vK, final int xOffset, final int yOffset) {
		final Point loc = new Point();
		loc.setLocation(xOffset + x*hK, yOffset + y*vK);
		setLocation(loc);
	}
}
