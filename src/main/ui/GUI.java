package ui;


import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import model.*;

// Partially copy from The Java™ Tutorials
// https://docs.oracle.com/javase/tutorial/uiswing/components/table.html

//TODO a visual component (displaying an image in a splash screen when your application starts)
//TODO Add a section at the end of your README.md file
public class GUI extends JPanel
        implements ListSelectionListener {
    private JList list;

    private DefaultListModel listModel;

    private static final String createPlayer = "Create Player";
    private static final String deletePlayer = "Delete Player";
    private static final String addPlayer = "Add Player   ";
    private static final String removePlayer = "Remove Player";
    private JButton deleteButton;
    private JButton addButton1;
    private JButton addButton2;
    private JButton removeButton1;
    private JButton removeButton2;
    private JButton saveButton;
    private JButton loadButton;
    private JTextField playersName;

    private TeamPanel team1Pane;
    private TeamPanel team2Pane;

    private GuiApp app;

    @SuppressWarnings("checkstyle:MethodLength")
    public GUI() {
        super(new BorderLayout());

        app = new GuiApp();

        // Construct player UI
        listModel = new DefaultListModel();

        //Create the player list and put it in a scroll pane.
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(8);
        JScrollPane listScrollPane = new JScrollPane(list);

        JButton createButton = new JButton(createPlayer);
        CreateListener createListener = new CreateListener(createButton);
        createButton.setActionCommand(createPlayer);
        createButton.addActionListener(createListener);
        createButton.setEnabled(false);
        try {
            Image img = ImageIO.read(getClass().getResource("images/create.jpg"));
            createButton.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println(ex);
        }

        deleteButton = new JButton(deletePlayer);
        deleteButton.setActionCommand(deletePlayer);
        deleteButton.addActionListener(new DeleteListener());
        deleteButton.setEnabled(false);
        try {
            Image img = ImageIO.read(getClass().getResource("images/delete.jpg"));
            deleteButton.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println(ex);
        }


        playersName = new JTextField(10);
        playersName.addActionListener(createListener);
        playersName.getDocument().addDocumentListener(createListener);

        addButton1 = new JButton(addPlayer);
        addButton1.setActionCommand(addPlayer);
        addButton1.addActionListener(new AddListener());
        addButton2 = new JButton(addPlayer);
        addButton2.setActionCommand(addPlayer);
        addButton2.addActionListener(new AddListener());
        addButton1.setEnabled(false);
        addButton2.setEnabled(false);
        try {
            Image img = ImageIO.read(getClass().getResource("images/add.jpg"));
            addButton1.setIcon(new ImageIcon(img));
            addButton2.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println(ex);
        }

        removeButton1 = new JButton(removePlayer);
        removeButton1.setActionCommand(removePlayer);
        removeButton1.addActionListener(new RemoveListener());
        removeButton2 = new JButton(removePlayer);
        removeButton2.setActionCommand(removePlayer);
        removeButton2.addActionListener(new RemoveListener());
        removeButton1.setEnabled(false);
        removeButton2.setEnabled(false);

        try {
            Image img = ImageIO.read(getClass().getResource("images/remove.jpg"));
            removeButton1.setIcon(new ImageIcon(img));
            removeButton2.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println(ex);
        }

        saveButton = new JButton("Save");
        saveButton.setActionCommand("Save");
        saveButton.addActionListener(new SaveListener());
        loadButton = new JButton("Load");
        loadButton.setActionCommand("Load");
        loadButton.addActionListener(new LoadListener());

        try {
            Image img = ImageIO.read(getClass().getResource("images/save.jpg"));
            saveButton.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        try {
            Image img = ImageIO.read(getClass().getResource("images/load.jpg"));
            loadButton.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println(ex);
        }

        //Create a panel that uses BoxLayout.
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane,
                BoxLayout.LINE_AXIS));
        buttonPane.add(deleteButton);
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(playersName);
        buttonPane.add(createButton);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        JPanel teamButton = new JPanel();
        teamButton.setLayout(new BoxLayout(teamButton,BoxLayout.PAGE_AXIS));

        teamButton.add(Box.createRigidArea(new Dimension(5, 16)));
        teamButton.add(addButton1);
        teamButton.add(Box.createRigidArea(new Dimension(5, 37)));
        teamButton.add(removeButton1);
        teamButton.add(Box.createRigidArea(new Dimension(5, 16)));
        teamButton.add(addButton2);
        teamButton.add(Box.createRigidArea(new Dimension(5, 40)));
        teamButton.add(removeButton2);

        //Create a panel displays players
        JPanel playerPane = new JPanel();
        playerPane.setLayout(new BoxLayout(playerPane,BoxLayout.Y_AXIS));
        playerPane.add(listScrollPane);
        playerPane.add(buttonPane);

        //Create a panel displays teams
        team1Pane = new TeamPanel("Team1");
        team2Pane = new TeamPanel("Team2");
        JPanel teamPane = new JPanel();
        teamPane.setLayout(new BoxLayout(teamPane, BoxLayout.Y_AXIS));
        teamPane.add(team1Pane);
        teamPane.add(team2Pane);

        // Save and load Panel
        JPanel savePane = new JPanel();
        savePane.setLayout(new BoxLayout(savePane,BoxLayout.X_AXIS));
        savePane.add(saveButton);
        savePane.add(loadButton);

        add(savePane, BorderLayout.PAGE_START);
        add(playerPane,BorderLayout.LINE_START);
        add(teamButton, BorderLayout.CENTER);
        add(teamPane, BorderLayout.LINE_END);
    }

    class SaveListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            app.save();
        }
    }

    class LoadListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            app.load();
            updateGUI();
        }
    }

    public void updateGUI() {
        String name;
        for (Player p: app.getPlayerList()) {
            listModel.addElement(p.getName());
        }
        Team team1 = app.getTeam1();
        Team team2 = app.getTeam2();
        for (int i = 0; i < team1.size(); i++) {
            name = team1.getPlayer(i).getName();
            team1Pane.addPlayerToTeam(name);
        }
        for (int i = 0; i < team2.size(); i++) {
            name = team2.getPlayer(i).getName();
            team2Pane.addPlayerToTeam(name);
        }
        return;
    }

    class RemoveListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String name;
            if (e.getSource() == removeButton1) {
                name = team1Pane.removePlayerFromTeam();
                app.removePlayerFromTeam(name,1);
            } else {
                name = team2Pane.removePlayerFromTeam();
                app.removePlayerFromTeam(name,2);
            }

            if (team1Pane.teamIsEmpty()) {
                removeButton1.setEnabled(false);
            }
            if (team2Pane.teamIsEmpty()) {
                removeButton2.setEnabled(false);
            }
        }
    }

    class AddListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            String name = (String)list.getSelectedValue();
            if (e.getSource() == addButton1) {
                app.addPlayerToTeam(name,1);
                team1Pane.addPlayerToTeam(name);
                removeButton1.setEnabled(true);
                if (team1Pane.teamIsFull()) {
                    addButton1.setEnabled(false);
                }
            } else {
                app.addPlayerToTeam(name,2);
                team2Pane.addPlayerToTeam(name);
                removeButton2.setEnabled(true);
                if (team2Pane.teamIsFull()) {
                    addButton2.setEnabled(false);
                }
            }
        }
    }

    class DeleteListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //This method can be called only if
            //there's a valid selection
            //so go ahead and remove whatever's selected.

            String name = (String)list.getSelectedValue();
            app.removePlayer(name);

            int index = list.getSelectedIndex();
            listModel.remove(index);

            int size = listModel.getSize();

            if (size == 0) { //Nobody's left, disable firing.
                deleteButton.setEnabled(false);
                addButton1.setEnabled(false);
                addButton2.setEnabled(false);
            } else { //Select an index.
                if (index == listModel.getSize()) {
                    //removed item in last position
                    index--;
                }

                list.setSelectedIndex(index);
                list.ensureIndexIsVisible(index);
            }

        }
    }

    //This listener is shared by the text field and the hire button.
    class CreateListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        public CreateListener(JButton button) {
            this.button = button;
        }

        //Required by ActionListener.
        public void actionPerformed(ActionEvent e) {
            String name = playersName.getText();

            //User didn't type in a unique name...
            if (name.equals("") || alreadyInList(name)) {
                Toolkit.getDefaultToolkit().beep();
                playersName.requestFocusInWindow();
                playersName.selectAll();
                return;
            }


            int index = list.getSelectedIndex(); //get selected index
            if (index == -1) { //no selection, so insert at beginning
                index = 0;
            } else {           //add after the selected item
                index++;
            }

            listModel.insertElementAt(name, index);
            //If we just wanted to add to the end, we'd do this:
            //listModel.addElement(employeeName.getText());

            app.createPlayer(name);

            //Reset the text field.
            playersName.requestFocusInWindow();
            playersName.setText("");

            //Select the new item and make it visible.
            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);
        }

        //This method tests for string equality. You could certainly
        //get more sophisticated about the algorithm.  For example,
        //you might want to ignore white space and capitalization.
        protected boolean alreadyInList(String name) {
            return listModel.contains(name);
        }

        //Required by DocumentListener.
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

        //Required by DocumentListener.
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

        //Required by DocumentListener.
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }

        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }
    }

    //This method is required by ListSelectionListener.
    @SuppressWarnings("checkstyle:MethodLength")
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {
            if (list.getSelectedIndex() == -1) {
                //No selection, disable removePlayer button.
                deleteButton.setEnabled(false);
                addButton1.setEnabled(false);
                addButton2.setEnabled(false);
                removeButton1.setEnabled(false);
                removeButton2.setEnabled(false);
            } else {
                //Selection, enable the removePlayer button.
                deleteButton.setEnabled(true);
                addButton1.setEnabled(true);
                addButton2.setEnabled(true);
            }
        }
        if (!team1Pane.teamIsFull()) {
            addButton1.setEnabled(true);
        }
        if (!team2Pane.teamIsFull()) {
            addButton2.setEnabled(true);
        }
        if (!team1Pane.teamIsEmpty()) {
            removeButton1.setEnabled(true);
        }
        if (!team2Pane.teamIsEmpty()) {
            removeButton2.setEnabled(true);
        }
    }

    // Create the GUI and show it.
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("ListDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new GUI();
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

}