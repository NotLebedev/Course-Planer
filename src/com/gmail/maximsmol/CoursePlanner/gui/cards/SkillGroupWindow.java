package com.gmail.maximsmol.CoursePlanner.gui.cards;

import com.gmail.maximsmol.CoursePlanner.gui.ResizablePanel;
import com.gmail.maximsmol.CoursePlanner.gui.GUIUtils;
import com.gmail.maximsmol.CoursePlanner.gui.winsys.BasicWindow;
import com.gmail.maximsmol.CoursePlanner.model.SkillSet;

public class SkillGroupWindow extends BasicWindow {
	public static final long serialVersionUID = 1L;

	public ResizablePanel list = new ResizablePanel();

	public SkillGroupWindow(SkillSet set) {
		super(set.name());

		list = new ResizablePanel();

		mainPane.add(list);

		sizeSelf();
		relayout();
	}

	public void sizeSelf() {
		layoutTitleBar();

		// list.sizeSelf();

		mainPane.setRelativeSize(mainPane.getRelativeWidth(), GUIUtils.verticalInsetOf(mainPane)+list.getRelativeHeight() );

		setRelativeSize(getRelativeWidth(), GUIUtils.verticalInsetOf(this)+titleBar.getRelativeHeight()+mainPane.getRelativeHeight());
	}

	@Override
	public void relayout() {
		super.relayout();

		GUIUtils.putInTopleft(list, this);
		list.setRelativeSize(GUIUtils.insetedWidthOf(mainPane), GUIUtils.insetedHeightOf(mainPane));
	}

	//
	// RelativelyResizable as superclass
	@Override
	public void resize(final double hK, final double vK) {
		super.resize(hK, vK);

		list.resize(hK, vK);
	}

	//
	// RelativelyLocated as superclass
	@Override
	public void relocate(final double hK, final double vK, final int xOffset, final int yOffset) {
		super.relocate(hK, vK, xOffset, yOffset);

		list.relocate(hK, vK, 0, 0);
	}
}