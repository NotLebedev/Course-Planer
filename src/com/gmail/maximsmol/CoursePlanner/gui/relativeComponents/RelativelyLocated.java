package com.gmail.maximsmol.CoursePlanner.gui.relativeComponents;

public interface RelativelyLocated {
	void relocate(final double horizontalStretch, final double verticalStretch, final int xOffset, final int yOffset);

	void setRelativeLocation(final int x, final int y);

	int getRelativeX();
	int getRelativeY();
}

