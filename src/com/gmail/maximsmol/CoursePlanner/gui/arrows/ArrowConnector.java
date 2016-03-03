package com.gmail.maximsmol.CoursePlanner.gui.arrows;

import com.gmail.maximsmol.CoursePlanner.gui.cards.SkillSetPane;
import javax.swing.JComponent;

public abstract class ArrowConnector extends JComponent {
	public static final long serialVersionUID = 1L;

	public abstract void unconnect();
	public abstract boolean connected();
	public abstract SkillSetPane parent();
	public abstract boolean isStart();
}
