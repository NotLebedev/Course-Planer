package com.gmail.maximsmol.CoursePlanner.gui;

import java.awt.Point;
import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputListener;

public class DraggablePanel extends ResizablePanel {
	public static final long serialVersionUID = 1L;

	private Point dragStart = new Point(0, 0);

	public DraggablePanel() {
		super();

		MouseInputListener listener = new MouseInputListener() {
			@Override
			public void mouseDragged(final MouseEvent e) {
				x += e.getX() - dragStart.getX();
				y += e.getY() - dragStart.getY();

				revalidate();
				repaint();
			}

			@Override
			public void mousePressed(final MouseEvent e) {
				dragStart = e.getPoint();
			}

			@Override
			public void mouseMoved(final MouseEvent e) { /* Required override */ }
			@Override
			public void mouseReleased(final MouseEvent e) { /* Required override */ }
			@Override
			public void mouseEntered(final MouseEvent e) { /* Required override */ }
			@Override
			public void mouseExited(final MouseEvent e) { /* Required override */ }
			@Override
			public void mouseClicked(final MouseEvent e) { /* Required override */ }
		};

		addMouseListener(listener);
		addMouseMotionListener(listener);
	}
}
