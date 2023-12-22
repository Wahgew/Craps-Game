package controller;

import model.BankBetLogic;
import model.CrapsLogic;
import res.R;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * RollPlayPanel represents the panel for
 * rolling dice and playing the game.
 *
 * @author Peter Madin
 * @version 0.0.5  December 5, 2023
 */
public class RollPlayPanel extends JPanel implements PropertyChangeListener {
    /**
     * Panel containing buttons for rolling dice and playing the game.
     */
    private final JPanel myButtons;

    /**
     * Button to roll the dice.
     */
    private final JButton myRoll;

    /**
     * Button to play the game again.
     */
    private final JButton myPlayAgain;

    /**
     * Panel for displaying the current roll.
     */
    private final CurrentRollPanel myRollPanel;

    /**
     * Instance of the Craps game logic.
     */
    private final CrapsLogic crapsLogic;

    /**
     * Constructs the RollPlayPanel.
     * @param currentRollPane The current roll panel instance.
     */
    public RollPlayPanel(CurrentRollPanel currentRollPane) {
        crapsLogic = CrapsLogic.getCrapsInstance();
        if (crapsLogic != null) {
            crapsLogic.addPropertyChangeListener(this);
        }
        myRollPanel = currentRollPane;
        myButtons = new JPanel(new BorderLayout());
        myRoll = new JButton("Roll Dice");
        myPlayAgain = new JButton("Play Again");
        add(myRoll);
        add(myPlayAgain);

        layoutComponents();
        addListeners();
    }

    /**
     * Sets the layout for buttons.
     */
    private void layoutComponents() {
        setLayout(new BorderLayout());
        myButtons.setBackground(R.Colors.BACKGROUND);
        myButtons.setPreferredSize(new Dimension(200,400));
        myButtons.setLayout(new BoxLayout(myButtons, BoxLayout.X_AXIS));
        myRoll.setBackground(R.Colors.BUTTON);
        myRoll.setForeground(R.Colors.TEXT_LABEL);
        myPlayAgain.setBackground(Color.BLACK);
        myPlayAgain.setForeground(R.Colors.TEXT_LABEL);
        myButtons.add(myRoll);
        myButtons.add(Box.createHorizontalStrut(30));
        myButtons.add(myPlayAgain);
        myRoll.setEnabled(false);
        myPlayAgain.setEnabled(false);
        myRoll.setMnemonic('R');
        myPlayAgain.setMnemonic('P');

        add(myButtons);
    }

    /**
     * Adds action listeners to the buttons.
     */
    private void addListeners() {
        myRoll.addActionListener(new RollDice());
        myPlayAgain.addActionListener(new PlayAgain());
    }

    /**
     * ActionListener implementation for rolling dice.
     */
    private static final class RollDice implements ActionListener {
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            CrapsLogic craps = CrapsLogic.getCrapsInstance();
            BankBetLogic bank = BankBetLogic.getBankBetInstance();
            if (craps.isGameActive() && bank.getBetSet()) {
                craps.roll();
                craps.checkBankBalance();
            }
        }
    }

    /**
     * ActionListener implementation for playing again.
     */
    private final class PlayAgain implements ActionListener {
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            CrapsLogic craps = CrapsLogic.getCrapsInstance();
            BankBetLogic bank = BankBetLogic.getBankBetInstance();
            if (!craps.isGameActive()) {
                if (!craps.checkBankBalance()) {
                    myPlayAgain.setEnabled(false);
                    craps.softReset();
                    myRoll.setEnabled(true);
                    bank.setBetAmount(bank.getBetAmount());
                }
            }
        }
    }

    /**
     * Handles property change events.
     * @param theEvt The PropertyChangeEvent instance.
     */
    @Override
    public void propertyChange(PropertyChangeEvent theEvt) {
        if ("start".equals(theEvt.getPropertyName())) {
            if ((boolean) theEvt.getNewValue()) {
                myRoll.setEnabled(true);
                myPlayAgain.setFocusable(true);
            }
        }

        if ("active".equals(theEvt.getPropertyName())) {
            if (!((boolean) theEvt.getNewValue())) {
                myRoll.setEnabled(false);
                myPlayAgain.setEnabled(true);
            }
        }

        if ("displayReset".equals(theEvt.getPropertyName())) {
            myPlayAgain.setEnabled(false);
            myPlayAgain.setFocusable(false);
            myRoll.setEnabled(false);
        }
    }
}