package ui;

import java.awt.*;

import javax.swing.*;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import model.Event;
import model.EventLog;

/**
 * Represents a screen printer for printing event log to screen.
 */
public class ScreenPrinter extends Panel {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 400;
    private JTextArea logArea;

    /**
     * Constructor sets up window in which log will be printed on screen
     */
    public ScreenPrinter() {
        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        add(scrollPane);
        setVisible(true);
    }

    public void printLog(EventLog el) {
        for (Event next : el) {
            logArea.setText(logArea.getText() + next.toString() + "\n\n");
        }

        repaint();
    }
}
