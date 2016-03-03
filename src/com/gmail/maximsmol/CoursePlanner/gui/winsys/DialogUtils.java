package com.gmail.maximsmol.CoursePlanner.gui.winsys;

import com.gmail.maximsmol.CoursePlanner.gui.Window;

public final class DialogUtils {
	private DialogUtils() {}

	public static void showDialog(BasicWindow dialog) {
		Window.win.add(dialog);
		Window.win.getContentPane().setComponentZOrder(dialog, 1);

        Window.win.revalidate();
        Window.win.repaint();
	}
}