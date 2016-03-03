package com.gmail.maximsmol.CoursePlanner.gui;

import java.awt.Dimension;
import javax.swing.JFrame;

public final class Window extends JFrame {
	public static final long serialVersionUID = 1L;

	public static final NotAbsoluteLayout layoutManager = new NotAbsoluteLayout(0, 0);
	public static final Window win = new Window();

	private Window() {
		super();

		setTitle("Course Planner");

		setMinimumSize(new Dimension(800, 600));
		setSize(getMinimumSize());

		setLocationRelativeTo(null);
		setLocationByPlatform(true);

		layoutManager.setBaseSize(getWidth(), getHeight());
		setLayout(layoutManager);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
	}

	public static int relativeXFromAbsolute(int x) {
		return layoutManager.relativeXFromAbsolute(x, win);
	}

	public static int relativeYFromAbsolute(int y) {
		return layoutManager.relativeYFromAbsolute(y, win);
	}

	public static int relativeFromOnScreenX(int x) {
		return layoutManager.relativeFromOnScreenX(x, win);
	}

	public static int relativeFromOnScreenY(int y) {
		return layoutManager.relativeFromOnScreenY(y, win);
	}
}
