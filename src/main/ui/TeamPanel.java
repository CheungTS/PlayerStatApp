package ui;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

// This class construct the components of team
public class TeamPanel extends JPanel
        implements ListSelectionListener {

    private JList teamList;
    private DefaultListModel listModel;

    private static final int MAX_MEMBER = 3;
    private JTextArea outputTeam;

    public TeamPanel(String name) {
        super(new BorderLayout());

        //Construct Team UI
        listModel = new DefaultListModel();

        outputTeam = new JTextArea(1, 20);
        outputTeam.setText(name);
        outputTeam.setEditable(false);

        teamList = new JList(listModel);
        teamList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        teamList.setSelectedIndex(0);
        teamList.addListSelectionListener(this);
        teamList.setVisibleRowCount(3);
        JScrollPane teamListScrollPane = new JScrollPane(teamList);

        add(outputTeam, BorderLayout.PAGE_START);
        add(teamListScrollPane, BorderLayout.CENTER);
    }

    // Return true if team is empty
    public boolean teamIsEmpty() {
        if (listModel.getSize() == 0) {
            return true;
        } else {
            return false;
        }
    }

    // Return true if team is full
    public boolean teamIsFull() {
        if (listModel.getSize() == MAX_MEMBER) {
            return true;
        } else {
            return false;
        }
    }

    // Remove player from team list
    public String removePlayerFromTeam() {
        String name;
        int index = teamList.getSelectedIndex(); //get selected index
        if (index == -1) { //no selection
            return "";
        } else {           //add after the selected item
            name = (String)teamList.getSelectedValue();
            listModel.remove(index);
        }

        int size = listModel.getSize();

        if (size != 0) { //Select an index.
            if (index == listModel.getSize()) {
                index--;
            }

            teamList.setSelectedIndex(index);
            teamList.ensureIndexIsVisible(index);
        }

        return name;
    }

    // Add player to the team list
    public void addPlayerToTeam(String name) {
        if (name.equals("") || alreadyInList(name) || name == null || teamIsFull()) {
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

    @Override
    public void valueChanged(ListSelectionEvent e) {
        return;
    }
}