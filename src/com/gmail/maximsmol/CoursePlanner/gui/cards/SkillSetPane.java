package com.gmail.maximsmol.CoursePlanner.gui.cards;

import java.util.Arrays;
import java.awt.Container;
import java.awt.Color;
import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputListener;
import java.awt.event.MouseListener;

import com.gmail.maximsmol.CoursePlanner.model.ModelWatcher;
import com.gmail.maximsmol.CoursePlanner.model.Course;
import com.gmail.maximsmol.CoursePlanner.model.SkillSet;
import com.gmail.maximsmol.CoursePlanner.gui.ResizableLabel;
import com.gmail.maximsmol.CoursePlanner.gui.arrows.ArrowUIDecorator;
import com.gmail.maximsmol.CoursePlanner.gui.arrows.ArrowManager;
import com.gmail.maximsmol.CoursePlanner.gui.arrows.ArrowConnector;
import com.gmail.maximsmol.CoursePlanner.gui.arrows.ArrowStartConnector;
import com.gmail.maximsmol.CoursePlanner.gui.arrows.ArrowEndConnector;
import com.gmail.maximsmol.CoursePlanner.gui.GUIUtils;

public class SkillSetPane extends BasicDataPane<SkillSet> {
	public static final long serialVersionUID = 1L;

	protected SkillSetListPane.OutputState isOutput;
	protected ResizableLabel closeButton;
	protected Course course;
	protected ArrowConnector connector;

	private Runnable setupFrom;
	public SkillSetPane() {
		SkillSetPane self = this;
		setupFrom = new Runnable() {
			@Override
			public void run() {
				GUIUtils.decorateAsOpaquePane(self);

				closeButton = new ResizableLabel("X");
				GUIUtils.addInsets(closeButton, 0, 2, 0, 1);
				add(closeButton);

				addMouseListener(new MouseInputListener() {
					@Override
					public void mousePressed(final MouseEvent e) {
					}

					@Override
					public void mouseDragged(final MouseEvent e) { /* Required override */ }
					@Override
					public void mouseReleased(final MouseEvent e) { /* Required override */ }
					@Override
					public void mouseEntered(final MouseEvent e) { /* Required override */ }
					@Override
					public void mouseExited(final MouseEvent e) { /* Required override */ }
					@Override
					public void mouseClicked(final MouseEvent e) {
						if (e.getButton() != MouseEvent.BUTTON1) return;

						ArrowManager.labelClick(connector, data);
					}
					@Override
					public void mouseMoved(final MouseEvent e) { /* Required override */ }
				});
			}
		};

		setupFrom.run();
	}

	public SkillSetPane(final SkillSet data, final SkillSetListPane.OutputState isOutput, final Course course) {
		this();
		setupFrom(data, isOutput, course);

		sizeSelf();
		relayout();
	}

	public void setupFrom() { super.setupFrom(); setupFrom.run(); }

	public void setupFrom(final SkillSet data, final SkillSetListPane.OutputState isOutput, final Course course) {
		super.setupFrom(data);

		this.isOutput = isOutput;
		this.course = course;

		if (isOutput == SkillSetListPane.OutputState.OUTPUT)
			connector = new ArrowStartConnector(this);
		else
			connector = new ArrowEndConnector(this);
		connector.setSize(0, 0);
		add(connector);

		arrowConnectionChanged();

		closeButton.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(final MouseEvent e) {
				if (isOutput == SkillSetListPane.OutputState.OUTPUT)
					course.output.remove(data);
				else
					course.input.remove(data);

				ModelWatcher.courseUpdated(course);
			}

			@Override
			public void mousePressed(final MouseEvent e) { /* Required override */ }
			@Override
			public void mouseReleased(final MouseEvent e) { /* Required override */ }
			@Override
			public void mouseEntered(final MouseEvent e) { /* Required override */ }
			@Override
			public void mouseExited(final MouseEvent e) { /* Required override */ }
		});
	}

	public void disconnect() {
		connector.unconnect();
	}

	//
	// Getters
	public Course course() {
		return course;
	}

	public ArrowConnector connector() {
		return connector;
	}

	public boolean isInSameCardWith(final SkillSetPane that) {
		if (that == null)
			return false;


		Container mySkillSetListPane = getParent();
		if (mySkillSetListPane == null)
			return false;

		Container thatSkillSetListPane = that.getParent();
		if (thatSkillSetListPane == null)
			return false;

		if (mySkillSetListPane == thatSkillSetListPane)
			return true;


		Container myCourseCard = mySkillSetListPane.getParent();
		if (myCourseCard == null)
			return false;

		Container thatCourseCard = thatSkillSetListPane.getParent();
		if (thatCourseCard == null)
			return false;

		return myCourseCard == thatCourseCard;
	}


	//
	// Relative component system integration
	//

	@Override
	public void sizeSelf() {
		super.sizeSelf();

		closeButton.sizeSelf();

		setRelativeSize(GUIUtils.horizontalInsetOf(this)+closeButton.getRelativeWidth()+label.getRelativeWidth(), GUIUtils.verticalInsetOf(this)+Math.max(closeButton.getRelativeHeight(), label.getRelativeHeight()));
	}

	@Override
	public void relayout() {
		super.relayout();

		GUIUtils.putAtLeftSide(closeButton, this);
		GUIUtils.putInVerticalMiddle(closeButton, this);
		closeButton.relayout();

		label.setRelativeSize(GUIUtils.insetedWidthOf(this)-closeButton.getRelativeWidth(), GUIUtils.insetedHeightOf(this));
		label.setRelativeLocation(GUIUtils.xAfter(closeButton), GUIUtils.topInsetOf(this));
		label.relayout();
	}

	@Override
	public void resize(final double hK, final double vK) {
		super.resize(hK, vK);

		closeButton.resize(hK, vK);
		connector.setSize(0, 0);
	}

	@Override
	public void relocate(final double hK, final double vK, final int xOffset, final int yOffset) {
		super.relocate(hK, vK, xOffset, yOffset);

		closeButton.relocate(hK, vK, 0, 0);

		if   (connector.isStart()) connector.setLocation(getWidth(), getHeight()/2);
		else                       connector.setLocation(0, getHeight()/2);
	}


	//
	// ArrowConnectionListener as superclass
	//

	public final void arrowConnectionChanged() {
		if (connector.isStart())
			ArrowUIDecorator.decorateStartConnectorOf(this);
		else
			ArrowUIDecorator.decorateEndConnectorOf(this);

		repaint();
	}

	public final void arrowConnectorSelected() {
		if (!connector.isStart()) return;

		ArrowUIDecorator.decorateAsSelected(this);

		repaint();
	}

	public final void arrowConnectorUnselected() {
		if (!connector.isStart()) return;

		ArrowUIDecorator.decorateAsUnselected(this);

		repaint();
	}

	public void setBackground(Color c) {
		super.setBackground(c);

		if (label != null) label.setBackground(c);
		if (closeButton != null) closeButton.setBackground(c);
	}

	public void setForeground(Color c) {
		super.setForeground(c);

		if (label != null) label.setForeground(c);
		if (closeButton != null) closeButton.setForeground(c);
	}
}
