package controller;

import model.BankBetLogic;
import model.CrapsLogic;
import res.R;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Winning panel which displays the total wins of
 * the player and house.
 * Also get notification of changes from the dice
 *
 * @author Peter Madin
 * @version 0.0.5  December 5, 2023
 */
public class WinPanel extends JPanel implements PropertyChangeListener {
    /**
     * The panel responsible for displaying win statistics in the UI.
     */
    private final JPanel myWinPanel;

    /**
     * The main frame used for the Craps game UI.
     */
    private final JFrame myMainFrame;

    /**
     * The text field displaying the number of wins for the player.
     */
    private final JTextField myPlayerWins;

    /**
     * The text field displaying the number of wins for the house.
     */
    private final JTextField myHouseWins;

    /**
     * An array holding labels used for UI elements.
     */
    private final String[] myLabels;

    /**
     * An array containing text fields used in the UI.
     */
    private final JTextField[] myTextFields;

    /**
     * The instance of the CrapsLogic class handling the game's logic.
     */
    private final CrapsLogic myCraps;

    /**
     * Constructor for the WinPanel class.
     *
     * @param theFrame The main frame of the Craps game UI.
     */
    public WinPanel(final JFrame theFrame) {
        myMainFrame = theFrame;
        myCraps = CrapsLogic.getCrapsInstance();
        if (myCraps != null) {
            myCraps.addPropertyChangeListener(this);
        }
        myWinPanel = new JPanel(new BorderLayout());
        myPlayerWins = new JTextField(10);
        myHouseWins = new JTextField(10);
        myTextFields = new JTextField[] {myPlayerWins, myHouseWins};
        myLabels = new String[] {"Player Win Total: ", "House win Total: "};
        layoutComponents();
    }

    /**
     * Arranges and adds components to the WinPanel layout.
     */
    private void layoutComponents() {
        setLayout(new BorderLayout());
        myWinPanel.setLayout(new BorderLayout());
        myWinPanel.add(makeHeader(), BorderLayout.NORTH);
        myWinPanel.add(makeWinPanel(myLabels, myTextFields));

        add(myWinPanel);
    }


    /**
     * Creates the header panel displaying the title "Win Totals".
     *
     * @return The header panel for the WinPanel.
     */
    private JPanel makeHeader() {
        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER));
        header.setBackground(R.Colors.BACKGROUND);
        JLabel titleLabel = new JLabel("Win Totals");
        titleLabel.setForeground(R.Colors.HEADER);
        header.add(titleLabel);
        return header;
    }

    /**
     * Creates the panel containing win statistics for the player and house.
     *
     * @param theLabels String array containing labels for the win.
     * @param theTextField Array of JTextFields containing win.
     * @return The panel with win statistics for the WinPanel.
     */
    private JPanel makeWinPanel(final String[] theLabels, JTextField[] theTextField) {
        final JPanel panel = new JPanel(new GridLayout(myLabels.length, 1));

        panel.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
        panel.setBackground(R.Colors.OUTLINES);
        for (int i = 0; i < 2; i++) {
            addRollPanel(theLabels[i], theTextField[i], panel);
        }
        return panel;
    }

    /**
     * Adds a roll panel containing label and text field to the provided panel.
     *
     * @param theLabel The label for the roll panel.
     * @param theTextField The text field displaying roll information.
     * @param thePanel The panel where the roll panel is added.
     */
    private void addRollPanel(final String theLabel, final JTextField theTextField, final JPanel thePanel) {
        final JPanel winInfo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        winInfo.setBackground(R.Colors.BACKGROUND);
        final JLabel l = new JLabel(theLabel);
        l.setForeground(R.Colors.TEXT_LABEL);

        theTextField.setHorizontalAlignment(SwingConstants.CENTER);
        theTextField.setEditable(false);
        theTextField.setFocusable(false);
        theTextField.setForeground(R.Colors.NUMBERS);
        theTextField.setBackground(R.Colors.BACKGROUND);
        winInfo.add(l);
        winInfo.add(theTextField);
        thePanel.add(winInfo);
    }


    /**
     * Handles property change events triggered during the Craps game.
     *
     * @param theEvt The PropertyChangeEvent object indicating a change in properties.
     */
    @Override
    public void propertyChange(PropertyChangeEvent theEvt) {
        if ("diceRoll".equals(theEvt.getPropertyName())) {
            myPlayerWins.setText(String.valueOf(myCraps.getPlayerWins()));
            myHouseWins.setText(String.valueOf(myCraps.getHouseWins()));
        }


        if ("winStatus".equals(theEvt.getPropertyName())) {
            if ((boolean) theEvt.getNewValue()) {
                myPlayerWins.setText(String.valueOf(myCraps.getPlayerWins()));
                myHouseWins.setText(String.valueOf(myCraps.getHouseWins()));
                BankBetLogic bank = BankBetLogic.getBankBetInstance();
                bank.bettingWinLost();
            }
        }

        if ("winDisplay".equals(theEvt.getPropertyName())) {
            if ((boolean) theEvt.getNewValue()) {
                String message = "<html>"
                        + "<div style='text-align:center;'>"
                        + "<h1>Congratulations!</h1>"
                        + "</div>"
                        + "<p>You have won!</p>";
                JOptionPane.showMessageDialog(myMainFrame, message, "Winner", JOptionPane.INFORMATION_MESSAGE);
            } else {
                String message = "<html>"
                        + "<div style='text-align:center;'>"
                        + "<h1>Unfortunate!</h1>"
                        + "</div>"
                        + "<p>You have lost!</p>";
                JOptionPane.showMessageDialog(myMainFrame, message, "Loser", JOptionPane.INFORMATION_MESSAGE);
            }
        }

        if ("displayReset".equals(theEvt.getPropertyName())) {
            myPlayerWins.setText("");
            myHouseWins.setText("");
        }

        if ("gameOver".equals(theEvt.getPropertyName())) {
            if ((boolean) theEvt.getNewValue()) {
                String message = "<html>"
                        + "<div style='text-align:center;'>"
                        + "<h1>That's Unfortunate!</h1>"
                        + "</div>"
                        + "<p>You have no more money</p>"
                        + "<p>Game Reset...</p>";
                JOptionPane.showMessageDialog(myMainFrame, message, "No Money Left", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}