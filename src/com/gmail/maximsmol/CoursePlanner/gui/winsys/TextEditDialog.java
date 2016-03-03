package com.gmail.maximsmol.CoursePlanner.gui.winsys;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFileChooser;

import com.gmail.maximsmol.CoursePlanner.ConfigData;
import com.gmail.maximsmol.CoursePlanner.gui.ResizableTextArea;
import com.gmail.maximsmol.CoursePlanner.gui.GUIUtils;
import com.gmail.maximsmol.CoursePlanner.gui.ResizableButton;
import com.gmail.maximsmol.CoursePlanner.gui.ResizableLabel;
import com.gmail.maximsmol.CoursePlanner.gui.DraggablePanel;

public class TextEditDialog extends BasicWindow {
	public static final long serialVersionUID = 1L;

	private final static int BUTTON_GAP = ConfigData.integer("text_edit_dialog_button_gap");

	protected final ResizableTextArea editArea = new ResizableTextArea();
	protected final ResizableButton okButton = new ResizableButton("Import");
	protected final ResizableLabel errorLabel = new ResizableLabel("", ResizableLabel.CENTER); 
	protected boolean hasError = false;

	public TextEditDialog(final String titleText) {
		super(titleText);

		errorLabel.setForeground(Color.red);
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				fireWindowEvent(new WinsysEvent(this, WinsysEvent.Type.DIALOG_RESULT, editArea.getText()));
				requestClose(WinsysEvent.CloseCause.DIALOG_CONFIRM_BUTTON);
			}
		});

		mainPane.add(editArea);
		mainPane.add(errorLabel);
		mainPane.add(okButton);

		relayout();
	}

	public void error(String err) {
		errorLabel.setText(err);
		hasError = true;

		relayout();
		repaint();
	}

	public void setText(String txt) {
		editArea.setText(txt);
	}

	public String text() {
		return editArea.getText();
	}

	@Override
	public final void relayout() {
		super.relayout();

		okButton.sizeSelf();

		if (hasError) {
			errorLabel.sizeSelf();
			errorLabel.setRelativeSize(GUIUtils.insetedWidthOf(mainPane), errorLabel.getRelativeHeight());
		}
		else errorLabel.setRelativeSize(0, 0);
		errorLabel.setRelativeLocation(GUIUtils.leftInsetOf(mainPane), GUIUtils.yAfter(editArea)+BUTTON_GAP);

		editArea.setRelativeSize(GUIUtils.insetedWidthOf(mainPane), GUIUtils.insetedHeightOf(mainPane)-okButton.getRelativeHeight()-BUTTON_GAP);
		GUIUtils.putInTopleft(editArea, mainPane);

		okButton.setRelativeLocation(GUIUtils.leftInsetOf(mainPane), GUIUtils.yAfter(errorLabel));
		GUIUtils.putInHorizontalMiddle(okButton, mainPane, 0);

		revalidate();
	}

	//
	// RelativelyResizable as superclass
	@Override
	public void resize(final double hK, final double vK) {
		super.resize(hK, vK);

		editArea.resize(hK, vK);
		errorLabel.resize(hK, vK);
		okButton.resize(hK, vK);
	}

	//
	// RelativelyLocated as superclass
	@Override
	public void relocate(final double hK, final double vK, final int xOffset, final int yOffset) {
		super.relocate(hK, vK, xOffset, yOffset);

		editArea.relocate(hK, vK, 0, 0);
		errorLabel.relocate(hK, vK, 0, 0);
		okButton.relocate(hK, vK, 0, 0);
	}
}