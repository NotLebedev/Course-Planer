package com.gmail.maximsmol.CoursePlanner.gui;

import javax.swing.JScrollPane;
import java.awt.Component;
import java.awt.Point;
import java.awt.Dimension;

import com.gmail.maximsmol.CoursePlanner.gui.relativeComponents.RelativelyLocated;
import com.gmail.maximsmol.CoursePlanner.gui.relativeComponents.RelativelyResizable;
import com.gmail.maximsmol.CoursePlanner.gui.relativeComponents.RelativelyBounded;

public class ResizableScrollPane extends JScrollPane implements RelativelyBounded {
	public static final long serialVersionUID = 1L;

	protected int x, y;
	protected int w, h;

	public ResizableScrollPane() {
		super();
	}

	public ResizableScrollPane(final Component c) {
		super(c);
	}

	//
	// RelativelyResizable as superclass
	@Override
	public void setRelativeSize(final int w, final int h) {
		this.w = w;
		this.h = h;
	}

	@Override
	public int getRelativeWidth() {
		return w;
	}

	@Override
	public int getRelativeHeight() {
		return h;
	}

	@Override
	public void resize(final double hK, final double vK) {
		final Dimension size = new Dimension();
		
		size.setSize(w*hK, h*vK);
		setSize(size);

		for (final Component c : getComponents()) {
			if (!(c instanceof RelativelyResizable)) continue;

			((RelativelyResizable) c).resize(hK, vK);
		}
	}

	//
	// RelativelyLocated as superclass
	@Override
	public void setRelativeLocation(final int x, final int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public int getRelativeX() {
		return x;
	}

	@Override
	public int getRelativeY() {
		return y;
	}

	@Override
	public void relocate(final double hK, final double vK, final int xOffset, final int yOffset) {
		final Point loc = new Point();

		loc.setLocation(xOffset + x*hK, yOffset + y*vK);
		setLocation(loc);

		for (final Component c : getComponents()) {
			if (!(c instanceof RelativelyLocated)) continue;

			((RelativelyLocated) c).relocate(hK, vK, xOffset, yOffset);
		}
	}
}
