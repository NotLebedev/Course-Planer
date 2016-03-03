package com.gmail.maximsmol.CoursePlanner.gui.arrows;

import com.gmail.maximsmol.CoursePlanner.gui.cards.SkillSetPane;
import java.awt.Color;

public class ArrowStartConnector extends ArrowConnector {
	public static final long serialVersionUID = 1L;

	protected SkillSetPane parent;
	protected ArrowEndConnector connectedTo;
	public Color arrowColor = ArrowUIDecorator.NORMAL_SKILL;

	public ArrowStartConnector() {
		super();

		ArrowManager.addConnector(this);
	}

	public ArrowStartConnector(final SkillSetPane parent) {
		super();
		this.parent = parent;

		ArrowManager.addConnector(this);
	}

	public ArrowStartConnector(final ArrowEndConnector that) {
		super();
		connectTo(that);

		ArrowManager.addConnector(this);
	}

	//
	// Getters
	public boolean connected() {
		return connectedTo != null;
	}

	public ArrowEndConnector connectedTo() {
		return connectedTo;
	}

	@Override
	public SkillSetPane parent() {
		return parent;
	}

	@Override
	public boolean isStart() {
		return true;
	}

	//
	// Connection changers
	public void connectTo(final ArrowEndConnector that) {
		if (connectedTo != null) connectedTo.unconnect(this);

		connectedTo = that;
		if (connectedTo != null) connectedTo.connectTo(this);

		if (parent != null) parent.arrowConnectionChanged();
	}

	@Override
	public void unconnect() {
		if (connectedTo != null) connectedTo.unconnect(this);
		connectedTo = null;

		if (parent != null) parent.arrowConnectionChanged();
	}
}
