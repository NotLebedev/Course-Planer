package com.gmail.maximsmol.CoursePlanner.gui.cards;

import java.awt.Color;
import java.awt.Point;
import java.awt.Dimension;
import javax.swing.BorderFactory;

import java.util.ArrayList;

import com.gmail.maximsmol.CoursePlanner.model.SkillSet;
import com.gmail.maximsmol.CoursePlanner.gui.GUIUtils;
import com.gmail.maximsmol.CoursePlanner.gui.ResizablePanel;

public class SkillDictionary extends ResizablePanel {
	public static final long serialVersionUID = 1L;

	protected DictionarySkillSetListPane skillsPane;

	public SkillDictionary() {
		setupFrom();
	}

	public void setupFrom() {
		super.setupFrom();

		//
		// Decorate
		GUIUtils.decorateAsOpaquePane(this);
		setBorder(BorderFactory.createRaisedSoftBevelBorder());

		//
		// Find unique SkillSets
		final ArrayList<SkillSet> uniqueSkills = new ArrayList<SkillSet>();
		for (final SkillSet candidate : SkillSet.skillSets.values()) {
			boolean shouldAdd = true;

			for (final SkillSet a : uniqueSkills) {
				if (a.canPartiallyFulfill(candidate)) {
					shouldAdd = false;
					break;
				}
			}

			if (!shouldAdd) continue;

			uniqueSkills.add(candidate);
		}

		//
		// Setup `skillsPane`
		skillsPane = new DictionarySkillSetListPane(uniqueSkills);
		add(skillsPane);

		relayout();
	}


	//
	// Relative component system integration
	//

	@Override
	public final void relayout() {
		super.relayout();

		GUIUtils.fillWith(skillsPane, this);
		GUIUtils.putInTopleft(skillsPane, this);
		skillsPane.relayout();
	}

	@Override
	public void resize(final double hK, final double vK) {
		super.resize(hK, vK);

		skillsPane.resize(hK, vK);
	}

	@Override
	public void relocate(final double hK, final double vK, final int xOffset, final int yOffset) {
		super.relocate(hK, vK, xOffset, yOffset);

		skillsPane.relocate(hK, vK, 0, 0);
	}
}
