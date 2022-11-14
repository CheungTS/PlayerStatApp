package ui;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// This class construct the components of team
public class ListTeam extends JPanel
        implements ListSelectionListener {

    private JList teamList;
    private DefaultListModel listModel;

    private static final String removePlayer = "Remove Player";
    private static final int MAX_MEMBER = 3;
    private JButton removeButton;
    private JTextArea outputTeam;

    public ListTeam() {
        super(new BorderLayout());

        //Construct Team UI
        listModel = new DefaultListModel();

        //Create the player list and put it in a scroll pane.
        outputTeam = new JTextArea(1, 20);
        outputTeam.setEditable(false);

        teamList = new JList(listModel);
        teamList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        teamList.setSelectedIndex(0);
        teamList.addListSelectionListener(this);
        teamList.setVisibleRowCount(3);
        JScrollPane teamListScrollPane = new JScrollPane(teamList);

        removeButton = new JButton(removePlayer);
        removeButton.setActionCommand(removePlayer);
        removeButton.addActionListener(new RemoveListener());
        removeButton.setEnabled(false);

        //Create a panel that uses BoxLayout.

        add(outputTeam, BorderLayout.PAGE_START);
        add(teamListScrollPane, BorderLayout.CENTER);
        //add(removeButton, BorderLayout.PAGE_END);
    }

    public boolean teamIsEmpty() {
        if (listModel.getSize() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean teamIsFull() {
        if (listModel.getSize() == MAX_MEMBER) {
            return true;
        } else {
            return false;
        }
    }

    // Add player to the team list
    public void addPlayerToTeam(String name) {
        if (name.equals("") || alreadyInList(name) || name == null) {
            Toolkit.getDefaultToolkit().beep();
            return;
        }

        int index = teamList.getSelectedIndex(); //get selected index
        if (index == -1) { //no selection, so insert at beginning
            index = 0;
        } else {           //add after the selected item
            index++;
        }

        listModel.insertElementAt(name, index);

        teamList.setSelectedIndex(index);
        teamList.ensureIndexIsVisible(index);
    }

    protected boolean alreadyInList(String name) {
        return listModel.contains(name);
    }

    class RemoveListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            String name = (String)teamList.getSelectedValue();

            int index = teamList.getSelectedIndex();
            listModel.remove(index);

            int size = listModel.getSize();

            if (size == 0) { //Nobody's left, disable firing.
                removeButton.setEnabled(false);

            } else { //Select an index.
                if (index == listModel.getSize()) {
                    //removed item in last position
                    index--;
                }

                teamList.setSelectedIndex(index);
                teamList.ensureIndexIsVisible(index);
            }
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (teamList.getSelectedIndex() == -1) {
                //No selection, disable removePlayer button.
                removeButton.setEnabled(false);

            } else {
                //Selection, enable the removePlayer button.
                removeButton.setEnabled(true);
            }
        }
    }
    /*

    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("ListDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new ListTeam();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
    */
}