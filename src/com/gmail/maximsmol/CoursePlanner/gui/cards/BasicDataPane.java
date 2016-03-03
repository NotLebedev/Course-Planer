package com.gmail.maximsmol.CoursePlanner.gui.cards;

import java.io.File;
import java.awt.Color;
import java.awt.Point;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

import com.gmail.maximsmol.CoursePlanner.ConfigData;
import com.gmail.maximsmol.CoursePlanner.model.ModelWatcher;
import com.gmail.maximsmol.CoursePlanner.gui.GUIUtils;
import com.gmail.maximsmol.CoursePlanner.gui.winsys.DialogUtils;
import com.gmail.maximsmol.CoursePlanner.gui.winsys.ImportDialog;
import com.gmail.maximsmol.CoursePlanner.gui.winsys.WinsysEvent;
import com.gmail.maximsmol.CoursePlanner.gui.winsys.WinsysEventListener;
import com.gmail.maximsmol.CoursePlanner.gui.Window;
import com.gmail.maximsmol.CoursePlanner.gui.GUIUtils;
import com.gmail.maximsmol.CoursePlanner.gui.ResizablePanel;
import com.gmail.maximsmol.CoursePlanner.gui.ResizableLabel;
import com.gmail.maximsmol.CoursePlanner.model.Course;
import com.gmail.maximsmol.CoursePlanner.model.InvalidModelException;
import com.gmail.maximsmol.CoursePlanner.model.Database;
import com.gmail.maximsmol.CoursePlanner.gui.relativeComponents.RelativeContainer;
import com.gmail.maximsmol.CoursePlanner.model.DescribedElement;

public class BasicDataPane<D extends DescribedElement> extends ResizablePanel {
	public static final long serialVersionUID = 1L;

	protected ResizableLabel label;
	protected D data;


	//
	// Class basics
	//

	//
	// Constructors
	private Runnable setupFrom;
	public BasicDataPane() {
		setupFrom = new Runnable() {
			@Override
			public void run() {
				setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.black));

				label = new ResizableLabel("", JLabel.CENTER);
				GUIUtils.addInsets(label, 2, 2, 2, 2);
				add(label);
			}
		};

		setupFrom.run();
	}

	public BasicDataPane(final D data) {
		this();
		setupFrom(data);

		sizeSelf();
		relayout();
	}

	//
	// `setupFrom`s
	public void setupFrom() { super.setupFrom(); setupFrom.run(); }

	public void setupFrom(final D data) {
		this.data = data;

		label.setText(data.name());
	}

	//
	// Getters
	public D data() {
		return data;
	}

	public ResizableLabel label() {
		return label;
	}


	//
	// Relative component system integration
	//

	@Override
	public void sizeSelf() {
		super.sizeSelf();

		label.sizeSelf();

		setRelativeSize(GUIUtils.horizontalInsetOf(this)+label.getRelativeWidth(), GUIUtils.verticalInsetOf(this)+label.getRelativeHeight());
	}

	@Override
	public void relayout() {
		super.relayout();

		//
		// `label`
		GUIUtils.fillWith(label, this);
		GUIUtils.putInTopleft(label, this);
		label.relayout();
	}

	@Override
	public void resize(final double hK, final double vK) {
		super.resize(hK, vK);

		label.resize(hK, vK);
	}

	@Override
	public void relocate(final double hK, final double vK, final int xOffset, final int yOffset) {
		super.relocate(hK, vK, xOffset, yOffset);

		label.relocate(hK, vK, 0, 0);
	}
}
