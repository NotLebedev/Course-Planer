package com.gmail.maximsmol.CoursePlanner.gui;

import java.awt.Point;
import java.awt.Dimension;
import javax.swing.JTextField;

import com.gmail.maximsmol.CoursePlanner.gui.relativeComponents.RelativelyBounded;

public class ResizableTextField extends JTextField implements RelativelyBounded {
	public static final long serialVersionUID = 1L;

	protected int x, y;
	protected int w, h;

	public ResizableTextField() {
		super();
	}

	public ResizableTextField(final String text) {
		super(text);
	}

	public void sizeSelf() {
		w = getColumnWidth()*getColumns()+getInsets().left+getInsets().right;
		h = getFont().getSize()+getInsets().top+getInsets().bottom;
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
