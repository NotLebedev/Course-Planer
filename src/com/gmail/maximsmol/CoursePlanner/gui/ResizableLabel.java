package com.gmail.maximsmol.CoursePlanner.gui;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.FontMetrics;

import com.gmail.maximsmol.CoursePlanner.gui.GUIUtils;
import com.gmail.maximsmol.CoursePlanner.gui.Window;
import com.gmail.maximsmol.CoursePlanner.gui.relativeComponents.FullyRelativeComponent;

public class ResizableLabel extends JLabel implements FullyRelativeComponent {
	public static final long serialVersionUID = 1L;

	protected int x, y;
	protected int w, h;

	protected int fontSize;

	public void setupFrom() {
		GUIUtils.decorateAsBasicPane(this);

		fontSize = getFont().getSize();
		setVerticalTextPosition(JLabel.CENTER);
	}

	public ResizableLabel(final String text) {
		super(text);
		setupFrom();
	}

	public ResizableLabel(final String text, final int direction) {
		super(text, direction);
		setupFrom();
	}

	@Override
	public void sizeSelf() {
		Dimension size = getPreferredSize();

		setRelativeSize(GUIUtils.horizontalInsetOf(this)+Window.relativeXFromAbsolute(size.width), GUIUtils.verticalInsetOf(this)+Window.relativeYFromAbsolute(size.height) );
	}

	@Override
	public void relayout() {
		// Do nothing
	}

	//
	// RelativelyResizable as superclass
	@Override
	public void setRelativeSize(final int w, final int h) {
		this.w = w;
		this.h = h;

		relayout();
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

		setFont(getFont().deriveFont(Font.PLAIN, (float) (fontSize*vK)));
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
	}
}
