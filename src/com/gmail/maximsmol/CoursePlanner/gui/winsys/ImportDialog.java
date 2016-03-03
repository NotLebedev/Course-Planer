package com.gmail.maximsmol.CoursePlanner.gui.winsys;

import java.io.File;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import com.gmail.maximsmol.CoursePlanner.ConfigData;
import com.gmail.maximsmol.CoursePlanner.gui.ResizableTextField;
import com.gmail.maximsmol.CoursePlanner.gui.GUIUtils;
import com.gmail.maximsmol.CoursePlanner.gui.ResizableButton;
import com.gmail.maximsmol.CoursePlanner.gui.ResizableLabel;
import com.gmail.maximsmol.CoursePlanner.gui.DraggablePanel;

public class ImportDialog extends BasicWindow {
	public static final long serialVersionUID = 1L;

	private final static int BROWSE_BUTTON_LEFT_GAP = ConfigData.integer("import_dialog_browse_button_gap");
	private final static int BUTTON_VERTICAL_GAP = ConfigData.integer("import_dialog_ok_button_gap");

	protected final ResizableTextField pathInput = new ResizableTextField();
	protected final ResizableButton okButton = new ResizableButton("Import");
	protected final ResizableButton browseButton = new ResizableButton("Browse"); 
	protected final JFileChooser fileChooser = new JFileChooser();
	protected final ResizableLabel errorLabel = new ResizableLabel("", ResizableLabel.CENTER); 
	protected boolean hasError = false;

	public ImportDialog(final String titleText) {
		super(titleText);

		fileChooser.setAcceptAllFileFilterUsed(true);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setDialogTitle("Select file for import");
		fileChooser.setApproveButtonToolTipText("Use selected file's path");
		fileChooser.setCurrentDirectory( new File(System.getProperty("user.dir")) );
		fileChooser.setApproveButtonMnemonic(KeyEvent.VK_ENTER);
		fileChooser.setControlButtonsAreShown(true);
		fileChooser.setMultiSelectionEnabled(false);

		FileFilter jsonFileFilter = new FileFilter() {
			public boolean accept(File f) {
				return f.isDirectory() || f.getName().endsWith(".json");
			}

			public String getDescription() {
				return "JSON files";
			}
		};
		fileChooser.addChoosableFileFilter(jsonFileFilter);
		fileChooser.setFileFilter(jsonFileFilter);

		errorLabel.setForeground(Color.red);
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				fireWindowEvent(new WinsysEvent(this, WinsysEvent.Type.DIALOG_RESULT, pathInput.getText()));
				requestClose(WinsysEvent.CloseCause.DIALOG_CONFIRM_BUTTON);
			}
		});

		browseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				int res = fileChooser.showDialog(ImportDialog.this, "Import");

				if (res != JFileChooser.APPROVE_OPTION) return;

				String path = fileChooser.getSelectedFile().getPath();
				if (path.startsWith(System.getProperty("user.home")))
					pathInput.setText( path.replaceFirst(System.getProperty("user.home"), "~") ); // ToDo: May not work, depending on existance of slashes in the end of user.home
				else
					pathInput.setText(path);
			}
		});

		mainPane.add(errorLabel);
		mainPane.add(pathInput);
		mainPane.add(browseButton);
		mainPane.add(okButton);

		w = 350;

		relayout();
	}

	public void error(String err) {
		errorLabel.setText(err);
		hasError = true;

		relayout();
		repaint();
	}

	public String importPath() {
		String path = pathInput.getText();
		if (!path.isEmpty() && path.charAt(0) == '~')
			return System.getProperty("user.home") + path.substring(1);
		return path;
	}

	@Override
	public final void relayout() {
		super.relayout();

		if (hasError) {
			errorLabel.sizeSelf();
			errorLabel.setRelativeSize(GUIUtils.insetedWidthOf(mainPane), errorLabel.getRelativeHeight());
		}
		else {
			errorLabel.setRelativeSize(0, 0);
		}
		GUIUtils.putInTopleft(errorLabel, mainPane);
		GUIUtils.putInHorizontalMiddle(errorLabel, mainPane, 0);

		pathInput.sizeSelf();
		browseButton.sizeSelf();

		browseButton.setRelativeSize(browseButton.getRelativeWidth(), Math.max(browseButton.getRelativeHeight(), pathInput.getRelativeHeight()) );
		browseButton.setRelativeLocation(GUIUtils.leftInsetOf(mainPane), GUIUtils.yAfter(errorLabel));
		GUIUtils.putAtRightSide(browseButton, mainPane);

		pathInput.setRelativeSize(GUIUtils.insetedWidthOf(mainPane)-browseButton.getRelativeWidth()-BROWSE_BUTTON_LEFT_GAP, browseButton.getRelativeHeight());
		pathInput.setRelativeLocation(GUIUtils.leftInsetOf(mainPane), GUIUtils.yAfter(errorLabel));

		okButton.sizeSelf();
		okButton.setRelativeLocation(GUIUtils.leftInsetOf(mainPane), GUIUtils.yAfter(pathInput)+BUTTON_VERTICAL_GAP);
		GUIUtils.putInHorizontalMiddle(okButton, mainPane, 0);

		setRelativeSize(getRelativeWidth(), GUIUtils.verticalInsetOf(this)+mainPane.getRelativeY()+GUIUtils.yAfter(okButton));

		revalidate();
	}

	//
	// RelativelyResizable as superclass
	@Override
	public void resize(final double hK, final double vK) {
		super.resize(hK, vK);

		errorLabel.resize(hK, vK);
		browseButton.resize(hK, vK);
		pathInput.resize(hK, vK);
		okButton.resize(hK, vK);
	}

	//
	// RelativelyLocated as superclass
	@Override
	public void relocate(final double hK, final double vK, final int xOffset, final int yOffset) {
		super.relocate(hK, vK, xOffset, yOffset);

		errorLabel.relocate(hK, vK, 0, 0);
		browseButton.relocate(hK, vK, 0, 0);
		pathInput.relocate(hK, vK, 0, 0);
		okButton.relocate(hK, vK, 0, 0);
	}
}