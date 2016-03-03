package com.gmail.maximsmol.CoursePlanner.gui;

import java.awt.Point;
import java.awt.Dimension;
import javax.swing.JTextArea;

import com.gmail.maximsmol.CoursePlanner.gui.relativeComponents.RelativelyBounded;

public class ResizableTextArea extends JTextArea implements RelativelyBounded {
	public static final long serialVersionUID = 1L;

	protected int x, y;
	protected int w, h;

	public ResizableTextArea() {
		super();
	}

	public ResizableTextArea(final String text) {
		super(text);
	}

	public void useCurrentWidth() {
		setColumns(w/getColumnWidth());
		setRows(0);
	}

	public void sizeSelf() {
		// Dimension size = getPreferredSize();

		// setRelativeSize(GUIUtils.horizontalInsetOf(this)+Window.relativeXFromAbsolute(size.width), GUIUtils.verticalInsetOf(this)+Window.relativeYFromAbsolute(size.height) );
		setRelativeSize(GUIUtils.horizontalInsetOf(this)+Window.relativeXFromAbsolute(getColumns()*getColumnWidth()), GUIUtils.verticalInsetOf(this)+Window.relativeYFromAbsolute(getLineCount()*getRowHeight()) );
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

		// setColumns(Math.round(newWidth/getColumnWidth()));
		// setRows(Math.round(newHeight/getRowHeight()));
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
