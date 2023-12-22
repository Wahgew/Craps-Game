package controller;

import model.BankBetLogic;
import model.CrapsLogic;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Menubar that has menu action that
 * changes and initialized the state of the game.
 *
 * @author Peter Madin
 * @version 0.0.3  December 7, 2023
 */
public class MenuBar {
    /**
     * Manages actions and components for a menu bar in the application.
     */
    private final PropertyChangeSupport changes = new PropertyChangeSupport(this);

    /**
     * String constant representing the "Exit" action.
     */
    private static final String EXIT_STRING = "Exit";

    /**
     * String constant representing the "Rules" action.
     */
    private static final String RULES_STRING = "Rules";

    /**
     * String constant representing the "About" action.
     */
    private static final String ABOUT_STRING = "About";

    /**
     * String constant representing the "Shortcut" action.
     */
    private static final String SHORT_STRING = "Shortcut";

    /**
     * String constant representing the "Start" action.
     */
    private static final String START_STRING = "Start";

    /**
     * String constant representing the "Rest" action.
     */
    private static final String REST_STRING = "Rest";

    /**
     * The menu bar associated with this MenuManager.
     */
    private JMenuBar menuBar;

    /**
     * Action representing the "Start" action.
     */
    private Action myStartAction;

    /**
     * Action representing the "Exit" action.
     */
    private Action myExitAction;

    /**
     * Action representing the "Rules" action.
     */
    private Action myRulesAction;

    /**
     * Action representing the "About" action.
     */
    private Action myAboutAction;

    /**
     * Action representing the "Shortcut" action.
     */
    private Action myShortcutAction;

    /**
     * Action representing the "Reset" action.
     */
    private Action myResetAction;

    /**
     * Constructs a MenuBar instance for the given frame.
     *
     * @param theFrame The JFrame to associate the menu bar with.
     */
    public MenuBar(final JFrame theFrame) {
        setupActions(theFrame);
        createMenuBar();
        theFrame.setJMenuBar(menuBar);
    }

    /**
     * Sets up actions for menu items and functions.
     *
     * @param theMainFrame The main JFrame associated with the actions.
     */
    private void setupActions(JFrame theMainFrame) {
        myStartAction = new AbstractAction(START_STRING, new ImageIcon("./images/start-mark.png")) {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                CrapsLogic craps = CrapsLogic.getCrapsInstance();
                BankBetLogic bank = BankBetLogic.getBankBetInstance();
                if (bank.getBankSet() && bank.getBetSet()) {
                    craps.setGameActive(true);
                    myResetAction.setEnabled(true);
                    myStartAction.setEnabled(false);
                    changes.firePropertyChange("start",false,true);
                } else {
                    String message = "<html>"
                            + "<div style='text-align:center;'>"
                            + "<img src='file:images/stop-mark.png' width='500' height='375'><br>"
                            + "</div>"
                            + "<b>Please set up the bank amount and betting before starting the game.</b>";

                    JOptionPane.showMessageDialog(theMainFrame, message, "Bank", JOptionPane.WARNING_MESSAGE);
                }
            }
        };

        myStartAction.putValue(Action.SHORT_DESCRIPTION, EXIT_STRING);
        myStartAction.putValue(Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke('S',
                        InputEvent.CTRL_DOWN_MASK));


        myResetAction = new AbstractAction(REST_STRING, new ImageIcon("./images/reset-mark.png")) {
            @Override
            public void actionPerformed(ActionEvent e) {
            myStartAction.setEnabled(true);
            myResetAction.setEnabled(false);
            CrapsLogic craps = CrapsLogic.getCrapsInstance();
            BankBetLogic bank = BankBetLogic.getBankBetInstance();
            craps.hardReset();
            bank.setBank(false);
            bank.setBet(false);
            bank.resetBankAndBet();
            }
        };

        myResetAction.putValue(Action.SHORT_DESCRIPTION, EXIT_STRING);
        myResetAction.putValue(Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke('R',
                        InputEvent.CTRL_DOWN_MASK));

        myExitAction = new AbstractAction(EXIT_STRING, new ImageIcon("./images/x-mark.png")) {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                int closeOption = JOptionPane.showConfirmDialog(theMainFrame,
                        "Are you sure you want to exit?",
                        "Confirm Exit.",
                        JOptionPane.YES_NO_OPTION
                );

                if (closeOption == JOptionPane.YES_NO_OPTION) {
                    theMainFrame.dispatchEvent(new WindowEvent(theMainFrame, WindowEvent.WINDOW_CLOSING));
                }
            }
        };

        myExitAction.putValue(Action.SHORT_DESCRIPTION, EXIT_STRING);
        myExitAction.putValue(Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke('E',
                        InputEvent.CTRL_DOWN_MASK));

        myAboutAction = new AbstractAction(ABOUT_STRING, new ImageIcon("./images/info-mark.png")) {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                String message = "<html>"
                        + "<div style='text-align:center;'>"
                        + "<img src='file:images/author-image.png' width='500' height='500'><br>"
                        + "</div>"
                        + "<b>Application:</b> Craps Game<br>"
                        + "<b>Version:</b> 1.0.0<br>"
                        + "<b>Author:</b> Peter Madin<br>"
                        + "<b>Java Version:</b> " + System.getProperty("java.version") + "<br>"
                        + "<b>Description:</b> This application allows the user to play a game of craps with betting.<br>";

                JOptionPane.showMessageDialog(theMainFrame, message, "About", JOptionPane.INFORMATION_MESSAGE);
            }
        };

        myAboutAction.putValue(Action.SHORT_DESCRIPTION, ABOUT_STRING);
        myAboutAction.putValue(Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke('A',
                        InputEvent.CTRL_DOWN_MASK));

        myRulesAction = new AbstractAction(RULES_STRING, new ImageIcon("./images/question-mark.png")) {
            @Override
            public void actionPerformed(final ActionEvent e) {
                String message = "<html>" +
                        "<p>The rules of the Game of craps are as follows:<br>"
                        + "<br>"
                        + "A player rolls two dice where each die has six faces in the usual way (values 1 through 6).<br>"
                        + "After the dice have come to rest the sum of the two upward faces is calculated.<br>"
                        + "The first roll/throw<br>"
                        + "&emsp;&bull; If the sum is 7 or 11 on the first throw the roller/player wins.<br>"
                        + "&emsp;&bull; If the sum is 2, 3 or 12 the roller/player loses, that is the house wins.<br>"
                        + "&emsp;&bull; If the sum is 4, 5, 6, 8, 9, or 10, that sum becomes the roller/player's 'point'.<br>"
                        + "Continue rolling given the player's point<br>"
                        + "Now the player must roll the 'point' total before rolling a 7 in order to win.<br>"
                        + "If they roll a 7 before rolling the point value they got on the first roll the roller/player loses (the 'house' wins).</p>"
                        + "<b>NOTE THAT ALL BETS ARE FINAL!</b>"
                        + "</html>";
                JOptionPane.showMessageDialog(theMainFrame, message, "Rules", JOptionPane.INFORMATION_MESSAGE);
            }
        };

        myRulesAction.putValue(Action.SHORT_DESCRIPTION, RULES_STRING);
        myRulesAction.putValue(Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke('T',
                        InputEvent.CTRL_DOWN_MASK));

        myShortcutAction = new AbstractAction(SHORT_STRING, new ImageIcon("./images/bolt-mark.png")) {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                String message = "<html>"
                        + "<b>Shortcuts:</b><br>"
                        + "Start: CTRL + S<br>"
                        + "Reset: CTRL + R<br>"
                        + "Exit: CTRL + E<br>"
                        + "About: CTRL + A<br>"
                        + "Rules: CTRL + T<br>"
                        + "Shortcut: CTRL + Q<br>"
                        + "Roll: ALT + R<br>"
                        + "Play Again: ALT + P<br>";
                JOptionPane.showMessageDialog(theMainFrame, message, "Shortcuts", JOptionPane.INFORMATION_MESSAGE);
            }
        };

        myShortcutAction.putValue(Action.SHORT_DESCRIPTION, SHORT_STRING);
        myShortcutAction.putValue(Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke('Q',
                        InputEvent.CTRL_DOWN_MASK));

    }

    /**
     * Creates the menu bar and
     * adds menus and actions to it.
     */
    public void createMenuBar() {
        menuBar = new JMenuBar();
        final JMenu gameMenu = new JMenu("Game");
        final JMenu helpBar = new JMenu("Help");

        gameMenu.add(myStartAction);
        gameMenu.add(myResetAction);
        gameMenu.add(myExitAction);
        myResetAction.setEnabled(false);

        helpBar.add(myAboutAction);
        helpBar.add(myRulesAction);
        helpBar.add(myShortcutAction);

        menuBar.add(gameMenu);
        menuBar.add(helpBar);
    }

    /**
     * Adds a PropertyChangeListener to listen for property change events.
     *
     * @param theL The PropertyChangeListener to add.
     */
    public void addPropertyChangeListener(PropertyChangeListener theL) {
        changes.addPropertyChangeListener(theL);
    }
}