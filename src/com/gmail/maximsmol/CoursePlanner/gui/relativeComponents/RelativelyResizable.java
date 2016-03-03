package com.gmail.maximsmol.CoursePlanner.gui.relativeComponents;

public interface RelativelyResizable {
	void resize(final double horizontalStretch, final double verticalStretch);

	void setRelativeSize(final int w, final int h);

	int getRelativeWidth();
	int getRelativeHeight();
}

