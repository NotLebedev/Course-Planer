package com.gmail.maximsmol.CoursePlanner.gui;

import javax.swing.JButton;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.FontMetrics;

import com.gmail.maximsmol.CoursePlanner.gui.relativeComponents.RelativelyBounded;

public class ResizableButton extends JButton implements RelativelyBounded {
	public static final long serialVersionUID = 1L;

	protected int x, y;
	protected int w, h;

	protected int fontSize;

	public ResizableButton(final String text) {
		super(text);
		fontSize = getFont().getSize();
	}

	public void sizeSelf() {
		final FontMetrics metrics = getFontMetrics(getFont().deriveFont(Font.PLAIN, fontSize));

		w = metrics.stringWidth(getText())+getInsets().left+getInsets().right;
		h = metrics.getHeight()+getInsets().top+getInsets().bottom;
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
