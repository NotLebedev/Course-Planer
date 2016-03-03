package com.gmail.maximsmol.CoursePlanner.gui.arrows;

import java.awt.Graphics;
import java.awt.RenderingHints;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Color;
import javax.swing.SwingUtilities;

import java.util.ArrayList;
import java.lang.ref.WeakReference;

import com.gmail.maximsmol.CoursePlanner.model.SkillSet;
import com.gmail.maximsmol.CoursePlanner.gui.RootGlassPane;

public final class ArrowManager {
	public static final double ARROW_END_LENGTH = 10;
	public static final double ARROW_END_ANGLE = Math.toRadians(40);

	private static ArrayList<WeakReference<ArrowStartConnector>> connectors = new ArrayList<WeakReference<ArrowStartConnector>>();
	private static ArrowStartConnector curCon;
	private static SkillSet curSkill;

	private ArrowManager() {};

	public static void addConnector(final ArrowStartConnector connector) {
		connectors.add(new WeakReference<ArrowStartConnector>(connector));
	}

	public static void labelClick(final ArrowConnector clickedCon, final SkillSet skill) {
		synchronized (ArrowManager.class) {
			if (curCon == null) {
				if (clickedCon == null) return;

				if (!clickedCon.isStart()) return;
				final ArrowStartConnector con = (ArrowStartConnector) clickedCon;

				curCon = con;
				curSkill = skill;
				curCon.parent().arrowConnectorSelected();
			}
			else if (clickedCon == null) {
				curCon.connectTo(null);
				curCon.parent().arrowConnectorUnselected();
				curCon = null;

				RootGlassPane.pane.repaint();
			}
			else {
				if (curCon.equals(clickedCon)) {
					curCon.parent().arrowConnectorUnselected();
					curCon = null;
					return;
				}

				if (clickedCon.isStart()) {
					final ArrowStartConnector con = (ArrowStartConnector) clickedCon;
					curCon = con;
					return;
				}
				final ArrowEndConnector con = (ArrowEndConnector) clickedCon;

				if (curCon.parent().isInSameCardWith(con.parent())) return;
				if (!curSkill.canPartiallyFulfill(skill)) return;

				curCon.connectTo(con);
				curCon.parent().arrowConnectorUnselected();
				curCon = null;

				RootGlassPane.pane.repaint();
			}
		}
	}

	public static void drawArrows(final Graphics g) {
		final Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g.setColor(Color.black);

		ArrayList<WeakReference<ArrowStartConnector>> toRem = new ArrayList<WeakReference<ArrowStartConnector>>();
		for (final WeakReference<ArrowStartConnector> ref : connectors) {
			final ArrowStartConnector con = ref.get();

			if (con == null) {
				toRem.add(ref);
				continue;
			}

			if (!con.connected()) continue;

			g2d.setStroke(new BasicStroke(ArrowUIDecorator.getArrowWidth(con)));
			g.setColor(con.arrowColor);

			final Point start = SwingUtilities.convertPoint(con.getParent(), con.getLocation(), RootGlassPane.pane);
			final Point end = SwingUtilities.convertPoint(con.connectedTo().getParent(), con.connectedTo().getLocation(), RootGlassPane.pane);

			final double ang = Math.atan2(end.y-start.y, end.x-start.x);

			g.drawLine(start.x, start.y, end.x, end.y);
			g.drawLine(end.x - (int) Math.round(Math.cos(ang+ARROW_END_ANGLE)*ARROW_END_LENGTH), end.y - (int) Math.round(Math.sin(ang+ARROW_END_ANGLE)*ARROW_END_LENGTH), end.x, end.y);
			g.drawLine(end.x - (int) Math.round(Math.cos(ang-ARROW_END_ANGLE)*ARROW_END_LENGTH), end.y - (int) Math.round(Math.sin(ang-ARROW_END_ANGLE)*ARROW_END_LENGTH), end.x, end.y);
		}
		connectors.remove(toRem);
	}
}
