package com.gmail.maximsmol.CoursePlanner.gui.arrows;

import com.gmail.maximsmol.CoursePlanner.gui.cards.SkillSetPane;
import java.util.ArrayList;

public class ArrowEndConnector extends ArrowConnector {
	public static final long serialVersionUID = 1L;

	protected final SkillSetPane parent;
	protected ArrayList<ArrowStartConnector> connectedTo = new ArrayList<ArrowStartConnector>();

	public ArrowEndConnector(final SkillSetPane parent) {
		super();
		this.parent = parent;
	}

	//
	// Getters
	@Override
	public boolean connected() {
		return !connectedTo.isEmpty();
	}

	public ArrayList<ArrowStartConnector> connectedTo() {
		return connectedTo;
	}

	@Override
	public SkillSetPane parent() {
		return parent;
	}

	@Override
	public boolean isStart() {
		return false;
	}

	//
	// Connection changers
	public void connectTo(final ArrowStartConnector that) {
		if (connectedTo.contains(that)) {
			throw new RuntimeException("Connector already added");
		}

		connectedTo.add(that);

		if (parent != null) parent.arrowConnectionChanged();
	}

	@Override
	public void unconnect() {
		ArrayList<ArrowStartConnector> connectorsToDisconnect = connectedTo;
		connectedTo = new ArrayList<ArrowStartConnector>();

		for (ArrowStartConnector c : connectorsToDisconnect) {
			c.unconnect();
		}

		if (parent != null) parent.arrowConnectionChanged();
	}

	public void unconnect(final ArrowStartConnector that) {
		connectedTo.remove(that);

		if (parent != null) parent.arrowConnectionChanged();
	}

	//
	// Object as superclass
	@Override
	public int hashCode() {
		return parent.hashCode() + connectedTo.hashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		if (!(obj instanceof ArrowEndConnector)) return false;

		final ArrowEndConnector that = (ArrowEndConnector) obj;

		return parent.equals(that.parent) && connectedTo.equals(that.connectedTo);
	}
}
