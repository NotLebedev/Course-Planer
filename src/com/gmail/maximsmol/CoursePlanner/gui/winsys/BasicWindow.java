package com.gmail.maximsmol.CoursePlanner.gui.winsys;

import java.awt.Point;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Container;
import javax.swing.BorderFactory;
import javax.swing.event.EventListenerList;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import com.gmail.maximsmol.CoursePlanner.ConfigData;
import com.gmail.maximsmol.CoursePlanner.gui.DraggablePanel;
import com.gmail.maximsmol.CoursePlanner.gui.GUIUtils;
import com.gmail.maximsmol.CoursePlanner.gui.ResizableLabel;
import com.gmail.maximsmol.CoursePlanner.gui.ResizablePanel;
import com.gmail.maximsmol.CoursePlanner.gui.relativeComponents.RelativeContainer;

public class BasicWindow extends DraggablePanel {
	public static final long serialVersionUID = 1L;

	protected final static int TITLEBAR_SIZE_FACTOR = ConfigData.integer("titleBar_size_factor");

	protected ResizablePanel titleBar;
	protected ResizableLabel title;
	protected ResizableLabel closeButton;
	protected ResizablePanel mainPane;

	protected EventListenerList windowEventListeners;


	//
	// Class basics
	//

	//
	// Constructors
	public BasicWindow() {
		super();
		setupFrom();
	}

	public BasicWindow(final String titleText) {
		this();
		setupFrom(titleText);

		sizeSelf();
		relayout();
	}

	//
	// `setupFrom`s
	public void setupFrom() {
		super.setupFrom();

		GUIUtils.decorateAsOpaquePane(this);

		w = ConfigData.integer("default_win_width");
		h = ConfigData.integer("default_win_height");

		setBorder(BorderFactory.createRaisedSoftBevelBorder());

		title = new ResizableLabel("", ResizableLabel.CENTER);

		titleBar = new ResizablePanel();
		titleBar.setLayout(null);
		titleBar.setOpaque(false);

		mainPane = new ResizablePanel();
		mainPane.setLayout(null);
		mainPane.setOpaque(false);

		closeButton = new ResizableLabel("X");
		closeButton.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(final MouseEvent e) {
				requestClose(WinsysEvent.CloseCause.CLOSE_BUTTON);
			}

			@Override
			public void mousePressed(final MouseEvent e)  { /* Required override */ }
			@Override
			public void mouseReleased(final MouseEvent e)  { /* Required override */ }
	        @Override
	        public void mouseEntered(final MouseEvent e) { /* Required override */ }
	        @Override
	        public void mouseExited(final MouseEvent e) { /* Required override */ }
		});

		GUIUtils.addInsets(titleBar, 0, 10, 0, 10);
		titleBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
		titleBar.add(closeButton);
		titleBar.add(title);

		windowEventListeners = new EventListenerList();

		add(titleBar);
		add(mainPane);
	}

	public void setupFrom(final String titleText) {
		title.setText(titleText);
	}


	//
	// Close handling
	//

	public void requestClose(WinsysEvent.CloseCause cause) {
		fireWindowEvent(new WinsysEvent(this, WinsysEvent.Type.CLOSE_REQUEST, cause));
	}

	public void close() {
		Container parent = getParent();

		if (parent != null) {
			parent.remove(this);
			parent.revalidate();
			parent.repaint();
		}

		fireWindowEvent(new WinsysEvent(this, WinsysEvent.Type.CLOSED, null));
	}


	//
	// WinsysEvent handling
	//

	public void addWindowEventListener(WinsysEventListener listener) {
		windowEventListeners.add(WinsysEventListener.class, listener);
	}

	public void removeWindowEventListener(WinsysEventListener listener) {
		windowEventListeners.remove(WinsysEventListener.class, listener);
	}

	protected void fireWindowEvent(WinsysEvent e) {
		Object[] listeners = windowEventListeners.getListenerList();

		for (int i = 0; i < listeners.length; i += 2) {
			if (listeners[i] != WinsysEventListener.class) continue;

			((WinsysEventListener) listeners[i+1]).windowActionPerformed(e);
		}
	}


	//
	// Relative component system integration
	//

	protected void layoutTitleBar() {
		title.sizeSelf();

		GUIUtils.putInTopleft(titleBar, this);
		titleBar.setRelativeSize(GUIUtils.insetedWidthOf(this), GUIUtils.verticalInsetOf(titleBar)+Math.max(GUIUtils.insetedHeightOf(this)/TITLEBAR_SIZE_FACTOR, title.getRelativeHeight()));

		closeButton.setRelativeLocation(GUIUtils.leftInsetOf(titleBar), 0);
		GUIUtils.putInVerticalMiddle(closeButton, titleBar, 0);
		closeButton.sizeSelf();
		closeButton.setRelativeSize(closeButton.getRelativeWidth(), GUIUtils.insetedHeightOf(titleBar));

		title.setRelativeSize(title.getRelativeWidth(), GUIUtils.insetedHeightOf(titleBar));
		GUIUtils.putInVerticalMiddle(title, titleBar, 0);
		GUIUtils.putInHorizontalMiddle(title, titleBar, GUIUtils.xAfter(closeButton));
	}

	@Override
	public void relayout() {
		super.relayout();

		layoutTitleBar();

		mainPane.setRelativeLocation(GUIUtils.leftInsetOf(this), GUIUtils.yAfter(titleBar));
		mainPane.setRelativeSize(GUIUtils.insetedWidthOf(this), GUIUtils.insetedHeightOf(this)-titleBar.getRelativeHeight());
	}

	@Override
	public void resize(final double hK, final double vK) {
		super.resize(hK, vK);

		titleBar.resize(hK, vK);
		title.resize(hK, vK);
		closeButton.resize(hK, vK);

		mainPane.resize(hK, vK);
	}

	@Override
	public void relocate(final double hK, final double vK, final int xOffset, final int yOffset) {
		super.relocate(hK, vK, xOffset, yOffset);

		titleBar.relocate(hK, vK, 0, 0);
		title.relocate(hK, vK, 0, 0);
		closeButton.relocate(hK, vK, 0, 0);

		mainPane.relocate(hK, vK, 0, 0);
	}
}
