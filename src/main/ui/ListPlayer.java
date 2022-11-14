package ui;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Partially copy from The Java™ Tutorials
// https://docs.oracle.com/javase/tutorial/uiswing/components/table.html
public class ListPlayer extends JPanel
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
    private JTextField playersName;

    private ListTeam team1Pane;
    private ListTeam team2Pane;

    private GuiApp app;

    @SuppressWarnings("checkstyle:MethodLength")
    public ListPlayer() {
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

        deleteButton = new JButton(deletePlayer);
        deleteButton.setActionCommand(deletePlayer);
        deleteButton.addActionListener(new DeleteListener());
        deleteButton.setEnabled(false);

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

        removeButton1 = new JButton(removePlayer);
        removeButton1.setActionCommand(removePlayer);
        //removeButton1.addActionListener(new RemoveListener());
        removeButton2 = new JButton(removePlayer);
        removeButton2.setActionCommand(removePlayer);
        //removeButton1.addActionListener(new RemoveListener());

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
        playerPane.setLayout(new BoxLayout(playerPane,BoxLayout.PAGE_AXIS));
        playerPane.add(listScrollPane);
        playerPane.add(buttonPane);

        //Create a panel displays teams
        team1Pane = new ListTeam();
        team2Pane = new ListTeam();
        JPanel teamPane = new JPanel();

        teamPane.setLayout(new BoxLayout(teamPane, BoxLayout.Y_AXIS));
        teamPane.add(team1Pane);
        teamPane.add(team2Pane);

        add(playerPane,BorderLayout.LINE_START);
        add(teamButton, BorderLayout.CENTER);
        add(teamPane, BorderLayout.LINE_END);
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

    class AddListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            String name = (String)list.getSelectedValue();
            if (e.getSource() == addButton1) {
                app.addPlayerToTeam(name,1);
                team1Pane.addPlayerToTeam(name);
                if (team1Pane.teamIsFull()) {
                    addButton1.setEnabled(false);
                }
            } else {
                app.addPlayerToTeam(name,2);
                team2Pane.addPlayerToTeam(name);
                if (team2Pane.teamIsFull()) {
                    addButton2.setEnabled(false);
                }
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
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (list.getSelectedIndex() == -1) {
                //No selection, disable removePlayer button.
                deleteButton.setEnabled(false);
                addButton1.setEnabled(false);
                addButton2.setEnabled(false);

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
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("ListDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new ListPlayer();
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