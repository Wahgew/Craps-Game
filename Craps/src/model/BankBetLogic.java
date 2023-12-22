package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Manages betting and banking functionality in a Craps game.
 * Tracks bank and bet amounts, adjusts bets and bank balances,
 * and facilitates notification for property changes.
 * @author Peter Madin
 * @version 0.0.2  December 10, 2023
 */
public class BankBetLogic {

    /**
     * Handles property change events related to the bank and bets.
     */
    private final PropertyChangeSupport myBankChange;

    /**
     * Represents the singleton instance of the BankBetLogic class.
     */
    private static final BankBetLogic myInstance = new BankBetLogic();

    /**
     * Stores the current amount in the bank.
     */
    private int myBankAmount;

    /**
     * Stores the current bet amount.
     */
    private int myBetAmount;

    /**
     * Indicates if the bank amount is set.
     */
    private boolean myBankSet;

    /**
     * Indicates if the bet amount is set.
     */
    private boolean myBetSet;

    /**
     * Controls the bet reduction functionality.
     */
    private boolean myCheckReduction;

    /**
     * Constructs a new instance of BankBetLogic with initial values.
     * Initializes the bank and bet amounts to zero, sets up property change support,
     * and configures the initial state of bank and bet settings.
     */
    public BankBetLogic() {
        myCheckReduction = true;
        myBankAmount = 0;
        myBetAmount = 0;
        myBankChange = new PropertyChangeSupport(this);
        myBankSet = false;
    }


    /**
     * Adjusts the current bet amount by the specified offset.
     *
     * @param theOffSet The value to be added to the current bet amount.
     */
    public void adjustBet(final int theOffSet) {
        setBetAmount(myBetAmount + theOffSet);
    }

    /**
     * Adjusts the current bank amount by the specified offset.
     *
     * @param theOffSet The value to be subtracted from the current bank amount.
     */
    public void adjustBank(final int theOffSet) {
        setBankAmount(myBankAmount - theOffSet);
    }

    /**
     * Checks and adjusts the bet and bank amounts based on the new bet value.
     *
     * @param theNewBet The new bet value to be checked against the current bet amount.
     */
    public void checkBetReduce(int theNewBet) {
        int previousBet = myBetAmount;
        int diff = 0;
        if (theNewBet >= previousBet) {
            diff = theNewBet - previousBet;
            myBankAmount -= diff;
            myBetAmount = theNewBet;
        }
        if (diff == 0) {
            myBankAmount -= theNewBet;
            myBetAmount = theNewBet;
        }
    }

    /**
     * Resets the bank and bet amounts to zero and notifies property listeners of the reset.
     */
    public void resetBankAndBet() {
        myBankChange.firePropertyChange("bankReset",null,this);
        myBankAmount = 0;
        myBetAmount = 0;
    }

    /**
     * Reset game when bank is at 0.
     */
    public void gameEnd() {
        myBankChange.firePropertyChange("gameOver", null, true);
    }

    /**
     * Adjusts the bank amount based on the game outcome (win or loss).
     */
    public void bettingWinLost() {
        CrapsLogic craps = CrapsLogic.getCrapsInstance();
        if (craps.getGameWon()) {
            setBankAmount(getBankAmount() + getBetAmount() * 2);
        }
    }

    /**
     * Sets the bank amount equal to the current bet amount,
     * resets the bet amount to zero,
     * and notifies property listeners of the bank reset.
     */
    public void allIn() {
        setBankAmount(getBetAmount() + getBankAmount());
        setBetAmount(getBankAmount());
        myBankAmount = 0;
        myBankChange.firePropertyChange("bankSet",null,myBankAmount);
    }

    /**
     * Retrieves the singleton instance of BankBetLogic.
     *
     * @return The instance of BankBetLogic.
     */
    public static BankBetLogic getBankBetInstance() {
        return myInstance;
    }

    /**
     * Retrieves the current bank amount.
     *
     * @return The current bank amount.
     */
    public int getBankAmount() {
        return myBankAmount;
    }

    /**
     * Retrieves the current bet amount.
     *
     * @return The current bet amount.
     */
    public int getBetAmount() {
        return myBetAmount;
    }

    /**
     * Checks if the bank amount is set.
     *
     * @return True if the bank amount is set, otherwise false.
     */
    public boolean getBankSet() {
        return myBankSet;
    }

    /**
     * Checks if the bet amount is set.
     *
     * @return True if the bet amount is set, otherwise false.
     */
    public boolean getBetSet() {
        return myBetSet;
    }

    /**
     * Sets the flag indicating if the bank amount is set.
     *
     * @param theFlag The flag value to be set for the bank.
     */
    public void setBank(boolean theFlag) {
        myBankSet = theFlag;
    }

    /**
     * Sets the flag indicating if the bet amount is set.
     *
     * @param theFlag The flag value to be set for the bet.
     */
    public void setBet(boolean theFlag) {
        myBetSet = theFlag;
    }

    /**
     * Sets the bank amount to the specified value.
     * Fires a property change event for bank amount set.
     *
     * @param theAmount The amount to be set for the bank.
     * @throws IllegalArgumentException
     * if the specified amount is less than or equal to 0.
     */
    public void setBankAmount(final int theAmount) {
        if (theAmount <= 0) {
            throw new IllegalArgumentException("The bank amount must be greater than 0, "
                    + "current value is " + theAmount);
        }
        myBankAmount = theAmount;
        myBankChange.firePropertyChange("bankSet",null,theAmount);
    }

    /**
     * Sets the bet amount to the specified value.
     * Fires a property change event for bet amount set.
     *
     * @param theAmount The amount to be set for the bet.
     * @throws IllegalArgumentException if the specified amount
     * is less than or equal to 0 or exceeds the available bank balance.
     */
    public void setBetAmount(final int theAmount) {
        if (theAmount > getBankAmount()) {
            throw new IllegalArgumentException("The bet amount exceeds the available bank balance, "
            + "current bet is " + getBetAmount());
        }

        if (theAmount <= 0) {
            throw new IllegalArgumentException("The bet mount must be greater than 0, "
                    + "current bet is " + getBetAmount());
        }
        checkBetReduce(theAmount);
        myBankChange.firePropertyChange("bet",null, theAmount);
        setBet(true);
    }

    /**
     * Adds a property change listener for bank-related changes.
     *
     * @param theL The PropertyChangeListener to be added.
     */
    public void addPropertyChangeListener(PropertyChangeListener theL) {
        myBankChange.addPropertyChangeListener(theL);
    }

    /**
     * Generates a string of the bank amount and the bet amount.
     *
     * @return a string representing the bank and bet amounts.
     */
    @Override
    public String toString() {
        return "Bank: " + getBankAmount()
             + "\nBet: " + getBetAmount();
    }
}