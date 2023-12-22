package controller;

import model.BankBetLogic;
import res.R;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * BankPanel class handles the bank-related
 * user interface elements and property change events
 * related to the bank in the Craps game.
 *
 * @author Peter Madin
 * @version 0.0.5  December 5, 2023
 */
public class BankPanel extends JPanel implements PropertyChangeListener {
    /**
     * Panel for bank-related components.
     */
    private final JPanel myBankPanel;

    /**
     * Button to set the bank amount.
     */
    private final JButton mySetButton;

    /**
     * Field displaying the bank amount.
     */
    private final JTextField myBankField;

    /**
     * Field displaying the bet amount.
     */
    private final JTextField myBetField;

    /**
     * Labels for various buttons.
     */
    private final String[] myButtonLabel;

    /**
     * Array holding various buttons.
     */
    private final JButton[] myButtons;

    /**
     * Button to add one unit to the bet.
     */
    private final JButton myAddOne;

    /**
     * Button to add five units to the bet.
     */
    private final JButton myAddFive;

    /**
     * Button to add ten units to the bet.
     */
    private final JButton myAddTen;

    /**
     * Button to add fifty units to the bet.
     */
    private final JButton myAddFifty;

    /**
     * Button to add a hundred units to the bet.
     */
    private final JButton myAddHundred;

    /**
     * Button to add five hundred units to the bet.
     */
    private final JButton myAddFiveHundred;

    /**
     * Button to go all in with the bank amount.
     */
    private final JButton myAllIn;

    /**
     * Instance of BankBetLogic.
     */
    private final BankBetLogic myBankBet;

    /**
     * Holds the previous bet amount.
     */
    private int previousBetAmount = 0;

    /**
     * Constructs a BankPanel instance,
     * initializes components, and lays out the UI.
     */
    public BankPanel() {
        myBankBet = BankBetLogic.getBankBetInstance();
        if (myBankBet != null) {
            myBankBet.addPropertyChangeListener(this);
        }
        myBankPanel = new JPanel(new BorderLayout());
        myBankField = new JTextField(10);
        mySetButton = new JButton("Set Bank");
        myBetField = new JTextField(10);
        myAddOne = new JButton();
        myAddFive = new JButton();
        myAddTen = new JButton();
        myAddFifty = new JButton();
        myAddHundred = new JButton();
        myAddFiveHundred = new JButton();
        myAllIn = new JButton();
        myButtons = new JButton[] {myAddOne, myAddFive, myAddTen, myAddFifty, myAddHundred, myAddFiveHundred, myAllIn};
        myButtonLabel = new String[] {"+$1", "+$5", "+$10", "+$50", "+$100", "+$500", "All In"};

        layoutComponents();
        addListeners();
    }

    /**
     * Lays out components in the BankPanel.
     */
    private void layoutComponents() {
        setLayout(new BorderLayout());
        myBankPanel.setLayout(new BorderLayout());
        myBankPanel.add(makeBankHeader(), BorderLayout.NORTH);
        myBankPanel.add(makeBankPanel());

        JPanel betHeaderAndPanel = new JPanel();
        betHeaderAndPanel.setLayout(new BoxLayout(betHeaderAndPanel, BoxLayout.Y_AXIS));
        betHeaderAndPanel.add(makeBetHeader());
        betHeaderAndPanel.add(makeBetPanel(myButtonLabel, myButtons));

        myBankPanel.add(betHeaderAndPanel, BorderLayout.SOUTH);
        myBankPanel.add(makeBankPanel(), BorderLayout.CENTER);

        add(myBankPanel);

    }

    /**
     * Creates the header panel for the bank section.
     *
     * @return A JPanel representing the bank section header.
     */
    private JPanel makeBankHeader() {
        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER));
        header.setBackground(R.Colors.BACKGROUND);
        JLabel titleLabel = new JLabel("Bank");
        titleLabel.setForeground(R.Colors.HEADER);
        header.add(titleLabel);
        return header;
    }

    /**
     * Creates the bank-related panel with text fields and buttons.
     *
     * @return A JPanel representing the bank panel.
     */
    private JPanel makeBankPanel() {
        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        panel.setBackground(R.Colors.OUTLINES);

        final JPanel bankTextField = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bankTextField.setBackground(R.Colors.BACKGROUND);

        myBankField.setHorizontalAlignment(SwingConstants.CENTER);
        myBankField.setForeground(R.Colors.NUMBERS);
        myBankField.setBackground(R.Colors.BACKGROUND);

        final JPanel bankButton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bankButton.setBackground(R.Colors.BACKGROUND);

        mySetButton.setHorizontalAlignment(SwingConstants.CENTER);
        mySetButton.setBackground(R.Colors.BUTTON);
        mySetButton.setForeground(R.Colors.TEXT_LABEL);
        mySetButton.setEnabled(false);

        bankTextField.add(myBankField);
        bankButton.add(mySetButton);

        panel.add(bankTextField);
        panel.add(bankButton);

        return panel;
    }

    /**
     * Creates the header panel for the bet section.
     *
     * @return A JPanel representing the bet section header.
     */
    private JPanel makeBetHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(R.Colors.BACKGROUND);
        header.setPreferredSize(new Dimension(0, 100));

        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        labelPanel.setBackground(R.Colors.BACKGROUND);

        JLabel titleLabel = new JLabel("Bet");
        titleLabel.setForeground(R.Colors.HEADER);
        labelPanel.add(titleLabel);

        header.add(labelPanel, BorderLayout.SOUTH);
        return header;
    }

    /**
     * Creates the bet-related panel with text fields and buttons.
     *
     * @param theButtonLabels An array of button labels.
     * @param theButtons An array of JButtons.
     * @return A JPanel representing the bet panel.
     */
    private JPanel makeBetPanel(final String[] theButtonLabels, JButton[] theButtons) {
        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        panel.setBackground(R.Colors.OUTLINES);

        final JPanel betTextField = new JPanel(new FlowLayout(FlowLayout.CENTER));
        betTextField.setBackground(R.Colors.BACKGROUND);

        myBetField.setHorizontalAlignment(SwingConstants.CENTER);
        myBetField.setForeground(R.Colors.NUMBERS);
        myBetField.setBackground(R.Colors.BACKGROUND);
        myBetField.setEditable(false);

        betTextField.add(myBetField);
        panel.add(betTextField);

        for (int i = 0; i < myButtons.length; i++) {
            addButtonComponents(theButtonLabels[i], theButtons[i], panel);
        }

        return panel;
    }

    /**
     * Adds button components to the bet panel.
     *
     * @param theButtonLabels The label for the button.
     * @param theButtons The JButton component.
     * @param thePanel The JPanel where the button is added.
     */
    private void addButtonComponents (final String theButtonLabels,
                                      final JButton theButtons, final JPanel thePanel) {
        final JPanel buttonPanels = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanels.setBackground(R.Colors.BACKGROUND);

        theButtons.setText(theButtonLabels);
        theButtons.setForeground(R.Colors.TEXT_LABEL);
        theButtons.setBackground(R.Colors.BUTTON);
        theButtons.setPreferredSize(new Dimension(100,30));
        theButtons.setEnabled(false);
        buttonPanels.add(theButtons);
        thePanel.add(buttonPanels);
    }

    /**
     * Adds listeners to various components in the BankPanel.
     */
    private void addListeners() {
        mySetButton.addActionListener(theEvent -> {
            myBankField.setEditable(false);
            myBankField.setFocusable(false);
            mySetButton.setEnabled(false);
            myBetField.setEditable(true);
            myBankBet.setBank(true);
            for (JButton myButton : myButtons) {
                myButton.setEnabled(true);
            }
        });

        myBankField.addActionListener(theEvent -> myBankField.transferFocus());
        myBankField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent theEvent) {
                final String numText = myBankField.getText().trim();
                int number;
                try {
                    number = Integer.parseInt(numText);
                    if (number <= 0) {
                        throw new NumberFormatException();
                    }
                } catch (final NumberFormatException bankE) {
                    number = myBankBet.getBankAmount();
                    if (number == 0) {
                        myBankField.setText("");
                    } else {
                        myBankField.setText(String.valueOf(number));
                    }
                }
                if (number != 0) {
                    myBankBet.setBankAmount(number);
                    mySetButton.setEnabled(true);
                }
            }
        });

        myBetField.addActionListener(theEvent -> myBetField.transferFocus());
        myBetField.addFocusListener(new FocusAdapter() {

        @Override
        public void focusLost(FocusEvent e) {
            final String numText = myBetField.getText().trim();
            int number;
            try {
                number = Integer.parseInt(numText);
                if (number <= 0 || number > myBankBet.getBankAmount()) {
                    throw new NumberFormatException();
                }
            } catch (final NumberFormatException betE) {
                number = myBankBet.getBetAmount();
                if (number == 0) {
                    myBetField.setText("");
                } else {
                    myBetField.setText(String.valueOf(number));
                }
            }

            if (number < myBankBet.getBetAmount()) {
                number = myBankBet.getBetAmount();
            }

            if (number != 0 && number != previousBetAmount) {
                myBankBet.setBetAmount(number);
                previousBetAmount = myBankBet.getBetAmount();
            }
        }
    });

        myAddOne.addActionListener(theEvent -> {
            if (myBankBet.getBetAmount() + 1 <= myBankBet.getBankAmount()) {
                myBankBet.adjustBet(1);
            }
        });

        myAddFive.addActionListener(theEvent -> {
            if (myBankBet.getBetAmount() + 5 <= myBankBet.getBankAmount()) {
                myBankBet.adjustBet(5);
            }
        });

        myAddTen.addActionListener(theEvent -> {
            if (myBankBet.getBetAmount() + 10 <= myBankBet.getBankAmount()) {
                myBankBet.adjustBet(10);
            }
        });

        myAddFifty.addActionListener(theEvent -> {
            if (myBankBet.getBetAmount() + 50 <= myBankBet.getBankAmount()) {
                myBankBet.adjustBet(50);
            }
        });

        myAddHundred.addActionListener(theEvent -> {
            if (myBankBet.getBetAmount() + 100 <= myBankBet.getBankAmount()) {
                myBankBet.adjustBet(100);
            }
        });

        myAddFiveHundred.addActionListener(theEvent -> {
            if (myBankBet.getBetAmount() + 500 <= myBankBet.getBankAmount()) {
                myBankBet.adjustBet(500);
            }
        });

        myAllIn.addActionListener(theEvent -> {
            if (myBankBet.getBetAmount() < myBankBet.getBankAmount()) {
                myBankBet.allIn();
            }
        });
    }

    /**
     * Handles property change events, updating UI components based on specific events.
     *
     * @param theEvt The PropertyChangeEvent object.
     */
    @Override
    public void propertyChange(PropertyChangeEvent theEvt) {
        if ("bet".equals(theEvt.getPropertyName())) {
            myBetField.setText(theEvt.getNewValue().toString());
            myBankField.setText(String.valueOf(myBankBet.getBankAmount()));
        }

        if ("bankReset".equals(theEvt.getPropertyName())) {
            myBankField.setEditable(true);
            myBankField.setFocusable(true);
            myBankField.setText("");
            myBetField.setText("");
            myBetField.setEditable(false);

            for (JButton myButton : myButtons) {
                myButton.setEnabled(false);
            }
        }

        if ("bankSet".equals(theEvt.getPropertyName())) {
            myBankField.setText(String.valueOf(theEvt.getNewValue()));
        }

        if ("gameOver".equals(theEvt.getPropertyName())) {
            if ((boolean) theEvt.getNewValue()) {
                myBankField.setEditable(false);
                myBankField.setText("");
                myBetField.setText("");

                for (JButton myButton : myButtons) {
                    myButton.setEnabled(false);
                }
            }
        }
    }
}