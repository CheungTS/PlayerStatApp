package ui;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.imageio.ImageIO;

import model.*;
import model.Event;

// This class is for constructing the GUI of the application and showing the GUI.
public class GUI extends JPanel implements ListSelectionListener {
    private JList list;

    private DefaultListModel listModel;

    private static final String createPlayer = "Create Player";
    private static final String deletePlayer = "Delete Player";
    private static final String addPlayer = "Add Player   ";
    private static final String removePlayer = "Remove Player";
    private JButton createButton;
    private JButton deleteButton;
    private JButton addButton1;
    private JButton addButton2;
    private JButton removeButton1;
    private JButton removeButton2;
    private JButton saveButton;
    private JButton loadButton;
    private JTextField playersName;

    private JScrollPane listScrollPane;

    private TeamPanel team1Pane;
    private TeamPanel team2Pane;
    private JPanel buttonPane;
    private JPanel teamButton;
    private JPanel playerPane;
    private JPanel teamPane;
    private GuiApp app;

    // Constructs the layouts
    public GUI() {
        super(new BorderLayout());
        app = new GuiApp();

        // set up the components
        setList();
        setCreateButton();
        setDeleteButton();
        setAddButton();
        setRemoveButton();
        setSaveLoadButton();

        //Create a panel that uses BoxLayout.
        setButtonPane();
        setTeamButtonPane();

        //Create a panel displays players
        setPlayerPane();

        //Create a panel displays teams
        setTeamPane();

        // Save and load Panel
        JPanel savePane = new JPanel();
        savePane.setLayout(new BoxLayout(savePane,BoxLayout.X_AXIS));
        savePane.add(saveButton);
        savePane.add(loadButton);
        savePane.add(new JButton(new PrintLogAction()));

        add(savePane, BorderLayout.PAGE_START);
        add(playerPane,BorderLayout.LINE_START);
        add(teamButton, BorderLayout.CENTER);
        add(teamPane, BorderLayout.LINE_END);
    }

    // methods that set up the buttons and panels
    private void setList() {
        listModel = new DefaultListModel();

        //Create the player list and put it in a scroll pane.
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(8);
        listScrollPane = new JScrollPane(list);
    }

    private void setCreateButton() {
        createButton = new JButton(createPlayer);
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
        playersName = new JTextField(10);
        playersName.addActionListener(createListener);
        playersName.getDocument().addDocumentListener(createListener);
    }

    private void setDeleteButton() {
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
    }

    private void setAddButton() {
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
    }

    private void setRemoveButton() {
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

    }

    private void setSaveLoadButton() {
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
    }

    private void setButtonPane() {
        buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        buttonPane.add(deleteButton);
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(playersName);
        buttonPane.add(createButton);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
    }

    private void setTeamButtonPane() {
        teamButton = new JPanel();
        teamButton.setLayout(new BoxLayout(teamButton,BoxLayout.PAGE_AXIS));

        teamButton.add(Box.createRigidArea(new Dimension(5, 16)));
        teamButton.add(addButton1);
        teamButton.add(Box.createRigidArea(new Dimension(5, 37)));
        teamButton.add(removeButton1);
        teamButton.add(Box.createRigidArea(new Dimension(5, 16)));
        teamButton.add(addButton2);
        teamButton.add(Box.createRigidArea(new Dimension(5, 40)));
        teamButton.add(removeButton2);
    }

    private void setPlayerPane() {
        playerPane = new JPanel();
        playerPane.setLayout(new BoxLayout(playerPane,BoxLayout.Y_AXIS));
        playerPane.add(listScrollPane);
        playerPane.add(buttonPane);
    }

    private void setTeamPane() {
        team1Pane = new TeamPanel("Team1");
        team2Pane = new TeamPanel("Team2");
        teamPane = new JPanel();
        teamPane.setLayout(new BoxLayout(teamPane, BoxLayout.Y_AXIS));
        teamPane.add(team1Pane);
        teamPane.add(team2Pane);
    }

    // Listeners to buttons
    class SaveListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            EventLog.getInstance().logEvent(new Event("Saved"));
            app.save();
        }
    }

    class LoadListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            EventLog.getInstance().logEvent(new Event("Loaded"));
            app.load();
            updateGUI();
        }
    }

    // Update GUI after loading from files
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
                EventLog.getInstance().logEvent(new Event("Removed player: " + name + " from team 1"));
            } else {
                name = team2Pane.removePlayerFromTeam();
                app.removePlayerFromTeam(name,2);
                EventLog.getInstance().logEvent(new Event("Removed player: " + name + " from team 2"));
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
                EventLog.getInstance().logEvent(new Event("Add player: " + name + " to team 1."));
            } else {
                app.addPlayerToTeam(name,2);
                team2Pane.addPlayerToTeam(name);
                removeButton2.setEnabled(true);
                if (team2Pane.teamIsFull()) {
                    addButton2.setEnabled(false);
                }
                EventLog.getInstance().logEvent(new Event("Add player: " + name + " to team 2."));
            }
        }
    }

    class DeleteListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            String name = (String)list.getSelectedValue();
            EventLog.getInstance().logEvent(new Event("Delete player: " + name));
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

    //This listener is shared by the text field and the create button.
    class CreateListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        public CreateListener(JButton button) {
            this.button = button;
        }

        //Required by ActionListener.
        public void actionPerformed(ActionEvent e) {
            String name = playersName.getText();

            EventLog.getInstance().logEvent(new Event("Create player: " + name));

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

        // Check if the name is already exist.
        // Return true if already exist.
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

    private class PrintLogAction extends AbstractAction {
        PrintLogAction() {
            super("Print log");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {

            JFrame printFrame = new JFrame("Event Log");
            printFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            printFrame.setLayout(new BorderLayout());
            printFrame.setSize(600,600);

            ScreenPrinter lp = new ScreenPrinter();
            lp.printLog(EventLog.getInstance());

            JButton closeButton = new JButton("Close");
            closeButton.addActionListener(e -> {
                printFrame.setVisible(false);
            });

            printFrame.add(lp, BorderLayout.CENTER);
            printFrame.add(closeButton, BorderLayout.PAGE_END);

            printFrame.pack();
            printFrame.setVisible(true);
        }
    }

    //This method is required by ListSelectionListener.
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {
            if (list.getSelectedIndex() == -1) {
                //No selection, disable removePlayer button.
                setButtonFalse();
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

    // Set some of the buttons false as necessary
    private void setButtonFalse() {
        deleteButton.setEnabled(false);
        addButton1.setEnabled(false);
        addButton2.setEnabled(false);
        removeButton1.setEnabled(false);
        removeButton2.setEnabled(false);
    }

    // Create the GUI and show it.
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Collection App");


        //Create and set up the content pane.
        JComponent newContentPane = new GUI();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);

        // Output logs to console when app is closed
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                outputLogs();
                System.exit(0);
            }
        });
    }

    // Output logs to console
    public static void outputLogs() {
        for (Event next : EventLog.getInstance()) {
            System.out.println(next.toString() + "\n\n");
        }
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}