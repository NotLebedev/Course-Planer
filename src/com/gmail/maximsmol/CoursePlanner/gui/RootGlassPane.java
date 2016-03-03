package com.gmail.maximsmol.CoursePlanner.gui;

import java.awt.Point;
import java.awt.Graphics;
import javax.swing.JComponent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;

import com.gmail.maximsmol.CoursePlanner.gui.arrows.ArrowManager;

public final class RootGlassPane extends JComponent {
	public static final long serialVersionUID = 1L;

	public static final RootGlassPane pane = new RootGlassPane();
	private static final Point dragStart = new Point(0, 0);

	private RootGlassPane() {
		super();

		Window.win.setGlassPane(this);
		Window.win.getContentPane().addMouseListener(new MouseListener() {
			@Override
			public void mousePressed(final MouseEvent e) {
				dragStart.setLocation(e.getPoint());
				ArrowManager.labelClick(null, null);
			}

			@Override
			public void mouseReleased(final MouseEvent e) { /* Required override */ }
			@Override
			public void mouseEntered(final MouseEvent e) { /* Required override */ }
			@Override
			public void mouseExited(final MouseEvent e) { /* Required override */ }
			@Override
			public void mouseClicked(final MouseEvent e) { /* Required override */ }
		});
		Window.win.getContentPane().addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(final MouseEvent e) {
				Window.layoutManager.addOffset(e.getX() - dragStart.x, e.getY() - dragStart.y);

				dragStart.setLocation(e.getPoint());

				Window.win.getContentPane().revalidate();
			}

			@Override
			public void mouseMoved(final MouseEvent e) { /* Required override */ }
		});

		setVisible(true);
	}

	@Override
	public void paintComponent(final Graphics g) {
		super.paintComponent(g);

		ArrowManager.drawArrows(g);
	}
}
