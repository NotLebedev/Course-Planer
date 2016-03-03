package com.gmail.maximsmol.CoursePlanner.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Insets;
import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.border.Border;
import javax.swing.BorderFactory;

import com.gmail.maximsmol.CoursePlanner.gui.relativeComponents.RelativelyResizable;
import com.gmail.maximsmol.CoursePlanner.gui.relativeComponents.RelativelyLocated;
import com.gmail.maximsmol.CoursePlanner.gui.relativeComponents.RelativelyBounded;

public final class GUIUtils {
	private GUIUtils() {}

	//
	// Generic helpers
	//

	public static void decorateAsOpaquePane(JComponent c) {
		c.setLayout(null);
		c.setOpaque(true);
		c.setBackground(new Color(250, 250, 250));
	}

	public static void decorateAsBasicPane(JComponent c) {
		c.setLayout(null);
		c.setOpaque(false);
	}

	public static Border addInsetsToBorder(Border brdr, int top, int left, int bottom, int right) {
		return BorderFactory.createCompoundBorder(brdr, BorderFactory.createEmptyBorder(top, left, bottom, right));
	}

	public static void addInsets(JComponent c, int top, int left, int bottom, int right) {
		c.setBorder(BorderFactory.createCompoundBorder(c.getBorder(), BorderFactory.createEmptyBorder(top, left, bottom, right)) );
	}


	//
	// Relative components handlers
	//

	//
	// RelativelyResizable handlers
	public static boolean isRelativelyResizable(final Object o) {
		return (o instanceof RelativelyResizable) || (o instanceof RelativelyBounded);
	}

	public static RelativelyResizable toRelativelyResizable(final Component c) {
		if (!isRelativelyResizable(c))
			throw new RuntimeException("The component specified is not relatively resizable");

		return ((RelativelyResizable) c);
	}

	//
	// RelativelyLocated handlers
	public static boolean isRelativelyLocated(final Object o) {
		return (o instanceof RelativelyResizable) || (o instanceof RelativelyBounded);
	}

	public static RelativelyLocated toRelativelyLocated(final Component c) {
		if (!isRelativelyLocated(c))
			throw new RuntimeException("The component specified is not relatively located");

		return ((RelativelyLocated) c);
	}

	//
	// Appropriate dimension calculators
	public static int appropriateWidthOf(final Component c) {
		if (isRelativelyResizable(c))
			return toRelativelyResizable(c).getRelativeWidth();

		return c.getWidth();
	}

	public static int appropriateHeightOf(final Component c) {
		if (isRelativelyResizable(c))
			return toRelativelyResizable(c).getRelativeHeight();

		return c.getHeight();
	}

	//
	// Appropriate location calculators
	public static int appropriateXOf(final Component c) {
		if (isRelativelyLocated(c))
			return toRelativelyLocated(c).getRelativeX();

		return c.getX();
	}

	public static int appropriateYOf(final Component c) {
		if (isRelativelyLocated(c))
			return toRelativelyLocated(c).getRelativeY();

		return c.getY();
	}

	//
	// Appropriate bounds setters
	public static void setAppropriateSizeOf(final Component c, final int w, final int h) {
		if (isRelativelyResizable(c))
			toRelativelyResizable(c).setRelativeSize(w, h);
		else
			c.setSize(w, h);
	}

	public static void setAppropriateLocationOf(final Component c, final int x, final int y) {
		if (isRelativelyLocated(c))
			toRelativelyLocated(c).setRelativeLocation(x, y);
		else
			c.setLocation(x, y);
	}


	//
	// Layout helpers
	//

	//
	// Single inset shortcuts
	public static int rightInsetOf(final Container c) {
		return c.getInsets().right;
	}

	public static int topInsetOf(final Container c) {
		return c.getInsets().top;
	}

	public static int leftInsetOf(final Container c) {
		return c.getInsets().left;
	}

	public static int bottomInsetOf(final Container c) {
		return c.getInsets().bottom;
	}

	//
	// Total inset calculators
	public static int horizontalInsetOf(final Container c) {
		Insets i = c.getInsets();
		return i.left+i.right;
	}

	public static int verticalInsetOf(final Container c) {
		Insets i = c.getInsets();
		return i.top+i.bottom;
	}

	//
	// Inseted dimension calculators
	public static int insetedWidthOf(final Container c) {
		return appropriateWidthOf(c)-horizontalInsetOf(c);
	}

	public static int insetedHeightOf(final Container c) {
		return appropriateHeightOf(c)-verticalInsetOf(c);
	}

	//
	// Relative positioning helpers
	public static int xAfter(final Component c) {
		return appropriateXOf(c)+appropriateWidthOf(c);
	}

	public static int yAfter(final Component c) {
		return appropriateYOf(c)+appropriateHeightOf(c);
	}


	//
	// Layouters
	//

	public static void fillWith(final Component c, final Container parent) {
		setAppropriateSizeOf(c, insetedWidthOf(parent), insetedHeightOf(parent));
	}

	//
	// Centerers
	public static void putInHorizontalMiddle(final Component c, final Container parent, final int leftInset) {
		setAppropriateLocationOf(c, leftInset+(insetedWidthOf(parent)-leftInset)/2-appropriateWidthOf(c)/2, appropriateYOf(c));
	}

	public static void putInHorizontalMiddle(final Component c, final Container parent) {
		putInHorizontalMiddle(c, parent, leftInsetOf(parent));
	}


	public static void putInVerticalMiddle(final Component c, final Container parent, final int topInset) {
		setAppropriateLocationOf(c, appropriateXOf(c), topInset+(insetedHeightOf(parent)-topInset)/2-appropriateHeightOf(c)/2);
	}

	public static void putInVerticalMiddle(final Component c, final Container parent) {
		putInVerticalMiddle(c, parent, topInsetOf(parent));
	}

	//
	// Put-aters
		//
		// Side put-aters
		public static void putAtTop(final Component c, final Container parent) {
			setAppropriateLocationOf(c, appropriateXOf(c), topInsetOf(parent));
		}

		public static void putAtLeftSide(final Component c, final Container parent) {
			setAppropriateLocationOf(c, leftInsetOf(parent), appropriateYOf(c));
		}

		public static void putAtBottom(final Component c, final Container parent) {
			setAppropriateLocationOf(c, appropriateXOf(c), topInsetOf(parent)+insetedHeightOf(parent)-appropriateHeightOf(c));
		}

		public static void putAtRightSide(final Component c, final Container parent) {
			setAppropriateLocationOf(c, leftInsetOf(parent)+insetedWidthOf(parent)-appropriateWidthOf(c), appropriateYOf(c));
		}

		//
		// Compound put-aters
		public static void putInTopleft(final Component c, final Container parent) {
			putAtTop(c, parent);
			putAtLeftSide(c, parent);
		}

		public static void putInTopright(final Component c, final Container parent) {
			putAtTop(c, parent);
			putAtRightSide(c, parent);
		}

		public static void putInBottomleft(final Component c, final Container parent) {
			putAtBottom(c, parent);
			putAtLeftSide(c, parent);
		}

		public static void putInTopBottomright(final Component c, final Container parent) {
			putAtBottom(c, parent);
			putAtRightSide(c, parent);
		}
};
