package controller;

import model.CrapsLogic;
import res.R;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Panel for displaying current roll
 * details in a Craps game.
 *
 * @author Peter Madin
 * @version 0.0.5  December 2, 2023
 */
public class CurrentRollPanel extends JPanel implements PropertyChangeListener {
    /**
     * Panel for organizing roll details.
     */
    private final JPanel myRollPanel;

    /**
     * Text field representing the value of the first die.
     */
    private final JTextField myDieField1;

    /**
     * Text field representing the value of the second die.
     */
    private final JTextField myDieField2;

    /**
     * Text field representing the current point in the game.
     */
    private final JTextField myPoint;

    /**
     * Text field representing the total value of the dice rolled.
     */
    private final JTextField myTotal;

    /**
     * Labels for different elements displayed in the panel.
     */
    private final String[] myLabels;

    /**
     * Array of text fields associated with specific labels.
     */
    private final JTextField[] myTextField;

    /**
     * Instance of the CrapsLogic class for managing game logic.
     */
    private final CrapsLogic crapsLogic;


    /**
     * Constructs a panel that displays the
     * current roll information for a game.
     * This panel gives details about dice values,
     * total, and point in a Craps game.
     */
    public CurrentRollPanel() {
        crapsLogic = CrapsLogic.getCrapsInstance();
        if (crapsLogic != null) {
            crapsLogic.addPropertyChangeListener(this);
        }
        myRollPanel = new JPanel(new BorderLayout());
        myDieField1 = new JTextField(10);
        myDieField2 = new JTextField(10);
        myTotal = new JTextField(10);
        myPoint = new JTextField(10);
        myTextField = new JTextField[] {myDieField1, myDieField2, myTotal, myPoint};
        myLabels = new String[] {"Die 1: ", "Die 2: ", "Total: ", "Point: "};
        layoutComponents();
    }

    /**
     * Organizes the components in the layout.
     */
    private void layoutComponents() {
        setLayout(new BorderLayout());
        myRollPanel.setLayout(new BorderLayout());
        myRollPanel.add(makeHeader(), BorderLayout.NORTH);
        myRollPanel.add(makeRollPanel(myLabels, myTextField));

        add(myRollPanel);
    }

    /**
     * Creates a header panel for current roll.
     *
     * @return JPanel The header panel.
     */
    private JPanel makeHeader() {
        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER));
        header.setBackground(R.Colors.BACKGROUND);
        JLabel titleLabel = new JLabel("Current Roll");
        titleLabel.setForeground(R.Colors.HEADER);
        header.add(titleLabel);
        return header;
    }

    /**
     * Creates a panel for displaying roll details.
     *
     * @param theLabels Array of labels.
     * @param theTextField Array of text fields.
     * @return JPanel Panel with roll details.
     */
    private JPanel makeRollPanel (final String[] theLabels, JTextField[] theTextField) {
        final JPanel panel = new JPanel(new GridLayout(myLabels.length, 1));

        panel.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
        panel.setBackground(R.Colors.OUTLINES);
        for (int i = 0; i < myLabels.length; i++) {
            addRollPanel(theLabels[i], theTextField[i], panel);
        }
        return panel;
    }

    /**
     * Adds roll details to the panel.
     *
     * @param theLabel Label for the roll detail.
     * @param theTextField Text field for the roll detail.
     * @param thePanel Panel to add the roll detail.
     */
    private void addRollPanel (final String theLabel, final JTextField theTextField, final JPanel thePanel) {
        final JPanel rollInfo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rollInfo.setBackground(R.Colors.BACKGROUND);
        final JLabel l = new JLabel(theLabel);
        l.setForeground(R.Colors.TEXT_LABEL);

        theTextField.setHorizontalAlignment(SwingConstants.CENTER);
        theTextField.setEditable(false);
        theTextField.setFocusable(false);
        theTextField.setForeground(R.Colors.NUMBERS);
        theTextField.setBackground(R.Colors.BACKGROUND);
        rollInfo.add(l);
        rollInfo.add(theTextField);
        thePanel.add(rollInfo);
    }

    /**
     * Handles property change events.
     *
     * @param theEvt PropertyChangeEvent instance.
     */
    @Override
    public void propertyChange(PropertyChangeEvent theEvt) {
        if ("diceRoll".equals(theEvt.getPropertyName())) {
            myDieField1.setText(String.valueOf(crapsLogic.getDice1()));
            myDieField2.setText(String.valueOf(crapsLogic.getDice2()));
            myTotal.setText(String.valueOf(crapsLogic.getTotal()));
            myPoint.setText(String.valueOf(crapsLogic.getPoint()));
        }

        if ("displayReset".equals(theEvt.getPropertyName())) {
            myDieField1.setText("");
            myDieField2.setText("");
            myTotal.setText("");
            myPoint.setText("");
        }
    }
}