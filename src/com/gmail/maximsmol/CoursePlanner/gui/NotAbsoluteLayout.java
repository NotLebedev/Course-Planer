package com.gmail.maximsmol.CoursePlanner.gui;

import java.awt.LayoutManager;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Component;

import com.gmail.maximsmol.CoursePlanner.gui.relativeComponents.RelativelyLocated;
import com.gmail.maximsmol.CoursePlanner.gui.relativeComponents.RelativelyResizable;
import com.gmail.maximsmol.CoursePlanner.gui.relativeComponents.RelativelyBounded;

public class NotAbsoluteLayout implements LayoutManager {
	public static final long serialVersionUID = 1L;

	protected int baseW, baseH;
	protected int xOffset, yOffset;


	//
	// Class basics
	// 

	public NotAbsoluteLayout(final int w, final int h) {
		setBaseSize(w, h);
	}

	//
	// Getters
	public int getXOffset() {
		return xOffset;
	}

	public int getYOffset() {
		return yOffset;
	}

	//
	// Setters
	public void setBaseSize(final int w, final int h) {
		baseW = w;
		baseH = h;
	}

	public void setOffset(final int xOffset, final int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}

	public void addOffset(final int xDiff, final int yDiff) {
		xOffset += xDiff;
		yOffset += yDiff;
	}

	
	//
	// Coordinate converters
	//

	//
	// Stretch getters
	public double getHorizontalStretchFor(Container c) {
		return 1+0*((double) c.getWidth())/baseW;
	}

	public double getVerticalStretchFor(Container c) {
		return 1+0*((double) c.getHeight())/baseH;
	}

	//
	// Absolute from (0, 0) of the screen -> relative
	public int relativeXFromAbsolute(int x, Container c) {
		return (int) Math.round(x/getHorizontalStretchFor(c));
	}

	public int relativeYFromAbsolute(int y, Container c) {
		return (int) Math.round(y/getVerticalStretchFor(c));
	}

	//
	// Absolute on current view of the screen -> relative
	public int relativeFromOnScreenX(int x, Container c) {
		return relativeXFromAbsolute(x-getXOffset(), c);
	}

	public int relativeFromOnScreenY(int y, Container c) {
		return relativeYFromAbsolute(y-getYOffset(), c);
	}


	//
	// LayoutManager as superclass
	//

	@Override
	public void layoutContainer(final Container parent) {
		final double horizontalStretch = getHorizontalStretchFor(parent);
		final double verticalStretch   = getVerticalStretchFor(parent);

		for (final Component resizeCandidate : parent.getComponents()) {
			if (resizeCandidate instanceof RelativelyResizable) {
				final RelativelyResizable component = ((RelativelyResizable) resizeCandidate);

				component.resize(1, 1);
			}
			if (resizeCandidate instanceof RelativelyLocated) {
				final RelativelyLocated component = ((RelativelyLocated) resizeCandidate);

				component.relocate(1, 1, xOffset, yOffset);
			}
		}
	}

	@Override
	public Dimension minimumLayoutSize(final Container parent) {
		int w = 0, h = 0;

		for (final Component candidate : parent.getComponents()) {
			if (!(candidate instanceof RelativelyBounded)) continue;

			final RelativelyBounded component = ((RelativelyBounded) candidate);
			
			w = Math.max(w, component.getRelativeX() + component.getRelativeWidth());
			h = Math.max(h, component.getRelativeY() + component.getRelativeHeight());
		}

		final double horizontalStretch = getHorizontalStretchFor(parent);
		final double verticalStretch   = getVerticalStretchFor(parent);

		final Dimension res = new Dimension();
		res.setSize(w*horizontalStretch, h*verticalStretch);
		return res;
	}

	@Override
	public Dimension preferredLayoutSize(final Container parent) {
		return minimumLayoutSize(parent);
	}

	@Override
	public void addLayoutComponent(final String name, final Component comp) { /* Required override */ }
	@Override
	public void removeLayoutComponent(final Component comp) { /* Required override */ }
}

