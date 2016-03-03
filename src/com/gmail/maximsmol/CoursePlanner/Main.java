package com.gmail.maximsmol.CoursePlanner;

import java.util.ArrayList;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import javax.swing.JButton;

import com.gmail.maximsmol.CoursePlanner.gui.winsys.*;
import com.gmail.maximsmol.CoursePlanner.gui.Window;
import com.gmail.maximsmol.CoursePlanner.gui.cards.CourseCard;
import com.gmail.maximsmol.CoursePlanner.gui.cards.SkillDictionary;
import com.gmail.maximsmol.CoursePlanner.model.*;

public final class Main {
    private Main() {}

    public static void main(final String... args) throws FileNotFoundException, IOException {
        ConfigData.read(new FileReader("res/config.json"));
        Database.read( new File(ConfigData.str("database_path")) );

    	final ArrayList<CourseCard> cards = new ArrayList<CourseCard>();

        final JButton newCardBtn = new JButton("Add new card");
        newCardBtn.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                final String[] courseOptions = Course.courses.keySet().toArray(new String[Course.courses.size()]);
                final String courseId = (String) JOptionPane.showInputDialog(Window.win, "Select the course ID", "New course card", JOptionPane.PLAIN_MESSAGE, null, courseOptions, courseOptions[0]);
                if (courseId == null) return;

                final CourseCard card = new CourseCard(Course.courses.get(courseId));
                card.setRelativeLocation(300 - Window.layoutManager.getXOffset(), 100 - Window.layoutManager.getYOffset());

                cards.add(card);
                Window.win.add(card);

                Window.win.revalidate();
                Window.win.repaint();
            }

            @Override
            public void mousePressed(final MouseEvent e) { /* Required override */ }
            @Override
            public void mouseReleased(final MouseEvent e) { /* Required override */ }
            @Override
            public void mouseEntered(final MouseEvent e) { /* Required override */ }
            @Override
            public void mouseExited(final MouseEvent e) { /* Required override */ }
        });
        newCardBtn.setSize(150, 30);
        newCardBtn.setLocation(Window.win.getWidth()/2-newCardBtn.getWidth()/2, 0);
        Window.win.add(newCardBtn);

        //
        // SkillDictionary
        final SkillDictionary dict = new SkillDictionary();
        dict.setRelativeSize(250, Window.win.getHeight());
        dict.setLocation(0, 0);
        Window.win.add(dict);

        //
        // Database write
    	Runtime.getRuntime().addShutdownHook(new Thread() {
    		@Override
    		public void run() {
    			try {
    				Database.prettyPrint(new FileWriter("res/test-written.json"));
    			}
    			catch (IOException e) {
    				System.err.println("IO!!!");
    				return;
    			}
    		}
    	});

        //
        // Wrapping up
        Window.win.setVisible(true);
    }
}
