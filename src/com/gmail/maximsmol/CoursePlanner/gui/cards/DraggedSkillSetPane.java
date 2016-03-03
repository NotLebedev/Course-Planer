package com.gmail.maximsmol.CoursePlanner.gui.cards;

import java.awt.Point;
import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputListener;
import javax.swing.BorderFactory;
import javax.swing.border.EmptyBorder;
import javax.swing.border.Border;

import com.gmail.maximsmol.CoursePlanner.gui.Window;
import com.gmail.maximsmol.CoursePlanner.gui.GUIUtils;
import com.gmail.maximsmol.CoursePlanner.model.SkillSet;

public class DraggedSkillSetPane extends BasicDataPane<SkillSet> implements MouseInputListener {
	public static final long serialVersionUID = 1L;

	protected DictionarySkillSetPane parent;

	protected Point dragStart;
	protected Point dropLocation;
	protected boolean dragging;
	protected EmptyBorder placeholderBorder;
	protected Border bevelBorder;


	//
	// Class basics
	//

	//
	// Constructors
	private Runnable setupFrom;
	public DraggedSkillSetPane() {
		DraggedSkillSetPane self = this;
		setupFrom = new Runnable() {
			@Override
			public void run() {
				dragStart = new Point(0, 0);
				dropLocation = new Point(0, 0);

				bevelBorder = BorderFactory.createRaisedSoftBevelBorder();
				placeholderBorder = new EmptyBorder(getInsets());
				setBorder(placeholderBorder);

				addMouseListener(self);
				addMouseMotionListener(self);

				GUIUtils.decorateAsOpaquePane(self);

				undecorate();
				repaint();
			}
		};

		setupFrom.run();
	}

	public DraggedSkillSetPane(final DictionarySkillSetPane parent) {
		this();
		setupFrom(parent);
	}

	public void setupFrom() { super.setupFrom(); setupFrom.run(); }

	public void setupFrom(final DictionarySkillSetPane parent) {
		if (this.parent == parent) return;

		this.parent = parent;

		setupFrom(parent.data);
	}

	public void setupFrom(final BasicDataPane<SkillSet> pane) {
		if (data.equals(pane.data)) return;

		super.setupFrom(pane.data);

		sizeSelf();
		relayout();
	}

	//
	// Getters
	public Point dropLocation() {
		return dropLocation;
	}

	public boolean isDragged() {
		return dragging;
	}


	//
	// UI control
	//

	private final void decorate() {
		setOpaque(true);
		setBorder(bevelBorder);
		label.setVisible(true);
	}

	private final void undecorate() {
		setOpaque(false);
		setBorder(placeholderBorder);
		label.setVisible(false);
	}


	//
	// MouseListener as superclass
	//

	@Override
	public void mousePressed(final MouseEvent e) {
		if (dragging) return;

		if (e.getButton() != MouseEvent.BUTTON1) return;

		dragStart = e.getPoint();
		dragging = true;

		decorate();
	}

	@Override
	public void mouseReleased(final MouseEvent e) {
		if (!dragging) return;

		dropLocation = e.getLocationOnScreen();
		dragging = false;

		SkillSetListPane.droppedSomewhere(this);

		undecorate();
		parent.putDragPaneInPlace();
	}

	@Override
	public void mouseDragged(final MouseEvent e) {
		x = e.getX()-dragStart.x;
		y = e.getY()-dragStart.y;

		revalidate();
		repaint();
	}

	@Override
	public void mouseEntered(final MouseEvent e) { /* Required override */ }
	@Override
	public void mouseExited(final MouseEvent e) { /* Required override */ }
	@Override
	public void mouseClicked(final MouseEvent e) { /* Required override */ }
	@Override
	public void mouseMoved(final MouseEvent e) { /* Required override */ }
}
