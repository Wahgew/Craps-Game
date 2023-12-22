package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Random;

/**
 * The CrapsLogic class simulates the logic of a game of Craps.
 * It manages the game state, dice rolling, win counts, and active game tracking.
 * This class follows a singleton pattern, ensuring
 * only one instance exists throughout the game session.
 *
 * @author Peter Madin
 * @version 0.0.7  November 27, 2023
 */
public class CrapsLogic {
    /**
     * Manages the PropertyChangeSupport for handling property changes.
     */
    private final PropertyChangeSupport myChanges = new PropertyChangeSupport(this);

    /**
     * Utilized for generating random numbers in the game.
     */
    private final Random myRandom = new Random();

    /**
     * Represents the value of the first dice.
     */
    private int myDice1;

    /**
     * Represents the value of the second dice.
     */
    private int myDice2;

    /**
     * Represents the total value of both dice.
     */
    private int myTotal;

    /**
     * Tracks the number of wins by the player.
     */
    private int myPlayerWins;

    /**
     * Represents the point value in the game.
     */
    private int myPoint;

    /**
     * Tracks the number of wins by the house.
     */
    private int myHouseWins;

    /**
     * Tracks whether the game is currently active.
     */
    private boolean myGameActive;

    /**
     * Tracks whether the game is won or not.
     */
    private boolean myGameWon;

    /**
     * Singleton instance of the CrapsLogic class.
     */
    private static final CrapsLogic myInstance = new CrapsLogic();

    /**
     * Initializes the CrapsLogic instance and starts the game.
     */
    public CrapsLogic() {
        startGame();
    }

    /**
     * Sets the game's active status.
     *
     * @param theActive true if the game is active, false otherwise.
     */
    public void setGameActive(final Boolean theActive) {
        myGameActive = theActive;
        if (!myGameActive) {
            myChanges.firePropertyChange("active", null, false);
        }
    }

    /**
     * Sets the win status of the game.
     * And fires changes to displaying panels.
     *
     * @param theGameWins true if the game is won, false otherwise.
     */
    public void setWins(final boolean theGameWins) {
        myGameWon = theGameWins;
        myChanges.firePropertyChange("diceRoll", null, this);
        myChanges.firePropertyChange("winStatus", null, true);
        myChanges.firePropertyChange("winDisplay", null, theGameWins);
    }

    /**
     * Sets the point value in the game.
     *
     * @param thePoint the point value to be set.
     */
    public void setPoint(final int thePoint) {
        myPoint = thePoint;
    }

    /**
     * Sets the total value in the game.
     *
     * @param theTotal the total value to be set.
     */
    public void setTotal(final int theTotal) {
        myTotal = theTotal;
    }

    /**
     * Sets the number of wins for the player.
     *
     * @param thePWins the number of player wins to be set.
     */
    public void setPlayerWins(final int thePWins) {
        myPlayerWins = thePWins;
    }

    /**
     * Sets the number of wins for the house.
     *
     * @param theHWins the number of house wins to be set.
     */
    public void setHouseWins(final int theHWins) {
        myHouseWins = theHWins;
    }

    /**
     * Sets the value of the first die.
     *
     * @param theDie1 the value of the first die.
     */
    public void setDie1(final int theDie1) {
        myDice1 = theDie1;
    }

    /**
     * Sets the value of the second die.
     *
     * @param theDie2 the value of the second die.
     */
    public void setDie2(final int theDie2) {
        myDice2 = theDie2;
    }

    /**
     * Retrieves the total value from the last dice roll.
     *
     * @return the total value of both dice.
     */
    public int getTotal() {
        return myTotal;
    }

    /**
     * Retrieves the point value in the game.
     *
     * @return the point value.
     */
    public int getPoint() {
        return myPoint;
    }

    /**
     * Retrieves the number of wins for the player.
     *
     * @return the number of player wins.
     */
    public int getPlayerWins() {
        return myPlayerWins;
    }

    /**
     * Retrieves the number of wins for the house.
     *
     * @return the number of house wins.
     */
    public int getHouseWins() {
        return myHouseWins;
    }

    /**
     * Retrieves the value of the first die.
     *
     * @return the value of the first die.
     */
    public int getDice1() {
        return myDice1;
    }

    /**
     * Retrieves the value of the second die.
     *
     * @return the value of the second die.
     */
    public int getDice2() {
        return myDice2;
    }

    /**
     * Retrieves the instance of the CrapsLogic class.
     *
     * @return the instance of CrapsLogic.
     */
    public static CrapsLogic getCrapsInstance() {
        return myInstance;
    }

    /**
     * Checks if the game is active.
     *
     * @return true if the game is active, false otherwise.
     */
    public boolean isGameActive() {
        return myGameActive;
    }

    /**
     * Checks if the game is won.
     *
     * @return true if the game is won, false otherwise.
     */
    public boolean getGameWon() {
        return myGameWon;
    }

    /**
     * Starts a new game by setting initial values.
     */
    public void startGame() {
        setGameActive(true);
        setWins(false);
        setPoint(0);
        setTotal(0);
        setHouseWins(0);
        setPlayerWins(0);
    }

    /**
     * Simulates a dice roll and performs game logic based on the roll outcome.
     * Updates game state accordingly and notifies listeners.
     */
    public void roll() {
        if (myGameActive) {
            setDie1(myRandom.nextInt(1, 7));
            setDie2(myRandom.nextInt(1, 7));
            setTotal(myDice1 + myDice2);

            if (myPoint == 0) {
                if (myTotal == 7 || myTotal == 11) {
                    myPlayerWins++;
                    setWins(true);
                    setGameActive(false);
                } else if (myTotal == 2 || myTotal == 3 || myTotal == 12) {
                    myHouseWins++;
                    setWins(false);
                    setGameActive(false);
                } else {
                    setPoint(myTotal);
                }
            } else {
                if (myTotal == myPoint) {
                    myPlayerWins++;
                    setWins(true);
                    setGameActive(false);
                } else if (myTotal == 7) {
                    myHouseWins++;
                    setWins(false);
                    setGameActive(false);
                }
            }
        }
        myChanges.firePropertyChange("diceRoll", null, this);
    }

    /**
     * Resets the game state partially
     * by setting the point and total to zero and
     * setting the game as active.
     */
    public void softReset() {
        setPoint(0);
        setTotal(0);
        setGameActive(true);
    }

    /**
     * Resets the game state completely
     * by setting all values to zero and notifies
     * listeners about the display reset.
     */
    public void hardReset() {
        setPoint(0);
        setTotal(0);
        setPlayerWins(0);
        setHouseWins(0);
        myChanges.firePropertyChange("displayReset",null,this);
    }

    /**
     * Checks the bank balance, triggers game over if the bank amount is zero,
     * and performs a hard reset of the game.
     *
     * @return true if bank balance is zero, false otherwise.
     */
    public boolean checkBankBalance() {
        BankBetLogic bank = BankBetLogic.getBankBetInstance();
        if (bank.getBankAmount() == 0) {
            bank.resetBankAndBet();
            bank.gameEnd();
            myChanges.firePropertyChange("gameOver", null, bank.getBankAmount() == 0);
            hardReset();
        }
        return bank.getBankAmount() == 0;
    }

    /**
     * Adds a PropertyChangeListener to listen for changes in the CrapsLogic class.
     *
     * @param theL the PropertyChangeListener to be added.
     */
    public void addPropertyChangeListener(PropertyChangeListener theL) {
        myChanges.addPropertyChangeListener(theL);
    }

    /**
     * Generates a string representation of the current game state,
     * including player wins, house wins, total, point, and dice values.
     *
     * @return a string representing the current game state.
     */
    @Override
    public String toString() {
        return "Player Wins: " + myPlayerWins
                + "\nHouse Wins: " + myHouseWins
                + "\nTotal: " + getTotal()
                + "\nPoint: " + getPoint()
                + "\nDie 1: " + getDice1()
                + "\nDie 2: " + getDice2();
    }
}