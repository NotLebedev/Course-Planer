package com.gmail.maximsmol.CoursePlanner.gui.arrows;

import java.awt.Color;

import com.gmail.maximsmol.CoursePlanner.model.SkillLevel;
import com.gmail.maximsmol.CoursePlanner.gui.cards.SkillSetPane;

public final class ArrowUIDecorator {
	public static final Color NORMAL_SKILL     = new Color(  0,   0,   0);
	public static final Color OPTIONAL_SKILL   = new Color(  0, 255, 255);
	public static final Color TRASNFERED_SKILL = new Color(255,   0, 255);

	public static final Color SKILL_LEVEL_MISMATCH = new Color(145, 145, 0);

	public static final Color DEFAULT_LABEL_BACKGROUND = new Color(255, 255, 255);
	public static final Color DEFAULT_LABEL_FOREGROUND = new Color(  0,   0,   0);

	public static final Color NONE_SKILL_LEVEL_LABEL_BACKGROUND = DEFAULT_LABEL_BACKGROUND;
	public static final Color BEGINNER_SKILL_LEVEL_LABEL_BACKGROUND = new Color(255, 255, 200);
	public static final Color INTERMEDIATE_SKILL_LEVEL_LABEL_BACKGROUND = new Color(255, 200, 255);
	public static final Color ADVANCED_SKILL_LEVEL_LABEL_BACKGROUND = new Color(255, 255, 200);

	public static final Color UNCONNECTED_INPUT_LABEL_BACKGROUND = new Color(255,   0,   0);
	public static final Color UNCONNECTED_INPUT_LABEL_FOREGROUND = new Color(255, 255, 255);

	public static final Color SELECTED_LABEL_BACKGROUND = new Color(200, 200, 200);
	public static final Color SELECTED_LABEL_FOREGROUND = new Color(  0,   0,   0);

	private ArrowUIDecorator() {}

	public static int getArrowWidth(final ArrowStartConnector con) {
		final SkillLevel level = con.parent().data().level();

		if (level == SkillLevel.NONE) return 1;
		else if (level == SkillLevel.BEGINNER) return 1;
		else if (level == SkillLevel.INTERMEDIATE) return 3;
		else if (level == SkillLevel.ADVANCED) return 6;

		return 1;
	}

	//
	// Connector decorators
	public static void decorateStartConnectorOf(final SkillSetPane pane) {
		if (!pane.connector().isStart()) return;

		final ArrowStartConnector connector = (ArrowStartConnector) pane.connector();

		final SkillLevel level = connector.parent().data().level();
		if (level == SkillLevel.NONE) pane.setBackground(NONE_SKILL_LEVEL_LABEL_BACKGROUND);
		else if (level == SkillLevel.BEGINNER) pane.setBackground(BEGINNER_SKILL_LEVEL_LABEL_BACKGROUND);
		else if (level == SkillLevel.INTERMEDIATE) pane.setBackground(INTERMEDIATE_SKILL_LEVEL_LABEL_BACKGROUND);
		else if (level == SkillLevel.ADVANCED) pane.setBackground(ADVANCED_SKILL_LEVEL_LABEL_BACKGROUND);

		pane.setForeground(DEFAULT_LABEL_FOREGROUND);

		if (pane.course().isSkillTransfered(pane.data()))
				connector.arrowColor = TRASNFERED_SKILL;

		if (!connector.connected()) return;

		if (connector.connectedTo().parent().data().level().value > pane.data().level().value)
			connector.arrowColor = SKILL_LEVEL_MISMATCH;
	}

	public static void decorateEndConnectorOf(final SkillSetPane pane) {
		if (pane.connector().isStart()) return;

		if (pane.connector().connected()) {
			pane.setBackground(DEFAULT_LABEL_BACKGROUND);
			pane.setForeground(DEFAULT_LABEL_FOREGROUND);
		}
		else {
			pane.setBackground(UNCONNECTED_INPUT_LABEL_BACKGROUND);
			pane.setForeground(UNCONNECTED_INPUT_LABEL_FOREGROUND);
		}
	}

	//
	// Pane decorators based on selection state
	public static void decorateAsSelected(final SkillSetPane pane) {
		pane.setBackground(SELECTED_LABEL_BACKGROUND);
		pane.setForeground(SELECTED_LABEL_FOREGROUND);
	}

	public static void decorateAsUnselected(final SkillSetPane pane) {
		final SkillLevel level = pane.connector().parent().data().level();
		if (level == SkillLevel.NONE) pane.setBackground(NONE_SKILL_LEVEL_LABEL_BACKGROUND);
		else if (level == SkillLevel.BEGINNER) pane.setBackground(BEGINNER_SKILL_LEVEL_LABEL_BACKGROUND);
		else if (level == SkillLevel.INTERMEDIATE) pane.setBackground(INTERMEDIATE_SKILL_LEVEL_LABEL_BACKGROUND);
		else if (level == SkillLevel.ADVANCED) pane.setBackground(ADVANCED_SKILL_LEVEL_LABEL_BACKGROUND);

		pane.setForeground(DEFAULT_LABEL_FOREGROUND);
	}
}
